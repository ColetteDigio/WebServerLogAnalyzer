# The Task

The task is to parse a log file containing HTTP requests and to report on its contents. For a given log file we want to know,
* The number of unique IP addresses
* The top 3 most visited URLs
* The top 3 most active IP addresses


Chosen Tech:
* Java


## Installation
``` 
mvn clean install
mvn clean package

java -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar
```

## Run tests
```
mvn test -Dtest=WebServerLogAnalyzerTest
```

## Assumptions and thoughts
* Most active IP addresses - same IP address with the URL path excluding domain
* Think about when the data / logs getting bigger, what are the solution to this?
* Error handling