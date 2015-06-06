# dropwizard-example
[![Build Status](https://travis-ci.org/rufer7/dropwizard-example.svg?branch=master)](https://travis-ci.org/rufer7/dropwizard-example)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/rufer7/dropwizard-example/blob/master/LICENSE)

A sample dropwizard project based on the [Dropwizard getting started](http://www.dropwizard.io/getting-started.html).

## Run application
To run the application perform the following steps.

1. Checkout source code
2. Run maven `clean install`
3. Go to the project root directory
4. Execute `java -jar target/dropwizard-example-0.0.1-SNAPSHOT.jar`

### Endpoints

**Example endpoint, which returns message with default name**

http://localhost:8080/example

**Example endpoint, which returns message with name passed as query parameter**

http://localhost:8080/example?name=me

**Operational tools**

http://localhost:8081/

## Useful links

* [Dropwizard documentation](http://flywaydb.org/documentation)
* [Dropwizard getting started](http://www.dropwizard.io/getting-started.html)
