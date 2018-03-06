package org.sourcelab.kafka.connect.apiclient.request.get;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;

import java.io.IOException;

/**
 * Defines request to get the status of a connector.
 */
public final class GetConnectorStatus implements GetRequest<ConnectorStatus> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of connector.
     */
    public GetConnectorStatus(final String connectorName) {
        if (connectorName == null) {
            throw new NullPointerException("connectorName parameter may not be null!");
        }
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(connectorName) + "/status";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public ConnectorStatus parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorStatus.class);
    }
}
