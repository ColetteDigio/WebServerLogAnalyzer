## Project startup
``` shell script
$ git clone git@github.com:ColetteDigio/WebServerLogAnalyzer.git
```

## Run application
``` shell script
$ mvn clean install
$ mvn clean package

$ java -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar
[//]: # (TODO How is the jar file execute in production)
```

## Run unit tests
``` shell script
$ mvn test -Dtest=WebServerLogAnalyzerTest 

[//]: # (TODO improve this command line)
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
