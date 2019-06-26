package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectorsWithExpandedStatus {

    @JsonAnySetter
    private Map<String, ConnectorWithExpandedStatus> results = new HashMap<>();

    public Collection<String> getConnectorNames() {
        return results.keySet();
    }

    public ConnectorStatus getStatusForConnector(final String connectorName) {
        if (!results.containsKey(connectorName)) {
            throw new IllegalArgumentException("Results do not contain connector: " + connectorName);
        }
        return results.get(connectorName).getStatus();
    }

    public Collection<ConnectorStatus> getAllStatuses() {
        return results
            .values()
            .stream()
            .map(ConnectorWithExpandedStatus::getStatus)
            .collect(Collectors.toList());
    }

    public Map<String, ConnectorStatus> getMappedStatuses() {
        return Collections.unmodifiableMap(
            results
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> entry.getValue().getStatus()))
        );
    }

    @Override
    public String toString() {
        return "ConnectorsWithExpandedStatus{"
            + results
            + '}';
    }

    public static class ConnectorWithExpandedStatus {

        @JsonProperty("status")
        private ConnectorStatus status;

        public ConnectorStatus getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "Connector{" +
                "status=" + status +
                '}';
        }
    }
}
