package org.sourcelab.kafka.connect.apiclient.request.put.connector;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.put.PutRequest;

import java.io.IOException;

public class PutConnectorPause implements PutRequest<Boolean> {
    private final String name;

    public PutConnectorPause(final String name) {
        this.name = name;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name) + "/pause";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Boolean parseResponse(final String responseStr) throws IOException {
        return true;
    }
}
