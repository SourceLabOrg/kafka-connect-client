package org.sourcelab.kafka.connect.apiclient.request.get;

import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;

/**
 * Defines interface for GET requests.
 * @param <T> Defines the return type of the request.
 */
public interface GetRequest<T> extends Request<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
