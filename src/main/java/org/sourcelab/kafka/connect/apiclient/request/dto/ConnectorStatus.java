package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.List;
import java.util.Map;

/**
 * Represents the status of a deployed connector.
 */
public final class ConnectorStatus {
    private String name;
    private Map<String, String> connector;
    private List<TaskStatus> tasks;

    public String getName() {
        return name;
    }

    public Map<String, String> getConnector() {
        return connector;
    }

    public List<TaskStatus> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "ConnectorStatus{"
            + "name='" + name + '\''
            + ", connector=" + connector
            + ", tasks=" + tasks
            + '}';
    }

    /**
     * Defines the status of a Task.
     */
    private static final class TaskStatus {
        private int id;
        private String state;
        private String workerId;
        private String trace;

        public int getId() {
            return id;
        }

        public String getState() {
            return state;
        }

        public String getWorkerId() {
            return workerId;
        }

        public String getTrace() {
            return trace;
        }

        @Override
        public String toString() {
            return "TaskStatus{"
                + "id=" + id
                + ", state='" + state + '\''
                + ", workerId='" + workerId + '\''
                + ", trace='" + trace + '\''
                + '}';
        }
    }
}
