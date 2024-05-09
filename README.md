# The Programming Task

The task is to parse a log file containing HTTP requests and to report on its contents. For a given log file we want to know,
* The number of unique IP addresses
* The top 3 most visited URLs
* The top 3 most active IP addresses


Chosen Tech:
* Java

## Project startup
``` shell script
$ git clone git@github.com:ColetteDigio/WebServerLogAnalyzer.git
```

## Run application
``` shell script
$ mvn clean install
$ mvn clean package

$ java -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar
```

## Run unit tests
``` shell script
$ mvn test -Dtest=WebServerLogAnalyzerTest
```

## Run JaCoCo report 
``` shell script
$ mvn clean verify
$ mvn jacoco:report
```
Jacoco test coverage report can be found in `target/jacoco/index.html`


## Creation of Test Data
* `test_log_file.log` was created by duplicating different logs in a specific number for testing purpose. i.e.:
copy and paste this log 3 times to get 4 entry in the log
```
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
```

## Assumptions and thoughts
* Most active URLs - made 2 assumptions here
  1. take the whole url without extracting the domain
  2. extract the `http` and `domain` out of url
* Think about :
  1. when the data / logs getting bigger, what are the solution to this? 
  2. when the http methods are also including POST, PUT, DELETE?

## Thought process
1. Understand the task on high level
2. Breaking down the task
3. Write the simplest code to get the functionalities working
4. Unit tests to verify implementation
5. Think about how to improve the code base
6. Improve set up process 
7. Documentation

## Feedback / More TODOs
1. How is the jar file execute in production?
2. HTTP method - should also take in other http methods
3. The list that is showing (result) - how do I know the information shown is accurate?
4. Evidence of test coverage - Jacoco report
    - Jacoco dependency into pom file
5. What happen when the file gets too big, think about another implementation that may take care of big log file. check JVM 
    - How do I run and test performance on my local?
6. Static method - treat it as it was a bigger exercise. We won't be using static method everywhere
7. QA manager - How would someone else run the test and knows the app is running?