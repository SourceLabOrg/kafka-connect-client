package org.sourcelab.kafka.connect.apiclient.request.post;

import com.google.common.base.Preconditions;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to restart a connector's task.
 */
public final class PostConnectorTaskRestart implements PostRequest<Boolean> {
    private final String connectorName;
    private final int taskId;

    /**
     * Constructor.
     * @param connectorName Name of connector.
     * @param taskId Id of the task.
     */
    public PostConnectorTaskRestart(final String connectorName, final int taskId) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
        this.taskId = taskId;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/tasks/" + taskId + "/restart";
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
