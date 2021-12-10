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
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    @Override
    public void getApiEndpoint() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector";
        final String result = new GetConnector(inputName).getApiEndpoint();
        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }
}