/**
 * Copyright 2018, 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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

import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorTopics;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Returns a list of connector topic names.
 * There is no defined order in which the topics are returned and consecutive calls may return the same topic names
 * but in different order. This request is independent of whether a connector is running, and will return an empty set
 * of topics, both for connectors that don’t have active topics as well as non-existent connectors.
 *
 * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)-topics
 */
public class GetConnectorTopics implements GetRequest<ConnectorTopics> {
    private final String connectorName;

    /**
     * Constructor.
     * @param connectorName Name of connector.
     */
    public GetConnectorTopics(final String connectorName) {
        Objects.requireNonNull(connectorName);
        this.connectorName = connectorName;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors/" + urlPathSegmentEscaper().escape(connectorName) + "/topics";
    }

    @Override
    public ConnectorTopics parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorTopics.class);
    }
}
