package org.sourcelab.kafka.connect.apiclient.request.post;

import com.sun.xml.internal.rngom.util.Uri;

import java.io.IOException;

/**
 * Defines a request to restart a connector.
 */
public final class PostConnectorRestart implements PostRequest<Boolean> {
    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of connector to restart
     */
    public PostConnectorRestart(final String connectorName) {
        if (connectorName == null) {
            throw new NullPointerException("connectorName parameter may not be null!");
        }
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(connectorName) + "/restart";
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
