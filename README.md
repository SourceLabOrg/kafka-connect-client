# Kafka-Connect Java REST API Client

[![Build Status](https://travis-ci.org/SourceLabOrg/kafka-connect-client.svg?branch=master)](https://travis-ci.org/SourceLabOrg/kafka-connect-client)

## What is it? 

This library provides an easy to use client library for interacting with the [Kafka-Connect](https://docs.confluent.io/current/connect/references/restapi.html) REST service (V4). 

## How to use this library

This client library is released on Maven Central.  Add a new dependency to your project's POM file:

```xml
<dependency>
    <groupId>org.sourcelab</groupId>
    <artifactId>kafka-connect-client</artifactId>
    <version>3.1.0</version>
</dependency>
```


#### Example Code:
```java
/*
 * Create a new configuration object.
 *
 * This configuration also allows you to define some optional details on your connection,
 * such as using an outbound proxy (authenticated or not), SSL client settings, etc..
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

/*
 * See KafkaConnectClient for other available operations.
 */
```

Public methods available on KafkaConnectClient can be [found here](src/main/java/org/sourcelab/kafka/connect/apiclient/KafkaConnectClient.java#L62)


#### Communicating with HTTPS enabled Kafka-Connect REST server:
```java
/*
 * Create a new configuration object.
 */
final Configuration configuration = new Configuration("https://hostname.for.kafka-connect.service.com:8083");

/*
 * If your JVM's TrustStore has already been updated to accept the certificate installed on your Kafka-Connect 
 * instance, then no further configuration is required. Typically this is done using the 'key-tool' command.
 * 
 * Alternatively, you can configure the path to JKS formatted TrustStore file to validate the host's certificate
 * with.
 */
configuration.useTrustStore(
    new File("/path/to/truststore.jks"), "Optional Password Here or NULL"
);

/*
 * Optionally instead of providing a TrustStore, you can disable all verifications of Kafka-Connect's SSL certificates.
 * 
 * Doing this is HIGHLY DISCOURAGED!
 */
//configuration.useInsecureSslCertificates();

/*
 * If your Kafka-Connect instance is configured to validate client certificates, you can configure a KeyStore for
 * the client to send with each request:
 */
configuration.useKeyStore(
    new File("/path/to/keystore.jks"), "Optional Password Here or NULL"    
);

/*
 * Create an instance of KafkaConnectClient, passing your configuration.
 */
final KafkaConnectClient client = new KafkaConnectClient(configuration);

```

# Contributing

Found a bug? Think you've got an awesome feature you want to add? We welcome contributions!


## Submitting a Contribution

1. Search for an existing issue. If none exists, create a new issue so that other contributors can keep track of what you are trying to add/fix and offer suggestions (or let you know if there is already an effort in progress).  Be sure to clearly state the problem you are trying to solve and an explanation of why you want to use the strategy you're proposing to solve it.
1. Fork this repository on GitHub and create a branch for your feature.
1. Clone your fork and branch to your local machine.
1. Commit changes to your branch.
1. Push your work up to GitHub.
1. Submit a pull request so that we can review your changes.

*Make sure that you rebase your branch off of master before opening a new pull request. We might also ask you to rebase it if master changes after you open your pull request.*

## Acceptance Criteria

We love contributions, but it's important that your pull request adhere to some of the standards we maintain in this repository. 

- All tests must be passing!
- All code changes require tests!
- All code changes must be consistent with our checkstyle rules.
- Great inline comments.

# Other Notes


## Releasing

Steps for proper release:
- Update release version: `mvn versions:set -DnewVersion=X.Y.Z`
- Validate and then commit version: `mvn versions:commit`
- Update CHANGELOG and README files.
- Merge to master.
- Deploy to Maven Central: `mvn clean deploy -P release`
- Create release on Github project.


## Changelog

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

[View Changelog](CHANGELOG.md)



