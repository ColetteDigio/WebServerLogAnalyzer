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
Jacoco test coverage report can be read in `target/jacoco/index.html` with browser of your choice.


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
  2. when the http methods are also including POST, PUT, DELETE? (Used Regex pattern matching)

## Testing Performance
1. Tools - JConsole, JMH, MBean
2. How to test the current "speed", what to look out for?
3. Identify the part of the code that slowing down the execution, and is it possible to refactor to improve its efficiency?
4. Create a log file big enough to crash the app after knowing the metrics
5. Retest the code and observe its efficiency? test it again with refactored codes and see how it improves


## Feedback / More TODOs
1. How is the jar file execute in production? (TBC)
2. HTTP method - should also take in other http methods (done)
3. The result list that is showing - how do I know which 1 has the 1 request? (done)
4. Evidence of test coverage - Jacoco report (done)
    - Jacoco dependency into pom file
    - introduce minimal test coverage %
5. What happen when the file gets too big, think about another implementation that may take care of big log file. check JVM 
    - How do I run and test performance on my local? (TBC)
6. Static method - treat it as it was a bigger exercise. We won't be using static method everywhere (done)
7. QA manager - what other cases would you think of to make the app more production ready?
   - empty file (done)
   - encoding (done)
   - handling large log file (same as point 5)
   - performance 
   - LOGGING! (done)



