package org.sourcelab.kafka.connect.apiclient.request.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents results from the Connector Plugin Config Validation API end point.
 */
public final class ConnectorPluginConfigValidationResults {
    private String name;
    private int errorCount;
    private Collection<String> groups = new ArrayList<>();
    private Collection<Config> configs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public Collection<Config> getConfigs() {
        return configs;
    }

    @Override
    public String toString() {
        return "ConnectorPluginConfigValidationResults{"
            + "name='" + name + '\''
            + ", errorCount=" + errorCount
            + ", groups=" + groups
            + ", configs=" + configs
            + '}';
    }

    /**
     * Defines a config item.
     */
    private final static class Config {
        private Definition definition;
        private Value value;

        public Definition getDefinition() {
            return definition;
        }

        public Value getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Config{"
                + "definition=" + definition
                + ", value=" + value
                + '}';
        }

        /**
         * Represents the Definition of a config item.
         */
        private final static class Definition {
            private String name;
            private String type;
            private boolean required = false;
            private String defaultValue;
            private String importance;
            private String documentation;
            private String group;
            private String width;
            private String displayName;
            private Collection<String> dependents = new ArrayList<>();
            private int order;

            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }

            public boolean isRequired() {
                return required;
            }

            public String getDefaultValue() {
                return defaultValue;
            }

            public String getImportance() {
                return importance;
            }

            public String getDocumentation() {
                return documentation;
            }

            public String getGroup() {
                return group;
            }

            public String getWidth() {
                return width;
            }

            public String getDisplayName() {
                return displayName;
            }

            public Collection<String> getDependents() {
                return dependents;
            }

            public int getOrder() {
                return order;
            }

            @Override
            public String toString() {
                return "Definition{"
                    + "name='" + name + '\''
                    + ", type='" + type + '\''
                    + ", required=" + required
                    + ", defaultValue='" + defaultValue + '\''
                    + ", importance='" + importance + '\''
                    + ", documentation='" + documentation + '\''
                    + ", group='" + group + '\''
                    + ", width='" + width + '\''
                    + ", displayName='" + displayName + '\''
                    + ", dependents=" + dependents
                    + ", order=" + order
                    + '}';
            }
        }

        /**
         * Defines a config item value.
         */
        private final static class Value {
            private String name;
            private String value;
            private Collection<String> recommendedValues = new ArrayList<>();
            private Collection<String> errors = new ArrayList<>();
            private boolean visible = true;

            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }

            public Collection<String> getRecommendedValues() {
                return recommendedValues;
            }

            public Collection<String> getErrors() {
                return errors;
            }

            public boolean isVisible() {
                return visible;
            }

            @Override
            public String toString() {
                return "Value{"
                    + "name='" + name + '\''
                    + ", value='" + value + '\''
                    + ", recommendedValues=" + recommendedValues
                    + ", errors=" + errors
                    + ", visible=" + visible
                    + '}';
            }
        }
    }
}
