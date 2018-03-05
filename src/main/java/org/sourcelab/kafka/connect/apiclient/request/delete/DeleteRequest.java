package org.sourcelab.kafka.connect.apiclient.request.delete;

import org.sourcelab.kafka.connect.apiclient.request.Request;
import org.sourcelab.kafka.connect.apiclient.request.RequestMethod;

public interface DeleteRequest<T> extends Request<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.DELETE;
    }
}
