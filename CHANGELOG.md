# Change Log
The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

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