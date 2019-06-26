package org.sourcelab.kafka.connect.apiclient.request.dto;

/**
 * Represents details about the kafka-connect server.
 */
public class ConnectServerVersion {
    private String version;
    private String commit;
    private String kafkaClusterId;

    public String getVersion() {
        return version;
    }

    public String getCommit() {
        return commit;
    }

    public String getKafkaClusterId() {
        return kafkaClusterId;
    }

    @Override
    public String toString() {
        return "ConnectServerVersion{" +
            "version='" + version + '\'' +
            ", commit='" + commit + '\'' +
            ", kafkaClusterId='" + kafkaClusterId + '\'' +
            '}';
    }
}
