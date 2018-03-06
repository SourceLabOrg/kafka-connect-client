package org.sourcelab.kafka.connect.apiclient.request.put;

import com.google.common.base.Preconditions;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to pause a connector.
 */
public final class PutConnectorPause implements PutRequest<Boolean> {
    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of connector
     */
    public PutConnectorPause(final String connectorName) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/pause";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Boolean parseResponse(final String responseStr) throws IOException {
        return true;
    }
}
