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

import org.sourcelab.http.rest.configuration.BasicConfiguration;
import org.sourcelab.http.rest.configuration.ProxyConfiguration;

/**
 * Configure your Kafka Connect API client.
 *
 * Also allows for configuring an optional proxy with or without authentication.
 */
public final class Configuration extends BasicConfiguration<Configuration> {
    // Used to provide non-breaking compatibility.
    private final ProxyConfiguration.Builder proxyConfigBuilder = ProxyConfiguration.newBuilder();

    /**
     * Default Constructor.
     * @param kafkaConnectHost Hostname of Kafka-Connect
     */
    public Configuration(final String kafkaConnectHost) {
        super(kafkaConnectHost);
    }

    /**
     * Allow setting optional proxy configuration.
     *
     * @param proxyHost Host for the proxy to use.
     * @param proxyPort Post for the proxy to use.
     * @param proxyScheme Scheme to use, HTTP/HTTPS
     * @return Configuration instance.
     * @deprecated Replaced with useProxy(ProxyConfiguration) method.
     */
    public Configuration useProxy(final String proxyHost, final int proxyPort, final String proxyScheme) {
        proxyConfigBuilder.useProxy(proxyHost, proxyPort, proxyScheme);
        return useProxy(proxyConfigBuilder.build());
    }

    /**
     * Allow setting credentials for a proxy that requires authentication.
     *
     * @param proxyUsername Username for proxy.
     * @param proxyPassword Password for proxy.
     * @return Configuration instance.
     * @deprecated Replaced with useProxy(ProxyConfiguration) method.
     */
    public Configuration useProxyAuthentication(final String proxyUsername, final String proxyPassword) {
        proxyConfigBuilder.useProxyAuthentication(proxyUsername, proxyPassword);
        return useProxy(proxyConfigBuilder.build());
    }

    /**
     * Return the configured proxy hostname.
     * @return Configured proxy hostname, or null if not configured.
     * @deprecated Replaced by getProxyConfiguration()
     */
    public String getProxyHost() {
        if (super.getProxyConfiguration() != null) {
            return super.getProxyConfiguration().getProxyHost();
        }
        return null;
    }

    /**
     * Return the configured proxy port.
     * @return Configured proxy port, or 0 if not configured.
     * @deprecated Replaced by getProxyConfiguration()
     */
    public int getProxyPort() {
        if (super.getProxyConfiguration() != null) {
            return super.getProxyConfiguration().getProxyPort();
        }
        return 0;
    }

    /**
     * Return the configured proxy scheme.
     * @return Configured proxy scheme, or null if not configured.
     * @deprecated Replaced by getProxyConfiguration()
     */
    public String getProxyScheme() {
        if (super.getProxyConfiguration() != null) {
            return super.getProxyConfiguration().getProxyScheme();
        }
        return null;
    }

    /**
     * Return the configured proxy username for proxy authentication.
     * @return Configured proxy username, or null if not configured.
     * @deprecated Replaced by getProxyConfiguration()
     */
    public String getProxyUsername() {
        if (super.getProxyConfiguration() != null) {
            return super.getProxyConfiguration().getProxyUsername();
        }
        return null;
    }

    /**
     * Return the configured proxy password for proxy authentication.
     * @return Configured proxy username, or null if not configured.
     * @deprecated Replaced by getProxyConfiguration()
     */
    public String getProxyPassword() {
        if (super.getProxyConfiguration() != null) {
            return super.getProxyConfiguration().getProxyPassword();
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Configuration{")
            .append("apiHost='").append(getApiHost()).append('\'')
            .append(", requestTimeout='").append(getRequestTimeoutInSeconds()).append('\'');
        if (getProxyConfiguration() != null) {
            stringBuilder
                .append(", proxy='").append(getProxyConfiguration().getProxyScheme()).append("://");

            // Append configured proxy auth details
            if (getProxyConfiguration().getProxyUsername() != null) {
                stringBuilder.append(getProxyConfiguration().getProxyUsername()).append(':').append("XXXXXXX@");
            }

            stringBuilder.append(getProxyConfiguration().getProxyHost()).append(":").append(getProxyConfiguration().getProxyPort()).append('\'');
        }
        stringBuilder.append(", ignoreInvalidSslCertificates='").append(getIgnoreInvalidSslCertificates()).append('\'');
        if (getTrustStoreFile() != null) {
            stringBuilder.append(", sslTrustStoreFile='").append(getTrustStoreFile()).append('\'');
            if (getTrustStorePassword() != null) {
                stringBuilder.append(", sslTrustStorePassword='******'");
            }
        }
        if (getBasicAuthUsername() != null) {
            stringBuilder
                .append(", basicAuthUsername='").append(getBasicAuthUsername()).append('\'')
                .append(", basicAuthPassword='******'");
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}