package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a Connector Plugin Configuration.
 */
public final class ConnectorPluginConfigDefinition {
    private final String name;
    private final Map<String, String> config;

    /**
     * Constructor
     * @param connectorPluginName Name of Connector Plugin.
     * @param config Configuration values for connector.
     */
    public ConnectorPluginConfigDefinition(final String connectorPluginName, final Map<String, String> config) {
        this.name = connectorPluginName;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public static ConnectorPluginConfigDefinition.Builder newBuilder() {
        return new ConnectorPluginConfigDefinition.Builder();
    }

    @Override
    public String toString() {
        return "ConnectorPluginConfigDefinition{"
            + "name='" + name + '\''
            + ", config=" + config
            + '}';
    }

    /**
     * Builder for ConnectorPluginConfigDefinition.
     */
    public static final class Builder {
        private String name;
        private Map<String, String> config = new HashMap<>();

        private Builder() {
        }

        public ConnectorPluginConfigDefinition.Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public ConnectorPluginConfigDefinition.Builder withConfig(final Map<String, String> config) {
            this.config = new HashMap<>(config);
            return this;
        }

        public ConnectorPluginConfigDefinition.Builder withConfig(final String key, final String value) {
            this.config.put(key, value);
            return this;
        }

        public ConnectorPluginConfigDefinition.Builder withConfig(final String key, final Object value) {
            this.config.put(key, value.toString());
            return this;
        }

        public ConnectorPluginConfigDefinition build() {
            return new ConnectorPluginConfigDefinition(name, config);
        }
    }
}
