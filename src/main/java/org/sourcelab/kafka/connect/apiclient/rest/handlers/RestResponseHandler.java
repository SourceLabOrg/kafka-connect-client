/**
 * Copyright 2018, 2019 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
public final class RestResponseHandler implements ResponseHandler<RestResponse> {
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
        } catch (final IOException exception) {
            logger.error("Failed to read entity: {}", exception.getMessage(), exception);
            // TODO throw exceptions
            return null;
        }
    }
}
