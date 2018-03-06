package org.sourcelab.kafka.connect.apiclient.request.put;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PutConnectorPluginConfigValidate implements PutRequest<String> {
    private final String connectorPluginName;
    private final Map<String, String> config;

    public PutConnectorPluginConfigValidate(final String connectorPluginName, final Map<String, String> config) {
        if (connectorPluginName == null) {
            throw new NullPointerException("ConnectorPluginName parameter may not be null!");
        }
        if (config == null) {
            throw new NullPointerException("config parameter may not be null!");
        }
        this.connectorPluginName = connectorPluginName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connector-plugins/" + Uri.escapeDisallowedChars(connectorPluginName) + "/config/validate";
    }

    @Override
    public Object getRequestBody() {
        return config;
    }

    @Override
    public String parseResponse(final String responseStr) throws IOException {
        //return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
        return responseStr;
    }
}
