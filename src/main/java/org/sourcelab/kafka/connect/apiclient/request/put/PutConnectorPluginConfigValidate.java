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

package org.sourcelab.kafka.connect.apiclient.request.put;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.http.rest.request.PutRequest;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.StringBodyContent;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPluginConfigValidationResults;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

/**
 * Defines request to validate a connector plugin's configuration.
 */
public final class PutConnectorPluginConfigValidate implements PutRequest<ConnectorPluginConfigValidationResults> {
    private final String connectorPluginName;
    private final Map<String, String> config;

    /**
     * Constructor.
     * @param connectorPluginName Name of the class for the connector plugin.
     * @param config Configuration entries to validate.
     */
    public PutConnectorPluginConfigValidate(final String connectorPluginName, final Map<String, String> config) {
        Objects.requireNonNull(connectorPluginName);
        Objects.requireNonNull(config);
        this.connectorPluginName = connectorPluginName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    @Override
    public String getApiEndpoint() {
        return "/connector-plugins/" + urlPathSegmentEscaper().escape(connectorPluginName) + "/config/validate";
    }

    @Override
    public RequestBodyContent getRequestBody() {
        try {
            return new StringBodyContent(
                JacksonFactory.newInstance().writeValueAsString(config)
            );
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectorPluginConfigValidationResults parseResponse(final String responseStr) throws IOException {
        return JacksonFactory.newInstance().readValue(responseStr, ConnectorPluginConfigValidationResults.class);
    }
}
