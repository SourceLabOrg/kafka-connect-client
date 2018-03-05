package org.sourcelab.kafka.connect.apiclient.request.post.connector;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.post.PostRequest;

import java.io.IOException;

public class PostConnectorRestart implements PostRequest<Boolean> {
    private final String name;

    public PostConnectorRestart(final String name) {
        this.name = name;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(name) + "/restart";
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
