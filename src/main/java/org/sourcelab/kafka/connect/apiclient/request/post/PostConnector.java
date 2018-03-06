package org.sourcelab.kafka.connect.apiclient.request.post;

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.dto.NewConnectorDefinition;

import java.io.IOException;

public class PostConnector implements PostRequest<ConnectorDefinition> {
    private final NewConnectorDefinition connectorDefinition;

    public PostConnector(final NewConnectorDefinition connectorDefinition) {
        if (connectorDefinition == null) {
            throw new NullPointerException("ConnectorDefinition parameter cannot be a null reference!");
        }
        this.connectorDefinition = connectorDefinition;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors";
    }

    @Override
    public Object getRequestBody() {
        return connectorDefinition;
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
