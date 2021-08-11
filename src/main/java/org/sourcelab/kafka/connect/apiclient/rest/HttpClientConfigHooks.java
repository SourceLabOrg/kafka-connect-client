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

import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.sourcelab.kafka.connect.apiclient.Configuration;

/**
 * HttpClient configuration hooks.
 *
 * Provides an interface for modifying how the underlying HttpClient instance is created.
 *
 * Usage of this would look like:
 *
 * final RestClient restClient = new HttpClientRestClient(new HttpClientConfigHooks {
 *    // Override methods as needed to modify behavior.
 * });
 *
 * // Create client, passing configuration and RestClient implementation
 * final KafkaConnectClient client = new KafkaConnectClient(configuration, restClient);
 *
 * // Use client as normal...
 *
 */
public interface HttpClientConfigHooks {
    /**
     * Create HttpClientBuilder instance.
     * @param configuration KafkaConnectClient configuration.
     * @return HttpClientBuilder instance.
     */
    default HttpClientBuilder createHttpClientBuilder(final Configuration configuration) {
        return HttpClientBuilder.create();
    }

    /**
     * Create HttpsContextBuilder instance.
     * @param configuration KafkaConnectClient configuration.
     * @return HttpsContextBuilder instance.
     */
    default HttpsContextBuilder createHttpsContextBuilder(final Configuration configuration) {
        return new HttpsContextBuilder(configuration);
    }

    /**
     * Create RequestConfig.Builder instance.
     * @param configuration KafkaConnectClient configuration.
     * @return RequestConfig.Builder instance.
     */
    default RequestConfig.Builder createRequestConfigBuilder(final Configuration configuration) {
        return RequestConfig.custom();
    }

    /**
     * Create AuthCache instance.
     * @param configuration KafkaConnectClient configuration.
     * @return AuthCache instance.
     */
    default AuthCache createAuthCache(final Configuration configuration) {
        return new BasicAuthCache();
    }

    /**
     * Create CredentialsProvider instance.
     * @param configuration KafkaConnectClient configuration.
     * @return CredentialsProvider instance.
     */
    default CredentialsProvider createCredentialsProvider(final Configuration configuration) {
        return new BasicCredentialsProvider();
    }

    /**
     * Create HttpClientContext instance.
     * @param configuration KafkaConnectClient configuration.
     * @return HttpClientContext instance.
     */
    default HttpClientContext createHttpClientContext(final Configuration configuration) {
        return HttpClientContext.create();
    }

    /**
     * Ability to modify or replace the AuthCache instance after initial configuration has been performed on it.
     * @param configuration KafkaConnectClient configuration.
     * @return AuthCache instance.
     */
    default AuthCache modifyAuthCache(final Configuration configuration, final AuthCache authCache) {
        return authCache;
    }

    /**
     * Ability to modify or replace the CredentialsProvider instance after initial configuration has been performed on it.
     * @param configuration KafkaConnectClient configuration.
     * @return CredentialsProvider instance.
     */
    default CredentialsProvider modifyCredentialsProvider(final Configuration configuration, final CredentialsProvider credentialsProvider) {
        return credentialsProvider;
    }

    /**
     * Ability to modify or replace the RequestConfig.Builder instance after initial configuration has been performed on it.
     * @param configuration KafkaConnectClient configuration.
     * @return RequestConfig.Builder instance.
     */
    default RequestConfig.Builder modifyRequestConfig(final Configuration configuration, final RequestConfig.Builder builder) {
        return builder;
    }

    /**
     * Ability to modify or replace the HttpClientBuilder instance after initial configuration has been performed on it.
     * @param configuration KafkaConnectClient configuration.
     * @return HttpClientBuilder instance.
     */
    default HttpClientBuilder modifyHttpClientBuilder(final Configuration configuration, final HttpClientBuilder builder) {
        return builder;
    }

    /**
     * Ability to modify or replace the HttpClientContext instance after initial configuration has been performed on it.
     * @param configuration KafkaConnectClient configuration.
     * @return HttpClientContext instance.
     */
    default HttpClientContext modifyHttpClientContext(final Configuration configuration, final HttpClientContext context) {
        return context;
    }
}
