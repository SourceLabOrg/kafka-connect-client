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

package org.sourcelab.kafka.connect.apiclient.request.post;

import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;

import static org.junit.Assert.assertEquals;

public class PostConnectorRestartTest extends AbstractRequestTest {
    @Override
    public void testParseResponse() throws Exception {

    }

    /**
     * With no optional parameters.
     */
    @Override
    public void getApiEndpoint() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart";
        final String result = new PostConnectorRestart(inputName).getApiEndpoint();
        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }

    /**
     * Verify with various optional parameters specified.
     */
    @Test
    public void getApiEndpoint_includeTasks_true() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart?includeTasks=true";
        final String result = new PostConnectorRestart(inputName)
            .withIncludeTasks(true)
            .getApiEndpoint();

        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }

    /**
     * Verify with various optional parameters specified.
     */
    @Test
    public void getApiEndpoint_includeTasks_false() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart?includeTasks=false";
        final String result = new PostConnectorRestart(inputName)
            .withIncludeTasks(false)
            .getApiEndpoint();

        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }

    /**
     * Verify with various optional parameters specified.
     */
    @Test
    public void getApiEndpoint_onlyFailed_true() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart?onlyFailed=true";
        final String result = new PostConnectorRestart(inputName)
            .withOnlyFailed(true)
            .getApiEndpoint();

        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }

    /**
     * Verify with various optional parameters specified.
     */
    @Test
    public void getApiEndpoint_onlyFailed_false() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart?onlyFailed=false";
        final String result = new PostConnectorRestart(inputName)
            .withOnlyFailed(false)
            .getApiEndpoint();

        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }

    /**
     * Verify with various optional parameters specified.
     */
    @Test
    public void getApiEndpoint_onlyFailed_includeTasks_true() {
        final String inputName = "My Test Connector";
        final String expectedUrl = "/connectors/My%20Test%20Connector/restart?includeTasks=true&onlyFailed=true";
        final String result = new PostConnectorRestart(inputName)
            .withOnlyFailed(true)
            .withIncludeTasks(true)
            .getApiEndpoint();

        assertEquals("Unexpected URL returned!", expectedUrl, result);
    }
}
