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
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedInfo;
import org.sourcelab.kafka.connect.apiclient.request.get.GetConnectorsExpandInfo;

import java.io.IOException;

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

        // Validate
        assertNotNull("Should not be null", result);

        // TODO write test
        assertTrue(false);
    }
}