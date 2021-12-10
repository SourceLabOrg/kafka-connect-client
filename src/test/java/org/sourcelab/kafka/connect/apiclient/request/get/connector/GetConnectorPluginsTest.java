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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPlugin;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnector;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorConfig;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorPlugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetConnectorPluginsTest extends AbstractRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(GetConnectorPluginsTest.class);

    /**
     * Test Parsing GET /connectors response.
     */
    @Test
    public void testParseResponse() throws IOException {
        final String mockResponse = readFile("getConnectorPlugins.json");
        final List<ConnectorPlugin> result = new ArrayList<>(new GetConnectorPlugins().parseResponse(mockResponse));

        // Validate
        assertNotNull("Should not be null", result);
        assertEquals("Should have two entries", 2, result.size());

        assertEquals("Should have connector", result.get(0).getClassName(), "org.apache.kafka.connect.file.FileStreamSinkConnector");
        assertEquals("Should have type", result.get(0).getType(), "sink");
        assertEquals("Should have version", result.get(0).getVersion(), "1.0.0-cp1");

        assertEquals("Should have connector", result.get(1).getClassName(), "org.apache.kafka.connect.file.FileStreamSourceConnector");
        assertEquals("Should have type", result.get(1).getType(), "source");
        assertEquals("Should have version", result.get(1).getVersion(), "1.0.0-cp1");
    }

    @Override
    public void getApiEndpoint() {
        final String expectedUrl = "/connector-plugins";
        final String result = new GetConnectorPlugins().getApiEndpoint();
        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }
}