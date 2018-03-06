package org.sourcelab.kafka.connect.apiclient.request.dto;

/**
 * Represents the Status of a Task.
 */
public class TaskStatus {
    private int id;
    private String state;
    private String trace;
    private String workerId;

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getTrace() {
        return trace;
    }

    public String getWorkerId() {
        return workerId;
    }

    @Override
    public String toString() {
        return "TaskStatus{"
            + "id=" + id
            + ", state='" + state + '\''
            + ", trace='" + trace + '\''
            + ", workerId='" + workerId + '\''
            + '}';
    }
}
