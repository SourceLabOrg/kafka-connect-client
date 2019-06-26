package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectServerVersion;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectServerVersion;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetConnectServerVersionTest extends AbstractRequestTest {
    /**
     * Test Parsing GET / response.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectServerVersion.json");
        final ConnectServerVersion result = new GetConnectServerVersion().parseResponse(mockResponse);

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("2.1.1", result.getVersion());
        assertEquals("21234bee31165527", result.getCommit());
        assertEquals("Fo2ySm4CT1Wvz4Kvm2jIhw", result.getKafkaClusterId());
    }
}
