package org.sourcelab.kafka.connect.apiclient;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.kafka.connect.apiclient.request.dto.ConnectorPluginConfigDefinition;
import org.sourcelab.kafka.connect.apiclient.request.dto.NewConnectorDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration test over ApiClient.
 *
 * Assumes kafka-connect is running at localhost.  More or less a sanity test
 * rather than checking assertions.
 */
public class ApiClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientTest.class);

    private ApiClient apiClient;
    private final String connectorName = "MyTestConnector";

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

    /**
     * Tests retrieving all connectors deployed.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors
     */
    @Test
    public void testGetConnectors() {
        logger.info("Result: {}", apiClient.getConnectors());
    }

    /**
     * Test getting a specific connector.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)
     */
    @Test
    public void testGetConnector() {
        logger.info("Result: {}", apiClient.getConnector(connectorName));
    }

    /**
     * Test getting a specific connectors config.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)
     */
    @Test
    public void testGetConnectorConfig() {
        logger.info("Result: {}", apiClient.getConnectorConfig(connectorName));
    }

    /**
     * Test getting the status of a specific connector.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)
     */
    @Test
    public void testGetConnectorStatus() {
        logger.info("Result: {}", apiClient.getConnectorStatus(connectorName));
    }

    /**
     * Test adding a connector.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)
     */
    @Test
    public void testAddConnector() {
        logger.info("Result: {}", apiClient.addConnector(NewConnectorDefinition.newBuilder()
            .withName(connectorName)
            .withConfig("connector.class", "org.apache.kafka.connect.tools.VerifiableSourceConnector")
            .withConfig("tasks.max", 3)
            .withConfig("topics", "test-topic")
            .build()
        ));
    }

    /**
     * Test updating a connector's config.
     * https://docs.confluent.io/current/connect/restapi.html#get--connectors-(string-name)
     */
    @Test
    public void testUpdateConnectorConfig() {
        final Map<String, String> config = new HashMap<>();
        config.put("connector.class", "org.apache.kafka.connect.tools.MockConnector");
        config.put("tasks.max", "10");
        config.put("topics", "test-topic");

        logger.info("Result: {}", apiClient.updateConnectorConfig(connectorName, config));
    }

    /**
     * Test restarting a connector.
     */
    @Test
    public void testRestartConnector() {
        logger.info("Result: {}", apiClient.restartConnector(connectorName));
    }

    /**
     * Test pausing a connector.
     */
    @Test
    public void testPauseConnector() {
        logger.info("Result: {}", apiClient.pauseConnector(connectorName));
    }

    /**
     * Test pausing a connector.
     */
    @Test
    public void testResumeConnector() {
        logger.info("Result: {}", apiClient.resumeConnector(connectorName));
    }

    /**
     * Test pausing a connector.
     */
    @Test
    public void testDeleteConnector() {
        logger.info("Result: {}", apiClient.deleteConnector(connectorName));
    }

    /**
     * Test retrieving tasks for a running connector.
     */
    @Test
    public void testGetConnectorTasks() {
        logger.info("Result: {}", apiClient.getConnectorTasks(connectorName));
    }

    /**
     * Test retrieving status about a specific task for a running connector.
     */
    @Test
    public void testGetConnectorTaskStatus() {
        logger.info("Result: {}", apiClient.getConnectorTaskStatus(connectorName, 0));
    }

    /**
     * Test restarting a specific task for a running connector.
     */
    @Test
    public void testRestartConnectorTask() {
        logger.info("Result: {}", apiClient.restartConnectorTask(connectorName, 0));
    }

    /**
     * Test retrieving available connector plugins.
     */
    @Test
    public void testGetConnectorPlugins() {
        logger.info("Result: {}", apiClient.getConnectorPlugins());
    }

    /**
     * Test retrieving available connector plugins.
     */
    @Test
    public void testValidateConnectorPluginConfig() {
        logger.info("Result: {}", apiClient.validateConnectorPluginConfig(ConnectorPluginConfigDefinition.newBuilder()
            .withName("VerifiableSourceConnector")
            .withConfig("connector.class", "org.apache.kafka.connect.tools.VerifiableSourceConnector")
            .withConfig("tasks.max", 3)
            .withConfig("topics", "test-topic")
            .build()
        ));
    }
}