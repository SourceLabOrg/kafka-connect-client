package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPlugin;

import java.io.IOException;
import java.util.Collection;

/**
 * Defines request to get a list of connector plugins.
 */
public final class GetConnectorPlugins implements GetRequest<Collection<ConnectorPlugin>> {

    @Override
    public String getApiEndpoint() {
        return "/connector-plugins";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Collection<ConnectorPlugin> parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, Collection.class);
    }
}
