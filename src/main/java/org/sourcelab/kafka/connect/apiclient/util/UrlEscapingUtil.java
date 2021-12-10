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
