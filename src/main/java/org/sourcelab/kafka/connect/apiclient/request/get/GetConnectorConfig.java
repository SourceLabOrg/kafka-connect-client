package org.sourcelab.kafka.connect.apiclient.request.get;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;

import java.io.IOException;
import java.util.Map;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines a request for getting the configuration for a connector.
 */
public final class GetConnectorConfig implements GetRequest<Map<String, String>> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of the connector.
     */
    public GetConnectorConfig(final String connectorName) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/config";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Map<String, String> parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, Map.class);
    }
}
