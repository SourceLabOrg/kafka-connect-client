/**
 * Copyright 2018, 2019 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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

package org.sourcelab.kafka.connect.apiclient.request.get.connector;

import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.exception.ResponseParseException;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedInfo;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorsExpandInfo;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GetConnectorsWithExpandInfoTest extends AbstractRequestTest {

    /**
     * Test Parsing GET /connectors response with expanded info metadata.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectorsWithExpandedInfo.json");
        final ConnectorsWithExpandedInfo result = new GetConnectorsExpandInfo().parseResponse(mockResponse);

        // High level null checks
        assertNotNull("Should not be null", result);
        assertNotNull(result.getConnectorNames());
        assertNotNull(result.getMappedDefinitions());
        assertNotNull(result.getAllDefinitions());

        // High level count checks
        assertEquals(2, result.getConnectorNames().size());
        assertEquals(2, result.getMappedDefinitions().size());
        assertEquals(2, result.getAllDefinitions().size());

        // Validate 'MyTestConnector'
        assertTrue(result.getConnectorNames().contains("MyTestConnector"));
        assertTrue(result.getMappedDefinitions().containsKey("MyTestConnector"));
        validateTestConnectorDefinition(result.getDefinitionForConnector("MyTestConnector"));
        validateTestConnectorDefinition(result.getMappedDefinitions().get("MyTestConnector"));

        // Validate 'MyTestConnector2'
        assertTrue(result.getConnectorNames().contains("MyTestConnector2"));
        assertTrue(result.getMappedDefinitions().containsKey("MyTestConnector2"));
        validateTestConnectorDefinition2(result.getDefinitionForConnector("MyTestConnector2"));
        validateTestConnectorDefinition2(result.getMappedDefinitions().get("MyTestConnector2"));
    }

    /**
     * Test what happens if we get back a pre 2.3.0 response for this request.
     *
     * It should throw a ResponseParseException.
     */
    @Test(expected = ResponseParseException.class)
    public void testParseResponseForKafkaConnectVersionEarlierThan2_3_0() throws IOException {
        final String mockResponse = readFile("getConnector.json");
        final ConnectorsWithExpandedInfo result = new GetConnectorsExpandInfo().parseResponse(mockResponse);
    }

    private void validateTestConnectorDefinition(final ConnectorDefinition connector) {
        final String expectedConnectorName = "MyTestConnector";

        assertNotNull(connector);
        assertEquals("Should have correct name", expectedConnectorName, connector.getName());
        assertEquals("Should have correct type", "source", connector.getType());

        // Validate config
        assertNotNull("Config should not be null", connector.getConfig());
        assertEquals("org.apache.kafka.connect.tools.VerifiableSourceConnector", connector.getConfig().get("connector.class"));
        assertEquals("3", connector.getConfig().get("tasks.max"));
        assertEquals("test-topic", connector.getConfig().get("topics"));
        assertEquals(expectedConnectorName, connector.getConfig().get("name"));

        // Validate tasks
        final List<ConnectorDefinition.TaskDefinition> taskDefinitions = connector.getTasks();
        assertNotNull("Tasks should not be null", taskDefinitions);
        assertEquals(3, taskDefinitions.size());

        for (int taskId=0; taskId<taskDefinitions.size(); taskId++) {
            assertEquals(expectedConnectorName, taskDefinitions.get(taskId).getConnector());
            assertEquals(taskId, taskDefinitions.get(taskId).getTask());
        }
    }

    private void validateTestConnectorDefinition2(final ConnectorDefinition connector) {
        final String expectedConnectorName = "MyTestConnector2";

        assertNotNull(connector);
        assertEquals("Should have correct name", expectedConnectorName, connector.getName());
        assertEquals("Should have correct type", "source", connector.getType());

        // Validate config
        assertNotNull("Config should not be null", connector.getConfig());
        assertEquals("org.apache.kafka.connect.tools.SomeOtherConnectorClass", connector.getConfig().get("connector.class"));
        assertEquals("1", connector.getConfig().get("tasks.max"));
        assertEquals("another-topic", connector.getConfig().get("topics"));
        assertEquals(expectedConnectorName, connector.getConfig().get("name"));

        // Validate tasks
        final List<ConnectorDefinition.TaskDefinition> taskDefinitions = connector.getTasks();
        assertNotNull("Tasks should not be null", taskDefinitions);
        assertEquals(1, taskDefinitions.size());

        for (int taskId=0; taskId<taskDefinitions.size(); taskId++) {
            assertEquals(expectedConnectorName, taskDefinitions.get(taskId).getConnector());
            assertEquals(taskId, taskDefinitions.get(taskId).getTask());
        }
    }
}