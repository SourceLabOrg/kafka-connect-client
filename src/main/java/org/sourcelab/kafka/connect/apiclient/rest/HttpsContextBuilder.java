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

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.sourcelab.kafka.connect.apiclient.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Objects;

/**
 * Utility for properly configuring the SSL Context based on client configuration settings.
 */
class HttpsContextBuilder {
    /**
     * Client configuration.
     */
    private final Configuration configuration;

    /**
     * Constructor.
     * @param configuration client configuration instance.
     */
    HttpsContextBuilder(final Configuration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
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
        // Create default SSLContext
        final SSLContext sslcontext = SSLContexts.createDefault();

        try {
            // If client configuration is set to ignore invalid certificates
            if (configuration.getIgnoreInvalidSslCertificates()) {
                // Initialize ssl context with a TrustManager instance that just accepts everything blindly.
                // HIGHLY INSECURE / NOT RECOMMENDED!
                sslcontext.init(new KeyManager[0], new TrustManager[]{new NoopTrustManager()}, new SecureRandom());

            // If client configuration has a trust store defined.
            } else if (configuration.getTrustStoreFile() != null) {

                final TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());


                // New JKS Keystore.
                final KeyStore keyStore = KeyStore.getInstance("JKS");

                // Attempt to read the trust store from disk.
                try (final FileInputStream trustStoreFileInput = new FileInputStream(configuration.getTrustStoreFile())) {

                    // If no trust store password is set.
                    if (configuration.getTrustStorePassword() == null) {
                        keyStore.load(trustStoreFileInput, null);
                    } else {
                        keyStore.load(trustStoreFileInput, configuration.getTrustStorePassword().toCharArray());
                    }
                    trustManagerFactory.init(keyStore);
                }

                // Initialize ssl context with our custom loaded trust store.
                sslcontext.init(new KeyManager[0], trustManagerFactory.getTrustManagers(), new SecureRandom());
            }
        } catch (final KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return sslcontext;
    }
}
