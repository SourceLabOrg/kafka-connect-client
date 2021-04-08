# Change Log
The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## 3.1.1 (04/08/2021)

#### Bugfixes
- [Issue-51](https://github.com/SourceLabOrg/kafka-connect-client/issues/51) Can't set request timeout
  Previously `ConfigurationBuilder::useRequestTimeoutInSeconds()` was incorrectly setting the request time to live property instead of the request time out property.  It's been updated to correctly set the request timeout value on the underlying http client.
  Additionally `ConfigurationBuilder::useConnectionTimeToLiveInSeconds()` was added to allow for setting the request time to live property on the underlying http client.
  

  Thanks [IvanZhilyakov28](https://github.com/IvanZhilyakov28) for supplying the bugfix!

#### Internal Dependency Updates
- com.fasterxml.jackson.core from 2.10.4 -> 2.12.2
- org.apache.httpcomponents from 4.5.12 -> 4.5.13
- com.google.guava:guava from 29.0-jre -> - 30.1.1-jre

## 3.1.0 (05/10/2020)

### New Features
- Add support for `/connectors/<connector-name>/topics` and `/connectors/<connector-name>/topics/reset` endpoints 
added in Kafka 2.5.0 via `getConnectorTopics()` and `resetConnectorTopics()` methods on KafkaConnectClient.

#### Internal Dependency Updates
- com.google.guava:guava from 28.2-jre -> 29.0-jre
- org.apache.httpcomponents from 4.5.11 -> 4.5.12
- com.fasterxml.jackson.core from 2.10.2 -> 2.10.4
- Checkstyle plugin from 8.24 -> 8.32

## 3.0.0 (03/20/2020)

#### Possible Breaking Dependency Change

- Removed `org.apache.logging.log4j` dependency, instead relying on the org.slf4j logging interface/facade dependency explicitly.
  - If your project was **NOT** depending on this transitive dependency, **no changes are required to upgrade**.
  - If your project **WAS** depending on this transitive dependency, you may need to add it to your own project:

  ```xml
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-api</artifactId>
      <version>2.12.1</version>
  </dependency>
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.12.1</version>
  </dependency>
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
      <version>2.12.1</version>
  </dependency>
  ```

#### Internal Dependency Updates
- Checkstyle plugin from 8.19 -> 8.24
- com.fasterxml.jackson.core from 2.9.9 -> 2.10.2
- org.apache.logging.log4j from 2.11.2 -> 2.12.1
- org.apache.httpcomponents from 4.5.8 -> 4.5.11
- com.google.guava:guava from 27.1-jre -> 28.2-jre
- org.eclipse.jetty:jetty-server (test dependency) from 9.4.17.v20190418 -> 9.4.27.v20200227
- org.mockito:mockito-core (test dependency) from 2.27.0 -> 2.28.2

## 2.1.0 (06/26/2019)

### New Features
- Added support to retrieve information about the Kafka-Connect server being queried.

```java
    /**
     * Retrieve details about the Kafka-Connect service itself.
     * @return ConnectServerVersion
     */
    public ConnectServerVersion getConnectServerVersion()
```

- Added support for [Expanded Connectors API Endpoint KIP-465](https://cwiki.apache.org/confluence/display/KAFKA/KIP-465%3A+Add+Consolidated+Connector+Endpoint+to+Connect+REST+API).

Only supported by Kafka-Connect servers running version 2.3.0+ the following methods were added:

```java
    /**
     * Get a list of deployed connectors, including the status for each connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors
     *
     * Requires Kafka-Connect 2.3.0+
     *
     * @return All deployed connectors, and their respective statuses.
     */
    public ConnectorsWithExpandedStatus getConnectorsWithExpandedStatus()

    /**
     * Get a list of deployed connectors, including the definition for each connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors
     *
     * Requires Kafka-Connect 2.3.0+
     *
     * @return All deployed connectors, and their respective definition.
     */
    public ConnectorsWithExpandedInfo getConnectorsWithExpandedInfo()

    /**
     * Get a list of deployed connectors, including all metadata available.
     * Currently includes both 'info' {@see getConnectorsWithExpandedInfo} and 'status' {@see getConnectorsWithExpandedStatus}
     * metadata.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors
     *
     * Requires Kafka-Connect 2.3.0+
     *
     * @return All deployed connectors, and their respective metadata.
     */
    public ConnectorsWithExpandedMetadata getConnectorsWithAllExpandedMetadata()
```

## 2.0.2 (06/06/2019)

### Internal Dependency Updates
- Update Jackson dependency from 2.9.8 to 2.9.9 [CVE-2019-12086](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2019-12086).

## 2.0.1 (04/23/2019)

### Bugfix
- Updated ConnectorPluginConfigValidationResults classes to be publicly scoped. Thanks [tchiotludo](https://github.com/tchiotludo)!

### Internal Dependency Updates
- Updated Google Guava from 27.0.1-JRE to 27.1-JRE
- Updated HttpComponents from 4.5.7 to 4.5.8 

## 2.0.0 (04/05/2019)

### Bugfix / Breaking Change

**PR**: [Fix connector tasks & plugins query](https://github.com/SourceLabOrg/kafka-connect-client/pull/19)

[tchiotludo](https://github.com/tchiotludo) discovered and fixed a bug in the `KafkaConnectClient::getConnectorPlugins()` and 
`KafkaConnectClient::getConnectorTasks()` client methods.  

Previous to this version these methods incorrectly returned a
`Collection<Map<>>` instead of `Collection<ConnectorPlugin>` and `Collection<Task>` respectively.

This release fixes these two methods to return the correct types as defined by their signatures. Any users of this library who were interacting with this incorrect return type will need to update their code for this release.

## 1.3.0 (02/06/2019)

### New Features
- Added ability to configure sending a client certificate in order to authenticate Kafka-Connect REST endpoints.

## 1.2.0 (02/02/2019)

### New Features
- Added ability to authenticate with Kafka-Connect REST endpoints utilizing Basic-Authentication.

## 1.1.0 (01/30/2019)

### New Features
- Added ability to communicate with Kafka-Connect REST endpoints using SSL.  More can be found in README.

### Internal Dependency Updates
- Updated Guava from 24.0-JRE to 27.0-JRE for [CVE-2018-10237](https://github.com/google/guava/wiki/CVE-2018-10237).
- Replace internal calls to Guava's Preconditions with Objects.requireNonNull()
- Jackson updated from 2.9.5 to 2.9.8 for several CVEs.

## 1.0.3 (04/16/2018)
- More specific exception classes thrown when requests fail.

## 1.0.2 (04/12/2018)
- More specific exception classes thrown when requests fail.
- Mark internal classes as not final to allow mocking more easily.

## 1.0.1 (04/05/2018)
- Update Jackson dependency to 2.9.5 [CVE-2018-7489](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2018-7489).

## 1.0.0 (03/01/2018)
- Initial release!