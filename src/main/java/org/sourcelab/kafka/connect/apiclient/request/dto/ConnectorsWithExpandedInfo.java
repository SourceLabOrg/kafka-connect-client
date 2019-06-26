package org.sourcelab.kafka.connect.apiclient.request.dto;


import java.util.Collection;
import java.util.Map;

public interface ConnectorsWithExpandedInfo {

    Collection<String> getConnectorNames();

    ConnectorDefinition getDefinitionForConnector(final String connectorName);

    Collection<ConnectorDefinition> getAllDefinitions();

    Map<String, ConnectorDefinition> getMappedDefinitions();
}
