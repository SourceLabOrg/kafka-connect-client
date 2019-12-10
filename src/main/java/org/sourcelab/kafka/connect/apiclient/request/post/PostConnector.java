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

package org.sourcelab.kafka.connect.apiclient.request.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.http.rest.request.PostRequest;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.StringBodyContent;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.dto.NewConnectorDefinition;

import java.io.IOException;
import java.util.Objects;

/**
 * Defines request to deploy a new connector.
 */
public final class PostConnector implements PostRequest<ConnectorDefinition> {
    private final NewConnectorDefinition connectorDefinition;

    /**
     * Constructor.
     * @param connectorDefinition Defines the new connector to be deployed.
     */
    public PostConnector(final NewConnectorDefinition connectorDefinition) {
        Objects.requireNonNull(connectorDefinition);
        this.connectorDefinition = connectorDefinition;
    }

    @Override
    public String getApiEndpoint() {
        return "/connectors";
    }

    @Override
    public RequestBodyContent getRequestBody() {
        try {
            return new StringBodyContent(
                JacksonFactory.newInstance().writeValueAsString(connectorDefinition)
            );
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectorDefinition parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorDefinition.class);
    }
}
