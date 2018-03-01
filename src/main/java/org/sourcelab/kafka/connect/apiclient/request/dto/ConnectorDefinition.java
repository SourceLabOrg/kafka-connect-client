package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectorDefinition {
    private String name;
    private String type;
    private Map<String, String> config;
    private List<TaskDefinition> tasks;

    public ConnectorDefinition() {

    }

    public ConnectorDefinition(final String name, final Map<String, String> config) {
        this.name = name;
        this.config = Collections.unmodifiableMap(new HashMap<>(config));

        // Not used.
        this.type = null;
        this.tasks = Collections.unmodifiableList(new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public List<TaskDefinition> getTasks() {
        return tasks;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "ConnectorDefinition{"
            + "name='" + name + '\''
            + ", type='" + type + '\''
            + ", config=" + config
            + ", tasks=" + tasks
            + '}';
    }

    public static final class Builder {
        private String name;
        private String type = null;
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

        public Builder withType(final String type) {
            this.type = type;
            return this;
        }

        public ConnectorDefinition build() {
            return new ConnectorDefinition(name, config);
        }
    }

    private static class TaskDefinition {
        private String connector;
        private int task;

        public String getConnector() {
            return connector;
        }

        public int getTask() {
            return task;
        }

        @Override
        public String toString() {
            return "TaskDefinition{"
                + "connector='" + connector + '\''
                + ", task=" + task
                + '}';
        }
    }
}
