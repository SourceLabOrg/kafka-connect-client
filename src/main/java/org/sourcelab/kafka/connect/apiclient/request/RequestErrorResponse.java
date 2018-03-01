package org.sourcelab.kafka.connect.apiclient.request;

public class RequestErrorResponse {
    private int errorCode;
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
