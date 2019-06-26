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
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorStatus;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedMetadata;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorsExpandAllDetails;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GetConnectorsExpandAllDetailsTest extends AbstractRequestTest {

    /**
     * Test Parsing GET /connectors response with all known metadata.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectorsWithAllExpandedMetadata.json");
        final ConnectorsWithExpandedMetadata result = new GetConnectorsExpandAllDetails().parseResponse(mockResponse);

        // Validate
        assertNotNull("Should not be null", result);

        // Validate High Level Checks
        assertNotNull("Should not be null", result);
        assertNotNull(result.getAllStatuses());
        assertNotNull(result.getConnectorNames());
        assertNotNull(result.getMappedStatuses());

        // High level count checks
        assertEquals(2, result.getConnectorNames().size());
        assertEquals(2, result.getAllStatuses().size());
        assertEquals(2, result.getMappedStatuses().size());

        // Validate 'MyTestConnector'
        assertTrue(result.getConnectorNames().contains("MyTestConnector"));
        assertTrue(result.getMappedStatuses().containsKey("MyTestConnector"));
        validateTestConnectorStatus(result.getStatusForConnector("MyTestConnector"));
        validateTestConnectorStatus(result.getMappedStatuses().get("MyTestConnector"));
        assertTrue(result.getConnectorNames().contains("MyTestConnector"));
        assertTrue(result.getMappedDefinitions().containsKey("MyTestConnector"));
        validateTestConnectorDefinition(result.getDefinitionForConnector("MyTestConnector"));
        validateTestConnectorDefinition(result.getMappedDefinitions().get("MyTestConnector"));

        // Validate 'MyTestConnector2'
        assertTrue(result.getConnectorNames().contains("MyTestConnector2"));
        assertTrue(result.getMappedStatuses().containsKey("MyTestConnector2"));
        validateTestConnectorStatus2(result.getStatusForConnector("MyTestConnector2"));
        validateTestConnectorStatus2(result.getMappedStatuses().get("MyTestConnector2"));
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
        final ConnectorsWithExpandedMetadata result = new GetConnectorsExpandAllDetails().parseResponse(mockResponse);
    }

    private void validateTestConnectorDefinition(final ConnectorDefinition connector) {
        final String expectedConnectorName = "MyTestConnector";

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

    private void validateTestConnectorStatus(final ConnectorStatus connectorStatus) {
        final String expectedConnectorName = "MyTestConnector";

        assertNotNull(connectorStatus);
        assertEquals(expectedConnectorName, connectorStatus.getName());
        assertEquals("source", connectorStatus.getType());
        assertNotNull(connectorStatus.getConnector());
        assertNotNull(connectorStatus.getTasks());

        // Validate connector
        assertEquals("RUNNING", connectorStatus.getConnector().get("state"));
        assertEquals("127.0.0.1:8083", connectorStatus.getConnector().get("worker_id"));

        // Validate tasks
        assertEquals(3, connectorStatus.getTasks().size());

        assertEquals(0, connectorStatus.getTasks().get(0).getId());
        assertEquals("FAILED", connectorStatus.getTasks().get(0).getState());
        assertEquals("trace0", connectorStatus.getTasks().get(0).getTrace());
        assertEquals("127.0.0.1:8083", connectorStatus.getTasks().get(0).getWorkerId());

        assertEquals(1, connectorStatus.getTasks().get(1).getId());
        assertEquals("RUNNING", connectorStatus.getTasks().get(1).getState());
        assertEquals("trace1", connectorStatus.getTasks().get(1).getTrace());
        assertEquals("127.0.0.1:8083", connectorStatus.getTasks().get(1).getWorkerId());

        assertEquals(2, connectorStatus.getTasks().get(2).getId());
        assertEquals("PAUSED", connectorStatus.getTasks().get(2).getState());
        assertEquals("trace2", connectorStatus.getTasks().get(2).getTrace());
        assertEquals("127.0.0.1:8083", connectorStatus.getTasks().get(2).getWorkerId());
    }

    private void validateTestConnectorDefinition2(final ConnectorDefinition connector) {
        final String expectedConnectorName = "MyTestConnector2";

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

    private void validateTestConnectorStatus2(final ConnectorStatus connectorStatus) {
        final String expectedConnectorName = "MyTestConnector2";

        assertNotNull(connectorStatus);
        assertEquals(expectedConnectorName, connectorStatus.getName());
        assertEquals("source", connectorStatus.getType());
        assertNotNull(connectorStatus.getConnector());
        assertNotNull(connectorStatus.getTasks());

        // Validate connector
        assertEquals("RUNNING", connectorStatus.getConnector().get("state"));
        assertEquals("127.0.0.1:8083", connectorStatus.getConnector().get("worker_id"));

        // Validate tasks
        assertEquals(1, connectorStatus.getTasks().size());

        assertEquals(0, connectorStatus.getTasks().get(0).getId());
        assertEquals("RUNNING", connectorStatus.getTasks().get(0).getState());
        assertEquals("trace0", connectorStatus.getTasks().get(0).getTrace());
        assertEquals("127.0.0.1:8083", connectorStatus.getTasks().get(0).getWorkerId());
    }
}