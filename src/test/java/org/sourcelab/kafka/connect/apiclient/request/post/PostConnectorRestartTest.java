package org.sourcelab.kafka.connect.apiclient.request.post;

import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.request.AbstractRequestTest;

import static org.junit.Assert.assertEquals;

/**
 *
 */
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
