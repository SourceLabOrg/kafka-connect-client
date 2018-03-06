package org.sourcelab.kafka.connect.apiclient.request.put;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to update a connectors configuration.
 */
public final class PutConnectorConfig implements PutRequest<ConnectorDefinition> {
    private final String connectorName;
    private final Map<String, String> config;

    /**
     * Constructor.
     * @param connectorName Name of connector
     * @param config Map of configuration items.
     */
    public PutConnectorConfig(final String connectorName, final Map<String, String> config) {
        Preconditions.checkNotNull(connectorName);
        Preconditions.checkNotNull(config);
        this.connectorName = connectorName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/config";
    }

    @Override
    public Object getRequestBody() {
        return config;
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
