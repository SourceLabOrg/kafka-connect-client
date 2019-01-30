# Change Log
The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## 1.2.0 (UNRELEASED)


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