package org.sourcelab.kafka.connect.apiclient.rest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;
import testserver.TestHttpServer;

import java.io.File;

import static org.junit.Assert.assertEquals;

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