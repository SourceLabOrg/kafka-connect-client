package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Collection;
import java.util.Map;

/**
 * Deployed Connectors extended with their associated ConnectorDefinitions.
 *
 * Requires Kafka-Connect server 2.3.0+
 */
public interface ConnectorsWithExpandedStatus {

    /**
     * Names for all deployed connectors.
     * @return Names of all deployed connectors.
     */
    Collection<String> getConnectorNames();

    /**
     * Given a connector name, return the status for the connector.
     * @param connectorName name of connector to return status for.
     * @return ConnectorStatus for the given connector name.
     * @throws IllegalArgumentException if passed a connector name not included in the results.
     */
    ConnectorStatus getStatusForConnector(final String connectorName);

    /**
     * All connector statuses.
     * @return All connector statuses.
     */
    Collection<ConnectorStatus> getAllStatuses();

    /**
     * Map of ConnectorName to its respective Status.
     * @return Map of ConnectorName to its respective Status.
     */
    Map<String, ConnectorStatus> getMappedStatuses();
}
