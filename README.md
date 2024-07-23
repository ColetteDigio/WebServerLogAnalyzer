# Web Server Log Analyzer

## Introduction
This application is to parse a log file containing HTTP requests and to report on its contents. 
For a given log file we want to know,
* The number of unique IP addresses
* The top 3 most visited URLs
* The top 3 most active IP addresses

Chosen Tech:
* Java
* JUnit - unit tests
* Jacoco - test coverage
* slf4j for logging

## Run application
``` shell script
$ mvn clean install 
$ mvn clean package

$ java -Xmx2048m -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar 
```

## Run application to analyse a log file from a directory

``` shell script
$ java -Xmx2048m -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar  ${path/to-your/file} 
```

## Run unit tests
``` shell script
$ mvn verify -Plocal -Dtest.unit.skip=false
```

## Run JaCoCo report
``` shell script
$ mvn clean verify
$ mvn jacoco:report
```
Jacoco test coverage report can be read in `target/jacoco/index.html` with browser of your choice.


[//]: # (consider if this is required)
# Profiles
The application and unit test can be run with the following Maven profiles enabled:
- *local* - tag `-Plocal`
- *prod* - tag `-Pprod`
- To run the application with dev config, add the `-Plocal` tag to the command line, i.e.: `mvn clean install -Plocal`
- To showcase the properties files are in working state, running the application with `prod` profile will throw `FileNotFoundException` as the file `prod-programming-task-example-data.log` does not exist.

## Future Possibilities
[future-implementation-ideas.md](docs%2Ffuture-implementation-ideas.md)

## Round 1 Feedback and Implementation
[round-1-feedback.md](docs%2Fround-1-feedback.md)
