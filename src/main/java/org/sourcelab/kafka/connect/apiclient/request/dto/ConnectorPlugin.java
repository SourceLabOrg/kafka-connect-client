package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Represents details about a Connector Plugin.
 */
public class ConnectorPlugin {
    @JsonAlias("class")
    private String className;
    private String type;
    private String version;

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
            + '}';
    }
}
