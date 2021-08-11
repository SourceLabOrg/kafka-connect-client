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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Deployed Connectors extended with all available associated Metadata.
 *
 * Currently this includes both 'info' and 'status' metadata.
 *
 * Requires Kafka-Connect server 2.3.0+
 */
public class ConnectorsWithExpandedMetadata implements ConnectorsWithExpandedInfo, ConnectorsWithExpandedStatus {
    @JsonAnySetter
    private Map<String, ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata> results = new HashMap<>();

    @Override
    public Collection<String> getConnectorNames() {
        return results.keySet();
    }

    @Override
    public ConnectorDefinition getDefinitionForConnector(final String connectorName) {
        if (!results.containsKey(connectorName)) {
            throw new IllegalArgumentException("Results do not contain connector: " + connectorName);
        }
        return results.get(connectorName).getInfo();
    }

    @Override
    public Collection<ConnectorDefinition> getAllDefinitions() {
        return results
            .values()
            .stream()
            .map(ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata::getInfo)
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, ConnectorDefinition> getMappedDefinitions() {
        return Collections.unmodifiableMap(
            results
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> entry.getValue().getInfo()))
        );
    }

    @Override
    public ConnectorStatus getStatusForConnector(final String connectorName) {
        if (!results.containsKey(connectorName)) {
            throw new IllegalArgumentException("Results do not contain connector: " + connectorName);
        }
        return results.get(connectorName).getStatus();
    }

    @Override
    public Collection<ConnectorStatus> getAllStatuses() {
        return results
            .values()
            .stream()
            .map(ConnectorsWithExpandedMetadata.ConnectorWithExpandedMetadata::getStatus)
            .collect(Collectors.toList());
    }

    @Override
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

    /**
     * Expanded metadata included with the connector response.
     */
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
            String value = "ConnectorMetadata{";
            if (info != null) {
                value += "info=" + info;
                if (status != null) {
                    value += ", ";
                }
            }
            if (status != null) {
                value += "status=" + status;
            }
            value += '}';
            return value;
        }
    }
}
