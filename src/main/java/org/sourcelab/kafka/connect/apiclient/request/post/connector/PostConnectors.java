package org.sourcelab.kafka.connect.apiclient.request.post.connector;

import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.post.PostRequest;

import java.io.IOException;

public class PostConnectors implements PostRequest<String> {
    private final ConnectorDefinition connectorDefinition;

    public PostConnectors(final ConnectorDefinition connectorDefinition) {
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
    public String parseResponse(final String responseStr) throws IOException {
        return null;
    }
}
