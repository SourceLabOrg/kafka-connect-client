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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the status of a deployed connector.
 */
public class ConnectorStatus {
    private String name;
    private String type;
    private Map<String, String> connector;
    private List<TaskStatus> tasks;

    /**
     * Default constructor.
     */
    public ConnectorStatus() {
    }

    /**
     * Constructor.
     */
    public ConnectorStatus(final String name, final String type, final Map<String, String> connector, final List<TaskStatus> tasks) {
        this.name = name;
        this.type = type;
        this.connector = new HashMap<>(connector);
        this.tasks = new ArrayList<>(tasks);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getConnector() {
        return Collections.unmodifiableMap(connector);
    }

    public List<TaskStatus> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ConnectorStatus{"
            + "name='" + name + '\''
            + ", connector=" + connector
            + ", tasks=" + tasks
            + ", type=" + type
            + '}';
    }

    /**
     * Defines the status of a Task.
     */
    public static class TaskStatus {
        private int id;
        private String state;
        private String workerId;
        private String trace;

        /**
         * Default constructor.
         */
        public TaskStatus() {
        }

        /**
         * Constructor.
         */
        public TaskStatus(final int id, final String state, final String workerId, final String trace) {
            this.id = id;
            this.state = state;
            this.workerId = workerId;
            this.trace = trace;
        }

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
