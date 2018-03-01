package org.sourcelab.kafka.connect.apiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.rest.HttpClientRestClient;
import org.sourcelab.kafka.connect.apiclient.rest.RestClient;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    /**
     * Our API Configuration.
     */
    private final Configuration configuration;

    /**
     * Underlying RestClient to use.
     */
    private final RestClient restClient;

    /**
     * Default Constructor.
     * @param configuration Api Client Configuration.
     */
    public ApiClient(final Configuration configuration) {
        this.configuration = configuration;
        this.restClient = new HttpClientRestClient();
    }

    /**
     * Constructor for injecting a RestClient implementation.
     * Typically only used in testing.
     * @param configuration Pardot Api Configuration.
     * @param restClient RestClient implementation to use.
     */
    public ApiClient(final Configuration configuration, final RestClient restClient) {
        this.configuration = configuration;
        this.restClient = restClient;
    }
}
