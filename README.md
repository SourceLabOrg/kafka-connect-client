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
    <version>1.0.0</version>
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
 * Create an instance of ApiClient, passing your configuration.
 */
final ApiClient client = new ApiClient(configuration);

/*
 * Making requests by calling the public methods available on ApiClient.
 */
```

Public methods available on ApiClient can be [found here](blob/master/src/main/java/org/sourcelab/kafka/connect/apiclient/ApiClient.java#L101-L266)

## How to Contribute 

Want to help implement the missing API end points?  Fork the repository, write some code, and 
submit a PR to the project!

## Changelog

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

[View Changelog](CHANGELOG.md)



