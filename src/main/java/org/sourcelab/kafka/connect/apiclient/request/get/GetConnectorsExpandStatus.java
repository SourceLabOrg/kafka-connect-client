package org.sourcelab.kafka.connect.apiclient.request.get;


import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedMetadata;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedStatus;

import java.io.IOException;

public class GetConnectorsExpandStatus implements GetRequest<ConnectorsWithExpandedStatus> {

    @Override
    public String getApiEndpoint() {
        return "/connectors?expand=status";
    }

    @Override
    public ConnectorsWithExpandedStatus parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorsWithExpandedMetadata.class);
    }
}
