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

package org.sourcelab.kafka.connect.apiclient.rest;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;
import testserver.TestHttpServer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
     * Test that the HttpClientRestClient actually uses the builder returned from the
     * {@link HttpClientRestClient#createHttpClientBuilder()} method.
     */
    @Test
    public void doHttp_withCustomHttpClientBuilder() {
        // Create a mock builder and a rest client that uses the mock builder
        final HttpClientBuilder builderMock = mock(HttpClientBuilder.class);
        HttpClientRestClient restClient = new HttpClientRestClient() {
            @Override
            protected HttpClientBuilder createHttpClientBuilder() {
                return builderMock;
            }
        };

        // Init the rest client
        final Configuration configuration = new Configuration("http://localhost:" + HTTP_PORT);
        restClient.init(configuration);

        // Verify the mock was used to create the HttpClient
        verify(builderMock).build();
    }

    /**
     * Test that the every request uses a new HttpClientContext.
     */
    @Test
    public void doHttp_verifyNewHttpContextOnEveryRequest() throws IOException {
        AtomicReference<HttpClientContext> firstContext = new AtomicReference<>();
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        when(httpClientMock.execute(any(HttpUriRequest.class), any(ResponseHandler.class), any(HttpClientContext.class)))
                .then(invocation -> {
                    // Store the context of first request
                    HttpClientContext context = invocation.getArgument(2);
                    firstContext.set(context);
                    return null;
                })
                .then(invocation -> {
                    // Compare the context of second request with the first context
                    HttpClientContext context = invocation.getArgument(2);
                    assertNotSame(context, firstContext.get());
                    return null;
                });

        // Create a mock builder and a rest client that uses the mock builder
        final HttpClientBuilder builderMock = mock(HttpClientBuilder.class);
        HttpClientRestClient restClient = new HttpClientRestClient() {
            @Override
            protected HttpClientBuilder createHttpClientBuilder() {
                return builderMock;
            }
        };
        when(builderMock.build()).thenReturn(httpClientMock);

        // Init the rest client
        final Configuration configuration = new Configuration("http://localhost:" + HTTP_PORT);
        restClient.init(configuration);

        restClient.submitRequest(new DummyRequest());
        restClient.submitRequest(new DummyRequest());

        verify(httpClientMock, times(2))
                .execute(any(HttpUriRequest.class), any(ResponseHandler.class), any(HttpClientContext.class));
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
