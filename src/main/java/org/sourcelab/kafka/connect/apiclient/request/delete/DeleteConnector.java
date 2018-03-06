package org.sourcelab.kafka.connect.apiclient.request.delete;

import com.sun.xml.internal.rngom.util.Uri;

import java.io.IOException;

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
        if (connectorName == null) {
            throw new NullPointerException("connectorName parameter may not be null!");
        }
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(connectorName);
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
