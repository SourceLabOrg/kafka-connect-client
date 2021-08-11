/**
 * Copyright 2018, 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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

package org.sourcelab.kafka.connect.apiclient;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.sourcelab.kafka.connect.apiclient.rest.RestClient;
import org.sourcelab.kafka.connect.apiclient.rest.RestResponse;
import org.sourcelab.kafka.connect.apiclient.rest.exceptions.ConcurrentConfigModificationException;
import org.sourcelab.kafka.connect.apiclient.rest.exceptions.ResourceNotFoundException;
import org.sourcelab.kafka.connect.apiclient.rest.exceptions.UnauthorizedRequestException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests over KafkaConnectClient.
 */
public class KafkaConnectClientUnitTest {

    private final Configuration configuration = new Configuration("http://localhost:9092");

    /**
     * This test verifies that if the underlying RestClient returns a response with Http Status Code 401,
     * then KafkaConnectClient will throw an UnauthorizedRequestException.
     */
    @Test(expected = UnauthorizedRequestException.class)
    public void unAuthorizedException() {
        // Create mock RestResponse
        final RestResponse restResponse = new RestResponse("Invalid credentials.", HttpStatus.SC_UNAUTHORIZED);

        // Create mock RestClient
        final RestClient mockRestClient = mock(RestClient.class);
        when(mockRestClient.submitRequest(any()))
            .thenReturn(restResponse);

        // Create client
        final KafkaConnectClient client = new KafkaConnectClient(configuration, mockRestClient);

        // Call any method that makes a request via RestClient.
        client.getConnectors();
    }

    /**
     * This test verifies that if the underlying RestClient returns a response with Http Status Code 404,
     * then KafkaConnectClient will throw a ResourceNotFoundException.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void on404_resourceNotFoundException() {
        // Create mock RestResponse
        final String connectorName = "DoesNotExist";
        final String result = "{\"error_code\":404,\"message\":\"Connector " + connectorName + " not found\"}";

        final RestResponse restResponse = new RestResponse(result, HttpStatus.SC_NOT_FOUND);

        // Create mock RestClient
        final RestClient mockRestClient = mock(RestClient.class);
        when(mockRestClient.submitRequest(any()))
            .thenReturn(restResponse);

        // Create client
        final KafkaConnectClient client = new KafkaConnectClient(configuration, mockRestClient);

        // Call any method that makes a request via RestClient.
        client.getConnector(connectorName);
    }

    /**
     * This test verifies that if the underlying RestClient returns a response with Http Status Code 409,
     * then KafkaConnectClient will throw a ConcurrentConfigModificationException.
     */
    @Test(expected = ConcurrentConfigModificationException.class)
    public void on409_concurrentConfigModificationException() {
        // Create mock RestResponse
        final String connectorName = "DoesNotExist";
        final String result = "{\"error_code\":409,\"message\":\"Rebalance in progress.\"}";

        final RestResponse restResponse = new RestResponse(result, HttpStatus.SC_CONFLICT);

        // Create mock RestClient
        final RestClient mockRestClient = mock(RestClient.class);
        when(mockRestClient.submitRequest(any()))
            .thenReturn(restResponse);

        // Create client
        final KafkaConnectClient client = new KafkaConnectClient(configuration, mockRestClient);

        // Call any method that makes a request via RestClient.
        client.getConnector(connectorName);
    }
}