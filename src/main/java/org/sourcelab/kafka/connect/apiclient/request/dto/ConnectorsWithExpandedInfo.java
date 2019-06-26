package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectorsWithExpandedInfo {
    @JsonAnySetter
    private Map<String, ConnectorsWithExpandedInfo.ConnectorWithExpandedInfo> results = new HashMap<>();

    public Collection<String> getConnectorNames() {
        return results.keySet();
    }

    public ConnectorDefinition getDefinitionForConnector(final String connectorName) {
        if (!results.containsKey(connectorName)) {
            throw new IllegalArgumentException("Results do not contain connector: " + connectorName);
        }
        return results.get(connectorName).getInfo();
    }

    public Collection<ConnectorDefinition> getAllStatuses() {
        return results
            .values()
            .stream()
            .map(ConnectorsWithExpandedInfo.ConnectorWithExpandedInfo::getInfo)
            .collect(Collectors.toList());
    }

    public Map<String, ConnectorDefinition> getMappedStatuses() {
        return Collections.unmodifiableMap(
            results
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> entry.getValue().getInfo()))
        );
    }

    @Override
    public String toString() {
        return "ConnectorsWithExpandedInfo{"
            + results
            + '}';
    }

    public static class ConnectorWithExpandedInfo {

        @JsonProperty("info")
        private ConnectorDefinition info;

        public ConnectorDefinition getInfo() {
            return info;
        }

        @Override
        public String toString() {
            return "Connector{" +
                "info=" + info +
                '}';
        }
    }
}
