/**
 * Copyright 2018, 2019 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents result from /connectors/[topic-name]/topics REST end point.
 */
@JsonDeserialize(using = ConnectorTopics.Deserializer.class)
public class ConnectorTopics {
    private final String name;
    private final List<String> topics;

    /**
     * Constructor.
     * @param name Name of the connector.
     * @param topics List of topics.
     */
    public ConnectorTopics(final String name, final List<String> topics) {
        this.name = Objects.requireNonNull(name);
        Objects.requireNonNull(topics);
        this.topics = Collections.unmodifiableList(topics.stream().sorted().collect(Collectors.toList()));
    }

    public String getName() {
        return name;
    }

    public List<String> getTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "ConnectorTopics{"
            + "name='" + name + '\''
            + ", topics=" + topics
            + '}';
    }

    /**
     * Deserializer for ConnectorTopics.
     */
    public static class Deserializer extends StdDeserializer<ConnectorTopics> {

        /**
         * Constructor.
         */
        public Deserializer() {
            this(null);
        }

        /**
         * Constructor.
         */
        public Deserializer(final Class<?> vc) {
            super(vc);
        }

        @Override
        public ConnectorTopics deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
            final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            final Iterator<Map.Entry<String, JsonNode>> children = node.fields();

            while (children.hasNext()) {
                final Map.Entry<String, JsonNode> child = children.next();
                final String name = child.getKey();
                final JsonNode childNode = child.getValue();

                // Skip unknown entries
                if (JsonNodeType.OBJECT != childNode.getNodeType()) {
                    continue;
                }

                // If has no topics key
                if (!childNode.has("topics") || JsonNodeType.ARRAY != childNode.get("topics").getNodeType()) {
                    // Skip?
                    continue;
                }
                final List<String> topicNames = new ArrayList<>();
                if (!childNode.get("topics").isEmpty()) {
                    // Parse topic name values out
                    childNode.get("topics").forEach((entry) -> topicNames.add(entry.textValue()));
                }
                return new ConnectorTopics(name, topicNames);
            }
            throw new JsonParseException(jsonParser, "Unable to parse response JSON");
        }
    }
}
