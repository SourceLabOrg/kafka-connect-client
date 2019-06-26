package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedMetadata;

import java.io.IOException;

public class GetConnectorsExpandAllDetails implements GetRequest<ConnectorsWithExpandedMetadata> {

    @Override
    public String getApiEndpoint() {
        return "/connectors?expand=info&expand=status";
    }

    @Override
    public ConnectorsWithExpandedMetadata parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorsWithExpandedMetadata.class);
    }
}

