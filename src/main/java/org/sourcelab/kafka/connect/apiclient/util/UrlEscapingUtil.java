package org.sourcelab.kafka.connect.apiclient.util;

import java.util.Objects;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

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
     */
    public static String escapePath(final String input) {
        Objects.requireNonNull(input);
        return urlPathSegmentEscaper().escape(input);
    }
}
