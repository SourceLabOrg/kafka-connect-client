package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;

import java.io.IOException;
import java.util.Collection;

/**
 * Defines request to get list of deployed connectors.
 */
public final class GetConnectors implements GetRequest<Collection<String>> {
    @Override
    public String getApiEndpoint() {
        return "/connectors";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Collection<String> parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, Collection.class);
    }
}
