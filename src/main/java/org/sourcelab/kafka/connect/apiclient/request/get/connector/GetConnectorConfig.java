package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.get.GetRequest;

import java.io.IOException;
import java.util.Map;

public class GetConnectorConfig implements GetRequest<Map<String, String>> {

    private final String name;

    public GetConnectorConfig(final String name) {
        this.name = name;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name) + "/config";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Map<String, String> parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, Map.class);
    }
}
