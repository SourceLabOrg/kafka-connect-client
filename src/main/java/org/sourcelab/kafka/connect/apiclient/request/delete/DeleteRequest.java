package org.sourcelab.kafka.connect.apiclient.request.delete;

import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;

/**
 * Defines interface for DELETE requests.
 * @param <T> Defines the return type of the request.
 */
public interface DeleteRequest<T> extends Request<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.DELETE;
    }
}
