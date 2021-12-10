package org.sourcelab.kafka.connect.apiclient.util;

import org.apache.http.client.utils.URLEncodedUtils;

import java.util.Objects;

/**
 * Intended to provide a shim between Guava so that dependency can be removed in a future release.
 */
public class UrlEscapingUtil {
    /**
     * Returns the escaped form of a given literal string.
     *
     * @param input the literal string to be escaped.
     * @return the escaped form of string.
     * @throws NullPointerException if input string is null.
     * @throws IllegalArgumentException if string contains badly formed UTF-16 or cannot be escaped for any other reason
     */
    public static String escapePath(final String input) {
        Objects.requireNonNull(input);
        final String result = URLEncodedUtils.formatSegments(input);
        // Strip prepended slash
        if (result.length() > 0) {
            return result.substring(1);
        }
        return "";
    }
}
