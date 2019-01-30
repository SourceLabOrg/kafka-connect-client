package org.sourcelab.kafka.connect.apiclient.rest.exceptions;

/**
 * Thrown if the server required Authentication, but the client was either not configured to provide credentials,
 * or those credentials were rejected/invalid.
 */
public class UnauthorizedRequestException extends InvalidRequestException {

    /**
     * Constructor.
     * @param message Error message.
     * @param errorCode Error code.
     */
    public UnauthorizedRequestException(final String message, final int errorCode) {
        super(message, errorCode);
    }
}
