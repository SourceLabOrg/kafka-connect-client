package org.sourcelab.kafka.connect.apiclient.rest;

import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.request.Request;

/**
 * A Mock Rest Client for testing.
 */
public class MockRestClient implements RestClient {

    public void init(final Configuration configuration) {
        // Noop.
    }

    public RestResponse submitRequest(final Request request) throws RestException {
        // Not implemented yet.
        return null;
    }

    public void close() {
        // Noop.
    }
}
