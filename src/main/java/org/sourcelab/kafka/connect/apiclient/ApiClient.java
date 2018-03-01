package org.sourcelab.kafka.connect.apiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestErrorResponse;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.connector.GetConnector;
import org.sourcelab.kafka.connect.apiclient.request.get.connector.GetConnectorConfig;
import org.sourcelab.kafka.connect.apiclient.request.get.connector.GetConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.connector.GetConnectors;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.post.connector.PostConnectors;
import org.sourcelab.kafka.connect.apiclient.request.put.connector.PutConnectorConfig;
import org.sourcelab.kafka.connect.apiclient.rest.HttpClientRestClient;
import org.sourcelab.kafka.connect.apiclient.rest.InvalidRequestException;
import org.sourcelab.kafka.connect.apiclient.rest.RestClient;
import org.sourcelab.kafka.connect.apiclient.rest.RestResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    /**
     * Our API Configuration.
     */
    private final Configuration configuration;

    /**
     * Underlying RestClient to use.
     */
    private final RestClient restClient;

    /**
     * Internal State flag.
     */
    private boolean isInitialized = false;


    /**
     * Default Constructor.
     * @param configuration Api Client Configuration.
     */
    public ApiClient(final Configuration configuration) {
        this.configuration = configuration;
        this.restClient = new HttpClientRestClient();
    }

    /**
     * Constructor for injecting a RestClient implementation.
     * Typically only used in testing.
     * @param configuration Pardot Api Configuration.
     * @param restClient RestClient implementation to use.
     */
    public ApiClient(final Configuration configuration, final RestClient restClient) {
        this.configuration = configuration;
        this.restClient = restClient;
    }

    private <T> T submitRequest(final Request<T> request) {
        // Submit request
        final RestResponse restResponse = getRestClient().submitRequest(request);
        final int responseCode = restResponse.getHttpCode();
        String responseStr = restResponse.getResponseStr();

        // If we have a valid response
        logger.info("Response: {}", restResponse);

        // Check for invalid http status codes
        if (responseCode >= 200 && responseCode < 300) {
            // These response codes have no values
            if ((responseCode == 204 || responseCode == 205) && responseStr == null) {
                // Avoid NPE
                responseStr = "";
            }

            try {
                return request.parseResponse(restResponse.getResponseStr());
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage(), exception);
            }
        }

        // Attempt to parse error response
        try {
            final RequestErrorResponse errorResponse = JacksonFactory.newInstance().readValue(responseStr, RequestErrorResponse.class);
            throw new InvalidRequestException(errorResponse.getMessage(), errorResponse.getErrorCode());
        } catch (IOException e) {
            // swallow
        }
        throw new InvalidRequestException("Invalid response from server: " + responseStr, restResponse.getHttpCode());

    }

    public Collection<String> getConnectors() {
        return submitRequest(new GetConnectors());
    }

    public ConnectorDefinition getConnector(final String connectorName) {
        return submitRequest(new GetConnector(connectorName));
    }

    public Map<String, String> getConnectorConfig(final String connectorName) {
        return submitRequest(new GetConnectorConfig(connectorName));
    }

    public ConnectorStatus getConnectorStatus(final String connectorName) {
        return submitRequest(new GetConnectorStatus(connectorName));
    }

    // TODO Add return value
    public String addConnector(final ConnectorDefinition connectorDefinition) {
        return submitRequest(new PostConnectors(connectorDefinition));
    }

    public ConnectorDefinition updateConnectorConfig(final String connectorName, final Map<String, String> config) {
        return submitRequest(new PutConnectorConfig(connectorName, config));
    }

    /**
     * package protected for access in tests.
     * @return Rest Client.
     */
    private RestClient getRestClient() {
        // If we haven't initialized.
        if (!isInitialized) {
            // Call Init.
            restClient.init(getConfiguration());

            // Flip state flag
            isInitialized = true;
        }

        // return our rest client.
        return restClient;
    }

    private Configuration getConfiguration() {
        return configuration;
    }
}
