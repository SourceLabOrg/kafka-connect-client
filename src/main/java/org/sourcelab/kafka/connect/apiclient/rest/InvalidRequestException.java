package org.sourcelab.kafka.connect.apiclient.rest;

/**
 * Represents when a request is invalid.
 */
public class InvalidRequestException extends RuntimeException {
    private final int errorCode;

    public InvalidRequestException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InvalidRequestException(final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
