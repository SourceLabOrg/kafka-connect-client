/**
 * Copyright 2018, 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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
import org.sourcelab.kafka.connect.apiclient.request.dto.Task;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorTasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetConnectorTasksTest extends AbstractRequestTest {
    /**
     * Test Parsing GET /connectors response.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectorTasks.json");
        final List<Task> result = new ArrayList<>(new GetConnectorTasks("MyTestConnector").parseResponse(mockResponse));

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("Should have one entry", 1, result.size());

        assertEquals("Should have connector", result.get(0).getId().getConnector(), "MyTestConnector");
        assertEquals("Should have task id", result.get(0).getId().getTask(), 0);

        // Define our expected result.
        final Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("connector.class", "org.apache.kafka.connect.tools.VerifiableSourceConnector");
        expectedMap.put("task.class", "org.apache.kafka.connect.tools.VerifiableSourceTask");
        expectedMap.put("tasks.max", "1");
        expectedMap.put("topics", "test-topic");
        expectedMap.put("name", "MyTestConnector");
        expectedMap.put("id", "0");

        // Assert it contains the expected values.
        assertEquals("Should have configs", result.get(0).getConfig(), expectedMap);
    }

    @Override
    public void getApiEndpoint() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/tasks";
        final String result = new GetConnectorTasks(inputName).getApiEndpoint();
        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }
}