package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectors;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class GetConnectorsTest extends AbstractRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(GetConnectorsTest.class);

    /**
     * Test Parsing GET /connectors response.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectors.json");
        final Collection<String> result = new GetConnectors().parseResponse(mockResponse);

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("Should have two entries", 2, result.size());
        assertTrue("Should have connector", result.contains("My Test Connector"));
        assertTrue("Should have connector", result.contains("My Other Test Connector"));
    }
}