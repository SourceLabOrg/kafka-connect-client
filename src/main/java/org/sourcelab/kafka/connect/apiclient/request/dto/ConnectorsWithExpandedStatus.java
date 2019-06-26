package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.Collection;
import java.util.Map;

public interface ConnectorsWithExpandedStatus {

    Collection<String> getConnectorNames();

    ConnectorStatus getStatusForConnector(final String connectorName);

    Collection<ConnectorStatus> getAllStatuses();

    Map<String, ConnectorStatus> getMappedStatuses();
}
