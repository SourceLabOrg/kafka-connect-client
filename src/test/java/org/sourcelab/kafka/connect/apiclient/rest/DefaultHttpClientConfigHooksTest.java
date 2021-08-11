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
import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.Configuration;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Verifying behavior of DefaultHttpClientConfigHooks.
 */
public class DefaultHttpClientConfigHooksTest {

    private final Configuration configuration = new Configuration("https://dummy.host");
    private final DefaultHttpClientConfigHooks configHooks = new DefaultHttpClientConfigHooks();

    /**
     * Test that the default behavior to always create a new instance.
     */
    @Test
    public void createHttpClientBuilder_alwaysCreatesNewInstance() {
        final HttpClientContext instance1 = configHooks.createHttpClientContext(configuration);
        final HttpClientContext instance2 = configHooks.createHttpClientContext(configuration);
        final HttpClientContext instance3 = configHooks.createHttpClientContext(configuration);

        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);
    }

    /**
     * Test that the default behavior to always create a new instance.
     */
    @Test
    public void createHttpsContextBuilder_alwaysCreatesNewInstance() {
        final HttpsContextBuilder instance1 = configHooks.createHttpsContextBuilder(configuration);
        final HttpsContextBuilder instance2 = configHooks.createHttpsContextBuilder(configuration);
        final HttpsContextBuilder instance3 = configHooks.createHttpsContextBuilder(configuration);

        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);
    }

    /**
     * Test that the default behavior to always create a new instance.
     */
    @Test
    public void createRequestConfigBuilder_alwaysCreatesNewInstance() {
        final RequestConfig.Builder instance1 = configHooks.createRequestConfigBuilder(configuration);
        final RequestConfig.Builder instance2 = configHooks.createRequestConfigBuilder(configuration);
        final RequestConfig.Builder instance3 = configHooks.createRequestConfigBuilder(configuration);

        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);
    }

    /**
     * Test that the default behavior to always create a new instance.
     */
    @Test
    public void createAuthCache_alwaysCreatesNewInstance() {
        final AuthCache instance1 = configHooks.createAuthCache(configuration);
        final AuthCache instance2 = configHooks.createAuthCache(configuration);
        final AuthCache instance3 = configHooks.createAuthCache(configuration);

        // Instances should differ
        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);

        // Should be expected type
        assertTrue("Unexpected Type", instance1 instanceof BasicAuthCache);
    }

    /**
     * Test that the default behavior to always create a new instance.
     */
    @Test
    public void createCredentialsProvider_alwaysCreatesNewInstance() {
        final CredentialsProvider instance1 = configHooks.createCredentialsProvider(configuration);
        final CredentialsProvider instance2 = configHooks.createCredentialsProvider(configuration);
        final CredentialsProvider instance3 = configHooks.createCredentialsProvider(configuration);

        // Instances should differ
        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);

        // Should be expected type
        assertTrue("Unexpected Type", instance1 instanceof BasicCredentialsProvider);
    }

    /**
     * Test that the default behavior is to always create a new HttpClientContext.
     */
    @Test
    public void createHttpClientContext_alwaysCreatesNewInstance() {
        final HttpClientContext instance1 = configHooks.createHttpClientContext(configuration);
        final HttpClientContext instance2 = configHooks.createHttpClientContext(configuration);
        final HttpClientContext instance3 = configHooks.createHttpClientContext(configuration);

        assertNotSame("Instances should be different", instance1, instance2);
        assertNotSame("Instances should be different", instance1, instance3);
        assertNotSame("Instances should be different", instance2, instance3);
    }

    /**
     * Test that the default behavior is to always return the same instance unmodified.
     */
    @Test
    public void modifyAuthCache_doesntModifyInstance() {
        final AuthCache mockInstance = spy(AuthCache.class);

        // Call method under test
        final AuthCache result = configHooks.modifyAuthCache(configuration, mockInstance);

        // Verify returned the same instance
        assertSame("Should be same instance", mockInstance, result);
        verifyZeroInteractions(mockInstance);
    }

    /**
     * Test that the default behavior is to always return the same instance unmodified.
     */
    @Test
    public void modifyCredentialsProvider_doesntModifyInstance() {
        final CredentialsProvider mockInstance = spy(CredentialsProvider.class);

        // Call method under test
        final CredentialsProvider result = configHooks.modifyCredentialsProvider(configuration, mockInstance);

        // Verify returned the same instance
        assertSame("Should be same instance", mockInstance, result);
        verifyZeroInteractions(mockInstance);
    }

    /**
     * Test that the default behavior is to always return the same instance unmodified.
     */
    @Test
    public void modifyRequestConfig_doesntModifyInstance() {
        final RequestConfig.Builder mockInstance = spy(RequestConfig.Builder.class);

        // Call method under test
        final RequestConfig.Builder result = configHooks.modifyRequestConfig(configuration, mockInstance);

        // Verify returned the same instance
        assertSame("Should be same instance", mockInstance, result);
        verifyZeroInteractions(mockInstance);
    }

    /**
     * Test that the default behavior is to always return the same instance unmodified.
     */
    @Test
    public void modifyHttpClientBuilder_doesntModifyInstance() {
        final HttpClientBuilder mockInstance = spy(HttpClientBuilder.class);

        // Call method under test
        final HttpClientBuilder result = configHooks.modifyHttpClientBuilder(configuration, mockInstance);

        // Verify returned the same instance
        assertSame("Should be same instance", mockInstance, result);
        verifyZeroInteractions(mockInstance);
    }

    /**
     * Test that the default behavior is to always return the same instance unmodified.
     */
    @Test
    public void modifyHttpClientContext_doesntModifyInstance() {
        final HttpClientContext mockInstance = spy(HttpClientContext.class);

        // Call method under test
        final HttpClientContext result = configHooks.modifyHttpClientContext(configuration, mockInstance);

        // Verify returned the same instance
        assertSame("Should be same instance", mockInstance, result);
        verifyZeroInteractions(mockInstance);
    }
}