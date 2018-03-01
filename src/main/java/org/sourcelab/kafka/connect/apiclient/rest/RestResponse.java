package org.sourcelab.kafka.connect.apiclient.rest;

/**
 * Represents the response from the API.
 */
public class RestResponse {
    private final String responseStr;
    private final int httpCode;

    /**
     * Constructor.
     * @param responseStr The http response body, in string format.
     * @param httpCode The http status code from the response.
     */
    public RestResponse(final String responseStr, final int httpCode) {
        this.responseStr = responseStr;
        this.httpCode = httpCode;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public String toString() {
        return "RestResponse{"
            + "responseStr='" + responseStr + '\''
            + ", httpCode=" + httpCode
            + '}';
    }
}
