## Project startup
``` shell script
$ git clone git@github.com:ColetteDigio/WebServerLogAnalyzer.git
```


# Profiles
The application and unit test can be run with the following Maven profiles enabled:
- *local* - tag `-Plocal`
- *prod* - tag `-Pprod`
- To run the application with dev config, add the `-Plocal` tag to the command line, i.e.: `mvn clean install -Plocal`
- To showcase the properties files are in working state, running the application with `prod` profile will throw `FileNotFoundException` as the file `prod-programming-task-example-data.log` does not exist. 


# Configuration Files
**:warning: Important! Do not store environment configuration in this repository (especially secrets)! That's what
the environment repositories are for.**

To run the application with special configuration - please create a
file named `config.${special-env}-.properties` in `/src/main/resources` and put your configuration in there. Then you can enable
it with the `${special-env}` profile. For more complex scenarios, feel free to use tools like Docker Compose.


## Run application
``` shell script
$ mvn clean install 
$ mvn clean package

$ java -Xmx8g -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Run application to analyse a log file from a directory

``` shell script
$ java -Xmx8g -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar ${path/to-your/file} 
```
OR
``` shell script
$ java -Xmx8g -cp target/WebServerLogAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar au.com.org.WebServerLogAnalyzerApp ${path/to-your/file} 
```
**Note:
  - tuning the -Xmx8g is based on the assumption the log file size around 4-5GB
  - Adding the  tag `-cp`, provide where the `main` function is and `filePath` to the file to run analysis from command line
  - modified the code to either take `filePath` as part of the command-line argument OR use the default path from config file.

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

## Creation of Test Data
`test_log_file.log` was created by duplicating different logs in a specific number for testing purpose. i.e.:
copy and paste this log 3 times to get 4 entry in the log
```
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] "GET /to-an-error HTTP/1.1" 500 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0"
```
## Running Performance test
The purpose of this performance test is to ensure that the application does not run out of HEAP memory, especially when processing very large log files. Follow these steps to monitor the application's performance:

a. Open JConsole:
- In your terminal, run the command `jconsole`
- This executable can be found in the bin directory of your JDK installation (JDK_HOME/bin)
- Note: If bin is included in your system's PATH variable, the command will run directly

b.Start the Application:
- Run your application as you normally would

c. Identify Application PID:
- JConsole will open a window displaying a list of running Java processes
- Note down the Process ID (PID) of your application. For example, in the image below, the PID is `99793`
![jconsole-pop-up.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Fjconsole-pop-up.png)

d. Connect to the Process:
- You can connect to the application in one of two ways:
    - Command Line: Run `jconsole ${processID}` (replace processID with the actual PID of your application).
    - GUI: Click the Connect button and select Insecure connection (knowing the application is running locally).

e. Monitor Performance:
- JConsole will display several graphs and tabs showing the performance metrics of your application, including memory usage, CPU usage, and more.

f. To terminate the performance testing, simply stop the application