/**
 * Copyright 2018, 2019 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.http.rest.request.GetRequest;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.TaskStatus;

import java.io.IOException;
import java.util.Objects;

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
        Objects.requireNonNull(connectorName);
        this.connectorName = connectorName;
        this.taskId = taskId;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/tasks/" + taskId + "/status";
    }

    @Override
    public TaskStatus parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, TaskStatus.class);
    }
}