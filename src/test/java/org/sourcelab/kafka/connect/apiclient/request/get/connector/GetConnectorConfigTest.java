package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorConfig;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetConnectorConfigTest extends AbstractRequestTest {

    @Override
    public void testParseResponse() throws Exception {
        final String mockResponse = readFile("getConnectorConfig.json");
        final Map<String, String> result = new GetConnectorConfig("Test Name").parseResponse(mockResponse);

        // validate config
        assertNotNull("Should not be null", result);
        assertEquals("Should have 4 entries", 4, result.size());
        assertEquals("org.apache.kafka.connect.tools.MockConnector", result.get("connector.class"));
        assertEquals("10", result.get("tasks.max"));
        assertEquals("test-topic", result.get("topics"));
        assertEquals("My Test Connector", result.get("name"));
    }
}