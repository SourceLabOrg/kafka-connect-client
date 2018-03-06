package org.sourcelab.kafka.connect.apiclient.request.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ConnectorPlugin {
    @JsonAlias("class")
    private String className;

    private String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "ConnectorPlugin{"
            + "className='" + className + '\''
            + '}';
    }
}
