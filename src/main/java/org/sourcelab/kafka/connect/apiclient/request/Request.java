package org.sourcelab.kafka.connect.apiclient.request;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for all Requests to implement.
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

    T parseResponse(final String responseStr) throws IOException;
}
