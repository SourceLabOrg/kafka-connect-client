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

package org.sourcelab.kafka.connect.apiclient.rest.exceptions;

import org.sourcelab.kafka.connect.apiclient.request.RequestErrorResponse;

import java.util.Objects;

/**
 * Represents when a request is invalid.
 */
public class InvalidRequestException extends RuntimeException {
    private final int errorCode;

    /**
     * Constructor.
     * @param message Error message.
     * @param errorCode Http Error Code.
     */
    public InvalidRequestException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor.
     * @param message Error message.
     * @param cause Originating exception.
     */
    public InvalidRequestException(final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
    }

    /**
     * Resulting HTTP Status code.
     * @return Http Status Code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Factory method to create proper exception class based on the error.
     * @param errorResponse Parsed error response from server.
     * @return Appropriate Exception class.
     */
    public static InvalidRequestException factory(final RequestErrorResponse errorResponse) {
        Objects.requireNonNull(errorResponse, "Invalid RequestErrorResponse parameter, must not be null");

        switch (errorResponse.getErrorCode()) {
            case 401:
                return new UnauthorizedRequestException(errorResponse.getMessage(), errorResponse.getErrorCode());
            case 404:
                return new ResourceNotFoundException(errorResponse.getMessage());
            case 409:
                return new ConcurrentConfigModificationException(errorResponse.getMessage());
            default:
                return new InvalidRequestException(errorResponse.getMessage(), errorResponse.getErrorCode());

        }
    }
}
