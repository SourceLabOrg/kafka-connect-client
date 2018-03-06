package org.sourcelab.kafka.connect.apiclient.request.get;

import com.sun.xml.internal.rngom.util.Uri;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.Task;

import java.io.IOException;
import java.util.Collection;

/**
 * Defines request to get tasks for a connector.
 */
public final class GetConnectorTasks implements GetRequest<Collection<Task>> {

    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName name of the connector.
     */
    public GetConnectorTasks(final String connectorName) {
        if (connectorName == null) {
            throw new NullPointerException("connectorName parameter may not be null!");
        }
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + Uri.escapeDisallowedChars(connectorName) + "/tasks";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Collection<Task> parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, Collection.class);
    }
}
