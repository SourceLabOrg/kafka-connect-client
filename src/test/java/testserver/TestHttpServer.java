package testserver;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.apache.commons.io.IOUtils.write;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Simple Test Http/Https server to validate http clients against.
 */
public class TestHttpServer implements AutoCloseable {

    private Server jettyServer;

    // last Request
    private RequestProperties lastRequest = null;

    // Mocked Responses
    private String responseBody;
    private String mockResponseData;

    // Http options
    private Integer httpPort = null;

    // Https options
    private Integer httpsPort = null;
    private String trustStoreFile = null;
    private String trustStorePassword = null;

    // Client Certificate
    private String keyStoreFile = null;
    private String keyStorePassword = null;

    public TestHttpServer() {
    }

    public TestHttpServer withHttp(final int port) {
        this.httpPort = port;
        return this;
    }

    public TestHttpServer withHttps(final String keyStoreFile, final String keyStorePassword, final int port) {
        this.httpsPort = port;
        this.keyStoreFile = keyStoreFile;
        this.keyStorePassword = keyStorePassword;

        return this;
    }

    public TestHttpServer withValidateClientCertificate(final String trustStoreFile, final String trustStorePassword) {
        this.trustStoreFile = trustStoreFile;
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    public TestHttpServer withMockData(final String mockResponseData) {
        setMockResponseData(mockResponseData);
        return this;
    }

    public TestHttpServer start() throws Exception {
        configureServer();
        jettyServer.start();
        return this;
    }


    private void configureServer() {
        jettyServer = new Server();

        final HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(8443);
        httpConfig.setOutputBufferSize(32768);

        // Setup http connector
        if (httpPort != null) {
            final ServerConnector httpConnector = new ServerConnector(jettyServer, new HttpConnectionFactory(httpConfig));
            httpConnector.setPort(httpPort);
            jettyServer.addConnector(httpConnector);
        }

        // Setup https connector
        if (httpsPort != null) {
            // SSL Context Factory for HTTPS and SPDY
            // SSL requires a certificate so we configure a factory for ssl contents with information pointing to what
            // keystore the ssl connection needs to know about. Much more configuration is available the ssl context,
            // including things like choosing the particular certificate out of a keystore to be used.
            final SslContextFactory sslContextFactory = new SslContextFactory();

            // define server side key
            sslContextFactory.setKeyStorePath(keyStoreFile);
            sslContextFactory.setKeyStorePassword(keyStorePassword);

            if (trustStoreFile != null) {
                sslContextFactory.setTrustStorePath(trustStoreFile);
                sslContextFactory.setTrustStorePassword(trustStorePassword);
                sslContextFactory.setNeedClientAuth(true);
            } else {
                sslContextFactory.setNeedClientAuth(false);
            }

            // HTTPS Configuration
            // A new HttpConfiguration object is needed for the next connector and you can pass the old one as an
            // argument to effectively clone the contents. On this HttpConfiguration object we add a
            // SecureRequestCustomizer which is how a new connector is able to resolve the https connection before
            // handing control over to the Jetty Server.
            final HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
            httpsConfig.addCustomizer(new SecureRequestCustomizer());

            // HTTPS connector
            // We create a second ServerConnector, passing in the http configuration we just made along with the
            // previously created ssl context factory. Next we set the port and a longer idle timeout.
            ServerConnector httpsConnector = new ServerConnector(jettyServer,
                new SslConnectionFactory(sslContextFactory,"http/1.1"),
                new HttpConnectionFactory(httpsConfig));
            httpsConnector.setPort(httpsPort);

            jettyServer.addConnector(httpsConnector);
        }

        jettyServer.setHandler(getMockHandler());
    }

    /**
     * Creates an {@link AbstractHandler handler} returning an arbitrary String as a response.
     *
     * @return never <code>null</code>.
     */
    public Handler getMockHandler() {
        return new AbstractHandler() {

            @Override
            public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
                lastRequest = new RequestProperties(
                    target,
                    IOUtils.toString(baseRequest.getInputStream(), Charset.forName("UTF-8")),
                    request.getMethod()
                );

                setResponseBody(getMockResponseData());
                response.setStatus(SC_OK);
                response.setContentType("text/json;charset=utf-8");
                write(getResponseBody(), response.getOutputStream(), Charset.forName("UTF-8"));
                baseRequest.setHandled(true);
            }

        };
    }

    public void stop() throws Exception {
        jettyServer.stop();
    }

    public void setResponseBody(final String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public RequestProperties getLastRequest() {
        return this.lastRequest;
    }

    public void setMockResponseData(final String mockResponseData) {
        this.mockResponseData = mockResponseData;
    }

    public String getMockResponseData() {
        return mockResponseData;
    }

    @Override
    public void close() throws Exception {
        stop();
    }
}
