package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Map;

/**
 * Represents Details about a Task.
 */
public final class Task {
    private TaskId id;
    private Map<String, String> config;

    public TaskId getId() {
        return id;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return "Task{"
            + "id=" + id
            + ", config=" + config
            + '}';
    }

    /**
     * Defines a Task Id.
     */
    private static class TaskId {
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
            return "TaskId{"
                + "connector='" + connector + '\''
                + ", task=" + task
                + '}';
        }
    }
}
