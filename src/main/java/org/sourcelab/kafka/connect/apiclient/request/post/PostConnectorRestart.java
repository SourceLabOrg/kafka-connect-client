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

package org.sourcelab.kafka.connect.apiclient.request.post;

import org.sourcelab.kafka.connect.apiclient.util.UrlEscapingUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Defines a request to restart a connector.
 */
public final class PostConnectorRestart implements PostRequest<Boolean> {
    /**
     * Defines the name of the connector to restart.
     */
    private final String connectorName;

    /**
     * Additional options to pass with the request.
     */
    private Map<String, Boolean> options = new HashMap<>();

    /**
     * Constructor.
     * @param connectorName Name of connector to restart
     */
    public PostConnectorRestart(final String connectorName) {
        Objects.requireNonNull(connectorName);
        this.connectorName = connectorName;
    }

    /**
     * Only available from Kafka Connect version 3.0.0 and up.
     * @param includeTasks Specifies whether to restart the connector instance and task instances (includeTasks=true`) or
     *                     just the connector instance (includeTasks=false).
     * @return self reference for method chaining.
     */
    public PostConnectorRestart withIncludeTasks(final boolean includeTasks)
    {
        options.put("includeTasks", includeTasks);
        return this;
    }

    /**
     * Only available from Kafka Connect version 3.0.0 and up.
     * @param onlyFailed specifies whether to restart just the instances with a FAILED status (onlyFailed=true)
     *                   or all instances (onlyFailed=false).
     * @return self reference for method chaining.
     */
    public PostConnectorRestart withOnlyFailed(final boolean onlyFailed)
    {
        options.put("onlyFailed", onlyFailed);
        return this;
    }

    @Override
    public String getApiEndpoint() {
        // Define base URL
        String url = "/connectors/" + UrlEscapingUtil.escapePath(connectorName) + "/restart";

        // Optionally add additional request parameters if explicitly defined.
        final List<String> params = new ArrayList<>();
        for (final Map.Entry<String, Boolean> option : options.entrySet()) {
            // skip null
            if (option.getValue() == null) {
                continue;
            }
            params.add(option.getKey() + "=" + (option.getValue() ? "true" : "false"));
        }
        if (params.size() > 0) {
            url = url + "?" + String.join("&", params);
        }
        return url;
    }

    @Override
    public Object getRequestBody() {
        return "";
    }

    @Override
    public Boolean parseResponse(final String responseStr) throws IOException {
        // Note: this doesn't currently support 202 responses which would normally contain a response body :/
        return true;
    }
}
