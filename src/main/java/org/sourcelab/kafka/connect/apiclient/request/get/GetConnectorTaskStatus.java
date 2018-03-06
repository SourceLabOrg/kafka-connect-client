package org.sourcelab.kafka.connect.apiclient.request.get;

import com.google.common.base.Preconditions;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.TaskStatus;

import java.io.IOException;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines a request to get the status of a connector's task.
 */
public final class GetConnectorTaskStatus implements GetRequest<TaskStatus> {

    private final String connectorName;
    private final int taskId;

    /**
     * Constructor.
     * @param connectorName Name of the connector.
     * @param taskId Task id.
     */
    public GetConnectorTaskStatus(final String connectorName, final int taskId) {
        Preconditions.checkNotNull(connectorName);
        this.connectorName = connectorName;
        this.taskId = taskId;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/tasks/" + taskId + "/status";
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public TaskStatus parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, TaskStatus.class);
    }
}