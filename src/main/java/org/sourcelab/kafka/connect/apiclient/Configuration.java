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

package org.sourcelab.kafka.connect.apiclient;

import java.io.File;
import java.util.Objects;

/**
 * Configure your Kafka Connect API client.
 *
 * Also allows for configuring an optional proxy with or without authentication.
 */
public final class Configuration {
    // Defines the URL/Hostname of Kafka-Connect
    private final String apiHost;

    // Optional Connection settings
    private int requestTimeoutInSeconds = 300;

    // Optional SSL options
    private boolean ignoreInvalidSslCertificates = false;
    private File trustStoreFile = null;
    private String trustStorePassword = null;

    // Optional Proxy Configuration
    private String proxyHost = null;
    private int proxyPort = 0;
    private String proxyScheme = "HTTP";

    // Optional Proxy Authentication.
    private String proxyUsername = null;
    private String proxyPassword = null;

    /**
     * Default Constructor.
     * @param kafkaConnectHost Hostname of Kafka-Connect
     */
    public Configuration(final String kafkaConnectHost) {
        if (kafkaConnectHost == null) {
            throw new NullPointerException("Kafka Connect Host parameter cannot be null!");
        }

        // Normalize into "http://<hostname>"
        if (kafkaConnectHost.startsWith("http://") || kafkaConnectHost.startsWith("https://")) {
            this.apiHost = kafkaConnectHost;
        } else {
            // Assume http protocol
            this.apiHost = "http://" + kafkaConnectHost;
        }
    }

    /**
     * Allow setting optional proxy configuration.
     *
     * @param proxyHost Host for the proxy to use.
     * @param proxyPort Post for the proxy to use.
     * @param proxyScheme Scheme to use, HTTP/HTTPS
     * @return Configuration instance.
     */
    public Configuration useProxy(final String proxyHost, final int proxyPort, final String proxyScheme) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyScheme = proxyScheme;
        return this;
    }

    /**
     * Allow setting credentials for a proxy that requires authentication.
     *
     * @param proxyUsername Username for proxy.
     * @param proxyPassword Password for proxy.
     * @return Configuration instance.
     */
    public Configuration useProxyAuthentication(final String proxyUsername, final String proxyPassword) {
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        return this;
    }

    /**
     * Skip all validation of SSL Certificates.  This is insecure and highly discouraged!
     *
     * @return Configuration instance.
     */
    public Configuration useInsecureSslCertificates() {
        this.ignoreInvalidSslCertificates = true;
        return this;
    }

    /**
     * You can supply a path to a JKS trust store to be used to validate SSL certificates with.
     *
     * Alternatively you can can explicitly add your certificate to the JVM's truststore using a command like:
     * keytool -importcert -keystore truststore.jks -file servercert.pem
     *
     * @param trustStorePath file path to truststore.
     * @param password (optional) Password for truststore.
     * @return Configuration instance.
     */
    public Configuration useTrustStore(final File trustStorePath, final String password) {
        this.trustStoreFile = Objects.requireNonNull(trustStorePath);
        this.trustStorePassword = password;
        return this;
    }

    /**
     * Set the request timeout value, in seconds.
     * @param requestTimeoutInSeconds How long before a request times out, in seconds.
     * @return Configuration instance.
     */
    public Configuration useRequestTimeoutInSeconds(final int requestTimeoutInSeconds) {
        this.requestTimeoutInSeconds = requestTimeoutInSeconds;
        return this;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getProxyScheme() {
        return proxyScheme;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public String getApiHost() {
        return apiHost;
    }

    public boolean getIgnoreInvalidSslCertificates() {
        return ignoreInvalidSslCertificates;
    }

    public File getTrustStoreFile() {
        return trustStoreFile;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public int getRequestTimeoutInSeconds() {
        return requestTimeoutInSeconds;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Configuration{")
            .append("apiHost='").append(apiHost).append('\'')
            .append(", requestTimeout='").append(requestTimeoutInSeconds).append('\'');
        if (proxyHost != null) {
            stringBuilder
                .append(", proxy='").append(proxyScheme).append("://");

            // Append configured proxy auth details
            if (proxyUsername != null) {
                stringBuilder.append(proxyUsername).append(':').append("XXXXXXX@");
            }

            stringBuilder.append(proxyHost).append(":").append(proxyPort).append('\'');
        }
        stringBuilder.append(", ignoreInvalidSslCertificates='").append(ignoreInvalidSslCertificates).append('\'');
        if (trustStoreFile != null) {
            stringBuilder.append(", sslTrustStoreFile='").append(trustStoreFile).append('\'');
            if (trustStorePassword != null) {
                stringBuilder.append(", sslTrustStorePassword='******'");
            }
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}
