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

import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;

/**
 * Utility for properly configuring the SSL Context based on client configuration settings.
 */
class HttpsContextBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HttpsContextBuilder.class);

    /**
     * Accept TLS1.2, 1.1, and 1.0 protocols.
     */
    private static final String[] sslProtocols = new String[] { "TLSv1.2", "TLSv1.1", "TLSv1" };

    /**
     * Client configuration.
     */
    private final Configuration configuration;

    /**
     * Constructor.
     * @param configuration client configuration instance.
     */
    public HttpsContextBuilder(final Configuration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    /**
     * Properly configured SslSocketFactory based on client configuration.
     * @return SslSocketFactory instance.
     */
    public LayeredConnectionSocketFactory createSslSocketFactory() {
        // Emit an warning letting everyone know we're using an insecure configuration.
        if (configuration.getIgnoreInvalidSslCertificates()) {
            logger.warn("Using insecure configuration, skipping server-side certificate validation checks.");
        }

        return new SSLConnectionSocketFactory(
            getSslContext(),
            getSslProtocols(),
            null,
            getHostnameVerifier()
        );
    }

    /**
     * Get HostnameVerifier instance based on client configuration.
     * @return HostnameVerifier instance.
     */
    HostnameVerifier getHostnameVerifier() {
        // If we're configured to ignore invalid certificates
        if (configuration.getIgnoreInvalidSslCertificates()) {
            // Use the Noop verifier.
            return NoopHostnameVerifier.INSTANCE;
        }
        // Otherwise use the default verifier.
        return SSLConnectionSocketFactory.getDefaultHostnameVerifier();
    }

    /**
     * Get properly configured SSLContext instance based on client configuration.
     * @return SSLContext instance.
     */
    SSLContext getSslContext() {
        try {
            // Create default SSLContext
            final SSLContext sslcontext = SSLContexts.createDefault();

            // Initialize ssl context with configured key and trust managers.
            sslcontext.init(getKeyManagers(), getTrustManagers(), new SecureRandom());

            return sslcontext;
        } catch (final KeyManagementException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Based on client configuration, construct KeyManager instances to use.
     * @return Array of 0 or more KeyManagers.
     */
    KeyManager[] getKeyManagers() {
        // If not configured to use a KeyStore
        if (configuration.getKeyStoreFile() == null) {
            // Return null array.
            return new KeyManager[0];
        }

        // If configured to use a key store
        try {
            final KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            // New JKS Keystore.
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            final char[] password;
            if (configuration.getKeyStorePassword() == null) {
                password = new char[0];
            } else {
                password = configuration.getKeyStorePassword().toCharArray();
            }

            try (final FileInputStream keyStoreFileInput = new FileInputStream(configuration.getKeyStoreFile())) {
                keyStore.load(keyStoreFileInput, password);
            }
            keyFactory.init(keyStore, password);
            return keyFactory.getKeyManagers();
        } catch (final FileNotFoundException exception) {
            throw new RuntimeException(
                "Unable to find configured KeyStore file \"" + configuration.getKeyStoreFile() + "\"",
                exception
            );
        } catch (final KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Based on Client Configuration, construct TrustManager instances to use.
     * @return Array of 0 or more TrustManager instances.
     */
    TrustManager[] getTrustManagers() {
        try {
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // If client configuration is set to ignore invalid certificates
            if (configuration.getIgnoreInvalidSslCertificates()) {
                // Initialize ssl context with a TrustManager instance that just accepts everything blindly.
                // HIGHLY INSECURE / NOT RECOMMENDED!
                return new TrustManager[]{ new NoopTrustManager() };

            // If client configuration has a trust store defined.
            } else if (configuration.getTrustStoreFile() != null) {
                // Attempt to read the trust store from disk.
                try (final FileInputStream trustStoreFileInput = new FileInputStream(configuration.getTrustStoreFile())) {
                    // New JKS Keystore.
                    final KeyStore keyStore = KeyStore.getInstance("JKS");

                    // If no trust store password is set.
                    if (configuration.getTrustStorePassword() == null) {
                        keyStore.load(trustStoreFileInput, null);
                    } else {
                        keyStore.load(trustStoreFileInput, configuration.getTrustStorePassword().toCharArray());
                    }
                    trustManagerFactory.init(keyStore);
                }
                return trustManagerFactory.getTrustManagers();
            } else {
                // use default TrustManager instances
                trustManagerFactory.init((KeyStore) null);
                return trustManagerFactory.getTrustManagers();
            }
        } catch (final FileNotFoundException exception) {
            throw new RuntimeException(
                "Unable to find configured TrustStore file \"" + configuration.getTrustStoreFile() + "\"",
                exception
            );
        } catch (final KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    /**
     * Get allowed SSL Protocols.
     * @return allowed SslProtocols.
     */
    private String[] getSslProtocols() {
        return sslProtocols;
    }
}
