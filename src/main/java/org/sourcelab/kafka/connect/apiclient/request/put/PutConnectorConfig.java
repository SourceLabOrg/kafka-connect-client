package org.sourcelab.kafka.connect.apiclient.request.put;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.put.PutRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PutConnectorConfig implements PutRequest<ConnectorDefinition> {
    private final String name;
    private final Map<String, String> config;

    public PutConnectorConfig(final String name, final Map<String, String> config) {
        this.name = name;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name) + "/config";
    }

    @Override
    public Object getRequestBody() {
        return config;
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
