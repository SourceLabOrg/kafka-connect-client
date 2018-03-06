package org.sourcelab.kafka.connect.apiclient.request.delete;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.delete.DeleteRequest;

import java.io.IOException;

public class DeleteConnector implements DeleteRequest<Boolean> {
    private final String name;

    public DeleteConnector(final String name) {
        this.name = name;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name);
    }

    @Override
    public Object getRequestBody() {
        return "";
    }

    @Override
    public Boolean parseResponse(final String responseStr) throws IOException {
        return true;
    }
}
