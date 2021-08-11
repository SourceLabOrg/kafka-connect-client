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

package org.sourcelab.kafka.connect.apiclient.rest;

import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;
import testserver.TestHttpServer;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class HttpClientRestClientTest {

    private static final int HTTP_PORT = 10880;
    private static final int HTTPS_PORT = 10881;
    private static final String RESPONSE_DATA = "My Test Response";

    private static String KEYSTORE_PATH;
    private static String TRUSTSTORE_PATH;

    private static final String DUMMY_PASSWORD = "password";

    @BeforeClass
    public static void setup() {
        KEYSTORE_PATH = HttpClientRestClientTest.class
            .getClassLoader()
            .getResource("certificates/server.keystore.jks")
            .getFile();

        TRUSTSTORE_PATH = HttpClientRestClientTest.class
            .getClassLoader()
            .getResource("certificates/server.truststore.jks")
            .getFile();
    }

    /**
     * Test against Http server.
     */
    @Test
    public void doHttpTest() throws Exception {

        try (final TestHttpServer httpServer = new TestHttpServer()
            .withHttp(HTTP_PORT)
            .withMockData(RESPONSE_DATA)
            .start()
        ) {

            // Create client
            final Configuration configuration = new Configuration("http://localhost:" + HTTP_PORT);
            final HttpClientRestClient restClient = new HttpClientRestClient();
            restClient.init(configuration);

            // Make request
            final RestResponse result = restClient.submitRequest(new DummyRequest());

            // Validate response.
            assertEquals(RESPONSE_DATA, result.getResponseStr());
        }
    }

    /**
     * Test against Https server.
     */
    @Test
    public void doHttps_noClientValidation_disableCertificateValidation_Test() throws Exception {

        try (final TestHttpServer httpServer = new TestHttpServer()
            .withHttps(KEYSTORE_PATH, DUMMY_PASSWORD, HTTPS_PORT)
            .withMockData(RESPONSE_DATA)
            .start()
        ) {

            final Configuration configuration = new Configuration("https://localhost:" + HTTPS_PORT)
                .useInsecureSslCertificates();

            final HttpClientRestClient restClient = new HttpClientRestClient();
            restClient.init(configuration);

            final RestResponse result = restClient.submitRequest(new DummyRequest());
            assertEquals(RESPONSE_DATA, result.getResponseStr());
        }
    }

    /**
     * Test against Https server with server certificate validation.
     */
    @Test
    public void doHttps_noClientValidation_withCertificateValidation_Test() throws Exception {

        try (final TestHttpServer httpServer = new TestHttpServer()
            .withHttps(KEYSTORE_PATH, DUMMY_PASSWORD, HTTPS_PORT)
            .withMockData(RESPONSE_DATA)
            .start()
        ) {

            final Configuration configuration = new Configuration("https://localhost:" + HTTPS_PORT)
                .useTrustStore(new File(TRUSTSTORE_PATH), DUMMY_PASSWORD);

            final HttpClientRestClient restClient = new HttpClientRestClient();
            restClient.init(configuration);

            final RestResponse result = restClient.submitRequest(new DummyRequest());
            assertEquals(RESPONSE_DATA, result.getResponseStr());
        }
    }

    /**
     * Test against Https server with server certificate and client validation.
     */
    @Test
    public void doHttps_withClientValidation_withCertificateValidation_Test() throws Exception {

        try (final TestHttpServer httpServer = new TestHttpServer()
            .withHttps(KEYSTORE_PATH, DUMMY_PASSWORD, HTTPS_PORT)
            .withValidateClientCertificate(TRUSTSTORE_PATH, DUMMY_PASSWORD)
            .withMockData(RESPONSE_DATA)
            .start()
        ) {

            final Configuration configuration = new Configuration("https://localhost:" + HTTPS_PORT)
                .useTrustStore(new File(TRUSTSTORE_PATH), DUMMY_PASSWORD)
                .useKeyStore(new File(KEYSTORE_PATH), DUMMY_PASSWORD);

            final HttpClientRestClient restClient = new HttpClientRestClient();
            restClient.init(configuration);

            final RestResponse result = restClient.submitRequest(new DummyRequest());
            assertEquals(RESPONSE_DATA, result.getResponseStr());
        }
    }

    /**
     * Verifies that config hooks are called as expected during HttpClientRestClient::init().
     */
    @Test
    public void validateConfigHooks() {
        // Create mock
        final HttpClientConfigHooks mockHooks = spy(DefaultHttpClientConfigHooks.class);

        // Define configuration
        final Configuration configuration = new Configuration("http://localhost:" + HTTP_PORT);

        // Create rest client injecting hooks
        final HttpClientRestClient restClient = new HttpClientRestClient(mockHooks);

        // Call init
        restClient.init(configuration);

        // Verify spy was called as expected

        // HttpClient Builder
        Mockito
            .verify(mockHooks, times(1))
            .createHttpClientBuilder(configuration);
        Mockito
            .verify(mockHooks, times(1))
            .modifyHttpClientBuilder(eq(configuration), any(HttpClientBuilder.class));

        // HttpsContext Builder
        Mockito
            .verify(mockHooks, times(1))
            .createHttpsContextBuilder(configuration);

        // Request Builder
        Mockito
            .verify(mockHooks, times(1))
            .createRequestConfigBuilder(configuration);
        Mockito
            .verify(mockHooks, times(1))
            .modifyRequestConfig(eq(configuration), any(RequestConfig.Builder.class));

        // AuthCache
        Mockito
            .verify(mockHooks, times(1))
            .createAuthCache(configuration);
        Mockito
            .verify(mockHooks, times(1))
            .modifyAuthCache(eq(configuration), any(AuthCache.class));

        // CredentialsProvider
        Mockito
            .verify(mockHooks, times(1))
            .createCredentialsProvider(configuration);
        Mockito
            .verify(mockHooks, times(1))
            .modifyCredentialsProvider(eq(configuration), any(CredentialsProvider.class));

        // Verify we had no other odd interactions.
        verifyNoMoreInteractions(mockHooks);
    }

    /**
     * Verifies that config hooks are called as expected during HttpClientRestClient::createHttpClientContext().
     */
    @Test
    public void verifyHttpClientReturnedByHookIsUsed() throws Exception {
        // Create mock
        final HttpClientConfigHooks mockHooks = spy(DefaultHttpClientConfigHooks.class);

        // Define configuration
        final Configuration configuration = new Configuration("http://localhost:" + HTTP_PORT);

        // Create rest client injecting hooks
        final HttpClientRestClient restClient = new HttpClientRestClient(mockHooks);

        // Call init
        restClient.init(configuration);

        try (final TestHttpServer httpServer = new TestHttpServer()
            .withHttp(HTTP_PORT)
            .withMockData(RESPONSE_DATA)
            .start()
        ) {
            // Make 2 requests
            RestResponse result = restClient.submitRequest(new DummyRequest());
            assertEquals(RESPONSE_DATA, result.getResponseStr());

            result = restClient.submitRequest(new DummyRequest());
            assertEquals(RESPONSE_DATA, result.getResponseStr());
        }

        // Verify hooks on HttpClientContext
        verify(mockHooks, times(2))
            .createHttpClientContext(configuration);
        verify(mockHooks, times(2))
            .modifyHttpClientContext(eq(configuration), any(HttpClientContext.class));
    }

    /**
     * Represents a dummy request.
     */
    private static class DummyRequest implements Request {
        private final String endPoint;
        private final RequestMethod requestMethod;
        private final String requestBody;

        public DummyRequest() {
            this("/", RequestMethod.GET, null);
        }

        public DummyRequest(final String endPoint, final RequestMethod requestMethod, final String requestBody) {
            this.endPoint = endPoint;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }

        @Override
        public String getApiEndpoint() {
            return endPoint;
        }

        @Override
        public RequestMethod getRequestMethod() {
            return requestMethod;
        }

        @Override
        public Object getRequestBody() {
            return requestBody;
        }

        @Override
        public Object parseResponse(final String responseStr) {
            return responseStr;
        }
    }
}
