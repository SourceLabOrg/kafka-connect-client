package org.sourcelab.kafka.connect.apiclient.rest.handlers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.rest.RestResponse;

import java.io.IOException;

/**
 * Handles parsing a response to RestResponse object.
 */
public class RestResponseHandler implements ResponseHandler<RestResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseHandler.class);

    @Override
    public RestResponse handleResponse(final HttpResponse response) {
        final int statusCode = response.getStatusLine().getStatusCode();

        try {
            final HttpEntity entity = response.getEntity();
            final String responseStr = entity != null ? EntityUtils.toString(entity) : null;

            // Fully consume entity.
            EntityUtils.consume(entity);

            // Construct return object
            return new RestResponse(responseStr, statusCode);
        } catch (IOException exception) {
            logger.error("Failed to read entity: {}", exception.getMessage(), exception);
            // TODO throw exception
            return null;
        }
    }
}
