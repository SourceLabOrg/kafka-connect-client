/**
 * Copyright 2018 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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

package org.sourcelab.kafka.connect.apiclient.rest;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.Configuration;

import javax.net.ssl.HostnameVerifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HttpsContextBuilderTest {

    /**
     * Constructor should require non-null arguments.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullArguments() {
        new HttpsContextBuilder(null);
    }

    /**
     * When configured to validate SSL certificates, should not get NoopHostnameVerifier back.
     */
    @Test
    public void getHostnameVerifier_validateSslCertificates() {
        final HttpsContextBuilder builder = new HttpsContextBuilder(new Configuration("http://localhost"));

        final HostnameVerifier verifier = builder.getHostnameVerifier();
        assertNotNull(verifier);
        assertFalse("Should not be an instance of NoopHostnameVerifier", verifier instanceof NoopHostnameVerifier);
    }

    /**
     * When configured to skip validating SSL certificates, should get NoopHostnameVerifier back.
     */
    @Test
    public void getHostnameVerifier_acceptInvalidSslCertificates() {
        final HttpsContextBuilder builder = new HttpsContextBuilder(
            new Configuration("http://localhost").useInsecureSslCertificates()
        );

        final HostnameVerifier verifier = builder.getHostnameVerifier();
        assertNotNull(verifier);
        assertTrue("Should be an instance of NoopHostnameVerifier", verifier instanceof NoopHostnameVerifier);
    }
}
