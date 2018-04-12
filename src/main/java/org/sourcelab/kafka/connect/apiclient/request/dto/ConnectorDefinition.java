/**
 * Copyright 2018 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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
    public static final class TaskDefinition {
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
