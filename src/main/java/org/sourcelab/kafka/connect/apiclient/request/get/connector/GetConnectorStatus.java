package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.get.GetRequest;

import java.io.IOException;

public class GetConnectorStatus implements GetRequest<ConnectorStatus> {

    private final String name;

    public GetConnectorStatus(final String name) {
        this.name = name;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name) + "/status";
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
