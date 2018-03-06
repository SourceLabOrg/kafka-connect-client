package org.sourcelab.kafka.connect.apiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestErrorResponse;
import org.sourcelab.kafka.connect.apiclient.request.delete.DeleteConnector;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.dto.NewConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.dto.Task;
import org.sourcelab.kafka.connect.apiclient.request.dto.TaskStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnector;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorConfig;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorTaskStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorTasks;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectors;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.post.PostConnector;
import org.sourcelab.kafka.connect.apiclient.request.post.PostConnectorRestart;
import org.sourcelab.kafka.connect.apiclient.request.post.PostConnectorTaskRestart;
import org.sourcelab.kafka.connect.apiclient.request.put.PutConnectorConfig;
import org.sourcelab.kafka.connect.apiclient.request.put.PutConnectorPause;
import org.sourcelab.kafka.connect.apiclient.request.put.PutConnectorResume;
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
                return request.parseResponse(responseStr);
            } catch (final IOException exception) {
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

    /**
     * Get the status of specified connector by name.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)-config
     *
     * @param connectorName Name of connector.
     * @return Status details of the connector.
     */
    public ConnectorStatus getConnectorStatus(final String connectorName) {
        return submitRequest(new GetConnectorStatus(connectorName));
    }

    /**
     * Create a new connector, returning the current connector info if successful.
     * https://docs.confluent.io/current/connect/restapi.html#post--connectors
     *
     * @param connectorDefinition Defines the new connector to deploy
     * @return connector info.
     */
    public ConnectorDefinition addConnector(final NewConnectorDefinition connectorDefinition) {
        return submitRequest(new PostConnector(connectorDefinition));
    }

    /**
     * Update a connector's configuration.
     * https://docs.confluent.io/current/connect/restapi.html#put--connectors-(string-name)-config
     *
     * @param connectorName Name of connector to update.
     * @param config Configuration values to set.
     * @return ConnectorDefinition describing the connectors configuration.
     */
    public ConnectorDefinition updateConnectorConfig(final String connectorName, final Map<String, String> config) {
        return submitRequest(new PutConnectorConfig(connectorName, config));
    }

    /**
     * Restart a connector.
     * https://docs.confluent.io/current/connect/restapi.html#post--connectors-(string-name)-restart
     *
     * @param connectorName Name of connector to restart.
     * @return Boolean true if success.
     */
    public Boolean restartConnector(final String connectorName) {
        return submitRequest(new PostConnectorRestart(connectorName));
    }

    /**
     * Pause a connector.
     * https://docs.confluent.io/current/connect/restapi.html#put--connectors-(string-name)-pause
     *
     * @param connectorName Name of connector to pause.
     * @return Boolean true if success.
     */
    public Boolean pauseConnector(final String connectorName) {
        return submitRequest(new PutConnectorPause(connectorName));
    }

    /**
     * Resume a connector.
     * https://docs.confluent.io/current/connect/restapi.html#put--connectors-(string-name)-resume
     *
     * @param connectorName Name of connector to resume.
     * @return Boolean true if success.
     */
    public Boolean resumeConnector(final String connectorName) {
        return submitRequest(new PutConnectorResume(connectorName));
    }

    /**
     * Resume a connector.
     * https://docs.confluent.io/current/connect/restapi.html#put--connectors-(string-name)-resume
     *
     * @param connectorName Name of connector to resume.
     * @return Boolean true if success.
     */
    public Boolean deleteConnector(final String connectorName) {
        return submitRequest(new DeleteConnector(connectorName));
    }

    /**
     * Get a list of tasks currently running for the connector.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)-tasks
     *
     * @param connectorName Name of connector to retrieve tasks for.
     * @return Collection of details about each task.
     */
    public Collection<Task> getConnectorTasks(final String connectorName) {
        return submitRequest(new GetConnectorTasks(connectorName));
    }

    /**
     * Get a task’s status.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)-tasks-(int-taskid)-status
     *
     * @param connectorName Name of connector to retrieve tasks for.
     * @param taskId Id of task to get status for.
     * @return Details about task.
     */
    public TaskStatus getConnectorTaskStatus(final String connectorName, final int taskId) {
        return submitRequest(new GetConnectorTaskStatus(connectorName, taskId));
    }

    /**
     * Restart an individual task.
     * https://docs.confluent.io/current/connect/restapi.html#post--connectors-(string-name)-tasks-(int-taskid)-restart
     *
     * @param connectorName Name of connector to restart tasks for.
     * @param taskId Id of task to restart
     * @return True if a success.
     */
    public Boolean restartConnectorTask(final String connectorName, final int taskId) {
        return submitRequest(new PostConnectorTaskRestart(connectorName, taskId));
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