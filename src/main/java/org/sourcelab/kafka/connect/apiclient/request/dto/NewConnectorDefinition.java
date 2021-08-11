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

package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a new Connector and its configuration to be deployed.
 */
public final class NewConnectorDefinition {
    private final String name;
    private final Map<String, String> config;

    /**
     * Constructor.
     * @param name Name of Connector.
     * @param config Configuration values for connector.
     */
    public NewConnectorDefinition(final String name, final Map<String, String> config) {
        this.name = name;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "NewConnectorDefinition{"
            + "name='" + name + '\''
            + ", config=" + config
            + '}';
    }

    /**
     * Builder for NewConnectorDefinition.
     */
    public static final class Builder {
        private String name;
        private Map<String, String> config = new HashMap<>();

        private Builder() {
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withConfig(final Map<String, String> config) {
            this.config = new HashMap<>(config);
            return this;
        }

        public Builder withConfig(final String key, final String value) {
            this.config.put(key, value);
            return this;
        }

        public Builder withConfig(final String key, final Object value) {
            this.config.put(key, value.toString());
            return this;
        }

        public NewConnectorDefinition build() {
            return new NewConnectorDefinition(name, config);
        }
    }
}
