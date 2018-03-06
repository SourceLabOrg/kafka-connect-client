package org.sourcelab.kafka.connect.apiclient.request.get;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;

import java.io.IOException;
import java.util.Map;

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
        if (connectorName == null) {
            throw new NullPointerException("connectorName parameter may not be null!");
        }
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(connectorName) + "/config";
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
