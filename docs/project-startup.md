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

$ java -Xmx2048m -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar 
```

## Run application to analyse a log file from a directory

``` shell script
$ java -Xmx2048m -jar target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar  ${path/to-your/file} 
```
OR
``` shell script
$ java -Xmx2024m -cp target/WebServerLogAnalyzer-1.0-SNAPSHOT.jar au.com.org.WebServerLogAnalyzerApp ${path/to-your/file} 
```
**Note:
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