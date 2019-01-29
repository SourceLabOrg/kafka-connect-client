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
    <version>1.0.3</version>
</dependency>
```


##### Example Code:
```java
/*
 * Create a new configuration object.
 *
 * This configuration also allows you to define some optional details on your connection,
 * such as using an outbound proxy (authenticated or not).
 */
final Configuration configuration = new Configuration("http://hostname.for.kafka-connect.service.com:8083");

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

Public methods available on KafkaConnectClient can be [found here](src/main/java/org/sourcelab/kafka/connect/apiclient/KafkaConnectClient.java#L62)


##### Communicating with HTTPS enabled Kafka-Connect REST server:
```java
/*
 * Create a new configuration object.
 *
 * This configuration also allows you to define some optional details on your connection,
 * such as using an outbound proxy (authenticated or not).
 */
final Configuration configuration = new Configuration("https://hostname.for.kafka-connect.service.com:8083");

/*
 * If you have a JKS formatted TrustStore file to validate your Kafka-Connect host's certificate with, 
 * you can provide it to the configuration.
 */
configuration.useTrustStore(
    new File("/path/to/truststore.jks"), "TrustStorePasswordHere (Optional)"
);

/*
 * Optionally, you can disable all verifications of Kafka-Connect's SSL certificates.
 * Doing this is HIGHLY discouraged and defeats most of the purpose of using SSL in the first place.
 */
//configuration.useInsecureSslCertificates();

/*
 * Create an instance of KafkaConnectClient, passing your configuration.
 */
final KafkaConnectClient client = new KafkaConnectClient(configuration);

```

## Changelog

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

[View Changelog](CHANGELOG.md)



