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

/**
 * Represents details about the kafka-connect server.
 */
public class ConnectServerVersion {
    private String version;
    private String commit;
    private String kafkaClusterId;

    /**
     * Default Constructor.
     */
    public ConnectServerVersion()
    {
    }

    /**
     * Constructor.
     */
    public ConnectServerVersion(final String version, final String commit, final String kafkaClusterId) {
        this.version = version;
        this.commit = commit;
        this.kafkaClusterId = kafkaClusterId;
    }

    /**
     * Version of running Kafka-Connect server.
     * @return Version of running Kafka-Connect server.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Commit hash of running Kafka-Connect server.
     * @return Commit hash of running Kafka-Connect server.
     */
    public String getCommit() {
        return commit;
    }

    /**
     * Kafka Cluster Identifier.
     * @return Kafka Cluster Identifier.
     */
    public String getKafkaClusterId() {
        return kafkaClusterId;
    }

    @Override
    public String toString() {
        return "ConnectServerVersion{"
            + "version='" + version + '\''
            + ", commit='" + commit + '\''
            + ", kafkaClusterId='" + kafkaClusterId + '\''
            + '}';
    }
}
