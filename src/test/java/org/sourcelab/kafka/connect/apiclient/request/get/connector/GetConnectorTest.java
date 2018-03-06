package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnector;

import static org.junit.Assert.*;

public class GetConnectorTest extends AbstractRequestTest {

    @Override
    public void testParseResponse() throws Exception {
        final String mockResponse = readFile("getConnector.json");
        final ConnectorDefinition result = new GetConnector("My Test Connector").parseResponse(mockResponse);

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("My Test Connector", result.getName());
        assertEquals("unknown", result.getType());

        // validate config
        assertNotNull("Should not be null", result.getConfig());
        assertEquals("Should have 4 entries", 4, result.getConfig().size());
        assertEquals("org.apache.kafka.connect.tools.MockConnector", result.getConfig().get("connector.class"));
        assertEquals("10", result.getConfig().get("tasks.max"));
        assertEquals("test-topic", result.getConfig().get("topics"));
        assertEquals("My Test Connector", result.getConfig().get("name"));

        // Validate tasks
        assertNotNull("Should not be null", result.getTasks());
        assertTrue("Should be empty", result.getTasks().isEmpty());
    }
}