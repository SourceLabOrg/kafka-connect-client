package org.sourcelab.kafka.connect.apiclient;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration test over ApiClient.
 *
 * Assumes kafka-connect is running at localhost.
 */
public class ApiClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientTest.class);

    private ApiClient apiClient;

    @Before
    public void setup() {
        // Pull apiHost from environment
        String apiHost = System.getenv("KAFKA_CONNECT_HOST");
        if (apiHost == null || apiHost.isEmpty()) {
            apiHost = "localhost:8083";
        }
        // Build api client
        this.apiClient = new ApiClient(new Configuration(apiHost));
    }

    @Test
    public void testGetConnectors() {
        logger.info("Result: {}", apiClient.getConnectors());
    }

    @Test
    public void testGetConnector() {
        logger.info("Result: {}", apiClient.getConnector("My Test Connector"));
    }

    @Test
    public void testGetConnectorConfig() {
        logger.info("Result: {}", apiClient.getConnectorConfig("My Test Connector"));
    }

    @Test
    public void testGetConnectorStatus() {
        logger.info("Result: {}", apiClient.getConnectorStatus("My Test Connector"));
    }

    @Test
    public void testAddConnector() {
        apiClient.addConnector(ConnectorDefinition.newBuilder()
            .withName("My Test Connector")
            .withConfig("connector.class", "org.apache.kafka.connect.tools.MockConnector")
            .withConfig("tasks.max", 10)
            .withConfig("topics", "test-topic")
            .build()
        );
    }

    @Test
    public void testUpdateConnectorConfig() {
        final Map<String, String> config = new HashMap<>();
        config.put("connector.class", "org.apache.kafka.connect.tools.MockConnector");
        config.put("tasks.max", "10");
        config.put("topics", "test-topic");

        logger.info("Result: {}", apiClient.updateConnectorConfig("My Test Connector", config));
    }
}