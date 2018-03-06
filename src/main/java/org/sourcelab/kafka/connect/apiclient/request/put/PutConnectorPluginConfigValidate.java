package org.sourcelab.kafka.connect.apiclient.request.put;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPluginConfigValidationResults;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines request to validate a connector plugin's configuration.
 */
public final class PutConnectorPluginConfigValidate implements PutRequest<ConnectorPluginConfigValidationResults> {
    private final String connectorPluginName;
    private final Map<String, String> config;

    /**
     * Constructor.
     * @param connectorPluginName Name of the class for the connector plugin.
     * @param config Configuration entries to validate.
     */
    public PutConnectorPluginConfigValidate(final String connectorPluginName, final Map<String, String> config) {
        if (connectorPluginName == null) {
            throw new NullPointerException("ConnectorPluginName parameter may not be null!");
        }
        if (config == null) {
            throw new NullPointerException("config parameter may not be null!");
        }
        this.connectorPluginName = connectorPluginName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connector-plugins/" + Uri.escapeDisallowedChars(connectorPluginName) + "/config/validate";
    }

    @Override
    public Object getRequestBody() {
        return config;
    }

    @Override
    public ConnectorPluginConfigValidationResults parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorPluginConfigValidationResults.class);
    }
}
