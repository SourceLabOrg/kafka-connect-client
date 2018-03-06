package org.sourcelab.kafka.connect.apiclient.request.post;

import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;

/**
 * Defines interface for POST requests.
 * @param <T> Defines the return type of the request.
 */
public interface PostRequest<T> extends Request<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
