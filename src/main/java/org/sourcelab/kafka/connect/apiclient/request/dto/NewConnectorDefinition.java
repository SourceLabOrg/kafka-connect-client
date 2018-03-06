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
     * Constructor
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
