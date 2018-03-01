/**
 * Copyright 2017 Stephen Powis https://github.com/Crim/pardot-java-client
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

import com.darksci.pardot.api.Configuration;
import com.darksci.pardot.api.request.Request;
import com.darksci.pardot.api.rest.handlers.RestResponseHandler;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RestClient implementation using HTTPClient.
 */
public class HttpClientRestClient implements RestClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientRestClient.class);

    /**
     * Save a copy of the configuration.
     */
    private Configuration configuration;

    /**
     * Our underlying Http Client.
     */
    private CloseableHttpClient httpClient;


    /**
     * Constructor.
     */
    public HttpClientRestClient() {
    }

    /**
     * Initialization method.  This takes in the configuration and sets up the underlying
     * http client appropriately.
     * @param configuration The user defined configuration.
     */
    @Override
    public void init(final Configuration configuration) {
        // Save reference to configuration
        this.configuration = configuration;

        // Create default SSLContext
        final SSLContext sslcontext = SSLContexts.createDefault();

        // Allow TLSv1 protocol only
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
            sslcontext,
            new String[] { "TLSv1" },
            null,
            SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        );

        // Setup client builder
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder
            // Pardot disconnects requests after 120 seconds.
            .setConnectionTimeToLive(130, TimeUnit.SECONDS)
            .setSSLSocketFactory(sslsf);

        // Define our RequestConfigBuilder
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        // If we have a configured proxy host
        if (configuration.getProxyHost() != null) {
            // Define proxy host
            final HttpHost proxyHost = new HttpHost(
                configuration.getProxyHost(),
                configuration.getProxyPort(),
                configuration.getProxyScheme()
            );

            // If we have proxy auth enabled
            if (configuration.getProxyUsername() != null) {
                // Create credential provider
                final CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(
                    new AuthScope(configuration.getProxyHost(), configuration.getProxyPort()),
                    new UsernamePasswordCredentials(configuration.getProxyUsername(), configuration.getProxyPassword())
                );

                // Attach Credentials provider to client builder.
                clientBuilder.setDefaultCredentialsProvider(credsProvider);
            }

            // Attach Proxy to request config builder
            requestConfigBuilder.setProxy(proxyHost);
        }

        // Attach default request config
        clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

        // build http client
        httpClient = clientBuilder.build();
    }

    @Override
    public void close() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("Error closing: {}", e.getMessage(), e);
            }
        }
        httpClient = null;
    }

    /**
     * Make a request against the Pardot API.
     * @param request The request to submit.
     * @return The response, in UTF-8 String format.
     * @throws RestException if something goes wrong.
     */
    @Override
    public RestResponse submitRequest(final Request request) throws RestException {
        try {
            return submitRequest(request, new RestResponseHandler());
        } catch (IOException exception) {
            throw new RestException(exception.getMessage(), exception);
        }
    }

    /**
     * For issuing an API Request.
     * @param request The Request to perform.
     * @param responseHandler How to parse the response.
     * @param <T> The return type.
     * @return The parsed API response.
     */
    private <T> T submitRequest(final Request request, final ResponseHandler<T> responseHandler) throws IOException {
        final String url = constructApiUrl(request.getApiEndpoint());
        return submitRequest(url, request.getRequestParameters(), responseHandler);
    }

    /**
     * Internal POST method.
     * @param url Url to POST to.
     * @param postParams POST parameters to include in the request
     * @param responseHandler The response Handler to use to parse the response
     * @param <T> The type that ResponseHandler returns.
     * @return Parsed response.
     */
    private <T> T submitRequest(final String url, Map<String, String> postParams, final ResponseHandler<T> responseHandler) throws IOException {
        try {
            final HttpPost post = new HttpPost(url);

            // Define required auth params
            final List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("user_key", configuration.getUserKey()));
            if (configuration.getApiKey() != null) {
                params.add(new BasicNameValuePair("api_key", configuration.getApiKey()));
            }

            // Attach submitRequest params
            for (Map.Entry<String, String> entry : postParams.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(params));

            logger.info("Executing request {} with {}", post.getRequestLine(), filterSensitiveParams(params));

            // Execute and return
            return httpClient.execute(post, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // Typically this is a parse error.
            e.printStackTrace();
        } finally {
            // Only close at end
            //httpClient.close();
        }
        return null;
    }

    /**
     * Internal helper method for generating URLs w/ the appropriate API host and API version.
     * @param endPoint The end point you want to hit.
     * @return Constructed URL for the end point.
     */
    private String constructApiUrl(final String endPoint) {
        return configuration.getPardotApiHost()
            + "/" + endPoint
            + "/version/"
            + configuration.getPardotApiVersion();
    }

    /**
     * Internal helper utility to prevent sensitive field values from being logged.
     * @param inputParams The input request parameters.
     * @return A filtered list of params suitable to be logged.
     */
    private List<NameValuePair> filterSensitiveParams(final List<NameValuePair> inputParams) {
        // Define sensitive fields
        final String[] sensitiveFields = new String[] { "user_key", "password", "api_key" };

        // Create a copy of the list
        final List<NameValuePair> copiedList = new ArrayList<>();
        copiedList.addAll(inputParams);

        // Loop over each sensitive field name.
        for (final String sensitiveField : sensitiveFields) {
            // Remove sensitive param from copied list
            if (copiedList.removeIf((nameValuePair -> nameValuePair.getName().equals(sensitiveField)))) {
                // Add dummy value
                copiedList.add(new BasicNameValuePair(sensitiveField, "XXXXXXX"));
            }
        }

        // Return the filtered list
        return copiedList;
    }
}
