package org.sourcelab.kafka.connect.apiclient.request;

/**
 * Represents an error response from the rest service.
 */
public final class RequestErrorResponse {
    private int errorCode = 0;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RequestErrorResponse{"
            + "errorCode=" + errorCode
            + ", message='" + message + '\''
            + '}';
    }
}
