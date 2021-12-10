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

    @Override
    public void getApiEndpoint() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/config";
        final String result = new GetConnectorConfig(inputName).getApiEndpoint();
        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }
}