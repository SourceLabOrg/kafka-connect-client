package org.sourcelab.kafka.connect.apiclient.rest;


import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.request.Request;

/**
 * Interface for making HTTP calls.
 */
public interface RestClient {
    /**
     * Initializes the RestClient implementation.
     * Any setup or resource allocation should happen here.
     * @param configuration Pardot Api Configuration.
     */
    void init(final Configuration configuration);

    /**
     * Make a request against the Pardot API.
     * @param request The request to submit.
     * @return The response, in UTF-8 String format.
     * @throws RestException When something goes wrong in an underlying implementation.
     */
    RestResponse submitRequest(final Request request) throws RestException;

    /**
     * Called to release any internally held resources.
     */
    void close();

}
