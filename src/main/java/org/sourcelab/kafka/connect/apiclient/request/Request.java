package org.sourcelab.kafka.connect.apiclient.request;

import java.io.IOException;

/**
 * Interface for all Requests to implement.
 * @param <T> return type of request.
 */
public interface Request<T> {

    /**
     * @return The name of the end point this request uses. Example: "campaign", "user", etc..
     */
    String getApiEndpoint();

    /**
     * @return The type of HTTP Request.
     */
    RequestMethod getRequestMethod();

    /**
     * @return correctly formatted request parameters.
     */
    Object getRequestBody();

    /**
     * Parse the rest service's response into a concrete object.
     * @param responseStr The servers response in string format.
     * @return A concrete object representing the result.
     * @throws IOException on parsing errors.
     */
    T parseResponse(final String responseStr) throws IOException;
}
