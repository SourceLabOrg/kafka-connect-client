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

package org.sourcelab.kafka.connect.apiclient.request.get;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.sourcelab.http.rest.request.GetRequest;
import org.sourcelab.kafka.connect.apiclient.exception.ResponseParseException;
import org.sourcelab.kafka.connect.apiclient.request.JacksonFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorsWithExpandedMetadata;

import java.io.IOException;

/**
 * Defines a request to retrieve all deployed Connectors extended with all available associated metadata.
 *
 * Currently this includes both 'info' and 'status' metadata.
 *
 * Requires Kafka-Connect server 2.3.0+
 */
public class GetConnectorsExpandAllDetails implements GetRequest<ConnectorsWithExpandedMetadata> {

    @Override
    public String getApiEndpoint() {
        return "/connectors?expand=info&expand=status";
    }

    @Override
    public ConnectorsWithExpandedMetadata parseResponse(final String responseStr) throws IOException {
        try {
            return JacksonFactory.newInstance().readValue(responseStr, ConnectorsWithExpandedMetadata.class);
        } catch (final MismatchedInputException exception) {
            throw new ResponseParseException(
                "Failed to parse response. The end point you requested requires Kafka-Connect 2.3.0+..."
                + "are you sure you're querying against the right version?",
                exception
            );
        }
    }
}

