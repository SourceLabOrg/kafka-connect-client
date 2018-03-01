package org.sourcelab.kafka.connect.apiclient;

/**
 * Configure your Kafka Connect API client.
 *
 * Also allows for configuring an optional proxy with or without authentication.
 */
public class Configuration {
    // Defines the URL/Hostname of Kafka-Connect
    private final String apiHost;

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
        if (!kafkaConnectHost.startsWith("http://")) {
            this.apiHost = "http://" + kafkaConnectHost;
        } else {
            this.apiHost = kafkaConnectHost;
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

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Configuration{")
            .append("apiHost='").append(apiHost).append('\'');
        if (proxyHost != null) {
            stringBuilder
                .append(", proxy='").append(proxyScheme).append("://");

            // Append configured proxy auth details
            if (proxyUsername != null) {
                stringBuilder.append(proxyUsername).append(':').append("XXXXXXX@");
            }

            stringBuilder.append(proxyHost).append(":").append(proxyPort).append('\'');
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}
