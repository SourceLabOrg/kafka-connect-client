package org.sourcelab.kafka.connect.apiclient.request.put;

import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;

/**
 * Defines interface for PUT requests.
 * @param <T> Defines the return type of the request.
 */
public interface PutRequest<T> extends Request<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.PUT;
    }
}
