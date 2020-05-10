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
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorTopics;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorTopics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GetConnectorTopicsTest extends AbstractRequestTest {

    @Test
    public void getApiEndpoint() {
        final String name = "This is My Connector name";
        final String expectedResult = "/connectors/This%20is%20My%20Connector%20name/topics";
        final String result = new GetConnectorTopics(name).getApiEndpoint();
        assertEquals(expectedResult, result);
    }

    @Test
    @Override
    public void testParseResponse() throws Exception {
        final String mockResponse = readFile("getConnectorTopics.json");
        final ConnectorTopics result =  new GetConnectorTopics("MyTestConnector").parseResponse(mockResponse);
        assertNotNull(result);
        assertEquals("MyTestConnector", result.getName());
        assertNotNull(result.getTopics());
        assertFalse("Should NOT be empty", result.getTopics().isEmpty());
        assertEquals("test-topic-1", result.getTopics().get(0));
        assertEquals("test-topic-2", result.getTopics().get(1));
        assertEquals("test-topic-3", result.getTopics().get(2));
    }

    @Test
    public void testParseResponse_emptyTopics() throws Exception {
        final String mockResponse = readFile("getConnectorTopics_empty.json");
        final ConnectorTopics result =  new GetConnectorTopics("MyTestConnector").parseResponse(mockResponse);
        assertNotNull(result);
        assertEquals("MyTestConnector", result.getName());
        assertNotNull(result.getTopics());
        assertTrue("Should be empty", result.getTopics().isEmpty());
    }
}