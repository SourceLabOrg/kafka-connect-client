package org.sourcelab.kafka.connect.apiclient.request.put;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPluginConfigValidationResults;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

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
        Preconditions.checkNotNull(connectorPluginName);
        Preconditions.checkNotNull(config);
        this.connectorPluginName = connectorPluginName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connector-plugins/" + urlPathSegmentEscaper().escape(connectorPluginName) + "/config/validate";
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
