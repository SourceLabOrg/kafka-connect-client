package org.sourcelab.kafka.connect.apiclient.request.delete;

import com.google.common.base.Preconditions;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines a request to delete a connector.
 */
public final class DeleteConnector implements DeleteRequest<Boolean> {
    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of the connector.
     */
    public DeleteConnector(final String connectorName) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName);
    }

    @Override
    public Object getRequestBody() {
        return "";
    }

    @Override
    public Boolean parseResponse(final String responseStr) throws IOException {
        return true;
    }
}
