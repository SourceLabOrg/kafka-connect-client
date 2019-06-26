package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedInfo;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedMetadata;

import java.io.IOException;

public class GetConnectorsExpandInfo implements GetRequest<ConnectorsWithExpandedInfo> {

    @Override
    public String getApiEndpoint() {
        return "/connectors?expand=info";
    }

    @Override
    public ConnectorsWithExpandedInfo parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorsWithExpandedMetadata.class);
    }
}

