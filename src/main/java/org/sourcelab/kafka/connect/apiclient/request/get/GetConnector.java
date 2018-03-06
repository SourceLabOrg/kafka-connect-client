package org.sourcelab.kafka.connect.apiclient.request.get;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;

import java.io.IOException;

/**
 * Defines request to get details about a deployed connector.
 */
public final class GetConnector implements GetRequest<ConnectorDefinition> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of the connector.
     */
    public GetConnector(final String connectorName) {
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
        return null;
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
