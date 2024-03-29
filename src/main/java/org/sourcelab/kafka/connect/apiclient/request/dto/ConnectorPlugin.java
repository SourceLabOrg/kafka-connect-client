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

package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Represents details about a Connector Plugin.
 */
public final class ConnectorPlugin {
    @JsonAlias("class")
    private String className;
    private String type;
    private String version;

    /**
     * Default constructor.
     */
    public ConnectorPlugin() {
    }

    /**
     * Constructor.
     */
    public ConnectorPlugin(final String className, final String type, final String version) {
        this.className = className;
        this.type = type;
        this.version = version;
    }

    public String getClassName() {
        return className;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ConnectorPlugin{"
            + "className='" + className + '\''
            + ", type='" + type + '\''
            + ", version='" + version + '\''
            + '}';
    }
}
