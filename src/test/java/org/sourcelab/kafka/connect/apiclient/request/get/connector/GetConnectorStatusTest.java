package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnector;

import static org.junit.Assert.*;

public class GetConnectorStatusTest extends AbstractRequestTest {

    @Override
    public void testParseResponse() throws Exception {
        final String mockResponse = readFile("getConnectorStatus.json");
        final ConnectorDefinition result = new GetConnector("My Test Connector").parseResponse(mockResponse);

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("My Test Connector", result.getName());
        assertEquals("unknown", result.getType());

        // validate config
        assertNull("Should be null", result.getConfig());

        // Validate tasks
        assertNotNull("Should not be null", result.getTasks());
        assertTrue("Should be empty", result.getTasks().isEmpty());
    }
}