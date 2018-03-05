package org.sourcelab.kafka.connect.apiclient.request;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Abstract / Base Request Test Class.
 */
abstract public class AbstractRequestTest {

    /**
     * Test Parsing response.
     */
    @Test
    public abstract void testParseResponse() throws Exception;

    /**
     * Utility method to help load mock responses from resources.
     * @param fileName file name to load from resources
     * @return The contents of the file, as a UTF-8 string.
     * @throws IOException on error reading from resource file.
     */
    protected String readFile(final String fileName) throws IOException {
        final URL inputFile = getClass().getClassLoader().getResource("mockResponses/" + fileName);
        return IOUtils.toString(inputFile, StandardCharsets.UTF_8);
    }
}
