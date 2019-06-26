package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectorsWithExpandedMetadata {
    @JsonAnySetter
    private Map<String, ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata> results = new HashMap<>();

    public Collection<String> getConnectorNames() {
        return results.keySet();
    }

    public ConnectorDefinition getDefinitionForConnector(final String connectorName) {
        if (!results.containsKey(connectorName)) {
            throw new IllegalArgumentException("Results do not contain connector: " + connectorName);
        }
        return results.get(connectorName).getInfo();
    }

    public Collection<ConnectorDefinition> getAllDefinitions() {
        return results
            .values()
            .stream()
            .map(ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata::getInfo)
            .collect(Collectors.toList());
    }

    public Map<String, ConnectorDefinition> getMappedDefinitions() {
        return Collections.unmodifiableMap(
            results
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> entry.getValue().getInfo()))
        );
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
            .map(ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata::getStatus)
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
        return "ConnectorsWithExpandedMetadata{"
            + results
            + '}';
    }

    public static class ConnectorWithExpandedMetadata {

        @JsonProperty("info")
        private ConnectorDefinition info;

        @JsonProperty("status")
        private ConnectorStatus status;

        public ConnectorStatus getStatus() {
            return status;
        }

        public ConnectorDefinition getInfo() {
            return info;
        }

        @Override
        public String toString() {
            return "Connector{" +
                "info=" + info +
                ", status=" + status +
                '}';
        }
    }
}
