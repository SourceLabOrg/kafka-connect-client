package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.List;
import java.util.Map;

/**
 * Represents a Connector.
 */
public final class ConnectorDefinition {
    private String name;
    private String type;
    private Map<String, String> config;
    private List<TaskDefinition> tasks;

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

    @Override
    public String toString() {
        return "ConnectorDefinition{"
            + "name='" + name + '\''
            + ", type='" + type + '\''
            + ", config=" + config
            + ", tasks=" + tasks
            + '}';
    }

    /**
     * Represents a Task.
     */
    private static final class TaskDefinition {
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
