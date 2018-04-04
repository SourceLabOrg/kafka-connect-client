# Kafka-Connect Java REST API Client

## What is it? 

This library intends to give you an easy way to interact with the [Kafka-Connect](https://docs.confluent.io/current/connect/restapi.html) REST service (V4). 

**Note** Use this library at your own risk!  Currently there are no known issues, but as an **unofficial** library,
 there are no guarantees.  

## How to use this library

This client library is released on Maven Central.  Add a new dependency to your project's POM file:

```xml
<dependency>
    <groupId>org.sourcelab</groupId>
    <artifactId>kafka-connect-client</artifactId>
    <version>1.0.1</version>
</dependency>
```

Example Code:
```java
/*
 * Create a new configuration object.
 *
 * This configuration also allows you to define some optional details on your connection,
 * such as using an outbound proxy (authenticated or not).
 */
final Configuration configuration = new Configuration("hostname.for.kafka-connect.service.com:8083");

/*
 * Create an instance of KafkaConnectClient, passing your configuration.
 */
final KafkaConnectClient client = new KafkaConnectClient(configuration);

/*
 * Making requests by calling the public methods available on ApiClient.
 * 
 * For example, get a list of deployed connectors:
 */
final Collection<String> connectorList = client.getConnectors();

/*
 * Or to deploy a new connector:
 */
final ConnectorDefinition connectorDefition = client.addConnector(NewConnectorDefinition.newBuilder()
    .withName("MyNewConnector")
    .withConfig("connector.class", "org.apache.kafka.connect.tools.VerifiableSourceConnector")
    .withConfig("tasks.max", 3)
    .withConfig("topics", "test-topic")
    .build()
));
```

Public methods available on KafkaConnectClient can be [found here](src/main/java/org/sourcelab/kafka/connect/apiclient/ApiClient.java#L101-L266)

## Changelog

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

[View Changelog](CHANGELOG.md)



