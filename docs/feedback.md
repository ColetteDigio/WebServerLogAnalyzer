## Feedback / More TODOs
1. How is the jar file execute in production?
   - used `maven-assembly-plugin`
   - this tool will create an executable JAR file that includes all dependencies
   - run `mvn clean package` will create this JAR file and made it executable JAR that includes all dependencies
   - in reality, it should test the packaged JAR file in staging or test env to ensure everything works as expected.
   
   * for unit test command line:
   - Maven profiles - create customised build configurations - targeting a level of test granularity or specific deployment env.

2. HTTP method - should also take in other http methods (done)

3. The result list that is showing - how do I know which 1 has the 1 request? (done)
   - improved readability of print result.
4. Evidence of test coverage - Jacoco report (done)
    - Jacoco dependency into pom file
    - introduce minimal test coverage 80%

5. What happen when the file gets too big, think about another implementation that may take care of big log file. check JVM
    - How do I run and test performance on my local? (done)

6. Static method - treat it as it was a bigger exercise. We won't be using static method everywhere (done)
7. QA manager - what other cases would you think of to make the app more production ready?
   - empty file (done)
   - encoding (done)
   - handling large log file (done)
   - LOGGING! (done)
   - validate IP format (done)
   - What if the IP OR URL of a log is empty - skip the line (done)


## Initial assumptions and thoughts
* Most active URLs - made 2 assumptions here
   1. take the whole url after the HTTP methods, and before HTTP version
* Think about :
   1. when the data / logs getting bigger, what are the solution to this?
   2. when the http methods are also including POST, PUT, DELETE? (Used Regex pattern matching)


# Round 2 - Feedback
## Things to improve:
1. LOGGING
   - Improve logging message
   - when the App is running, it would be good to have some meaningful logging message (ie: timestamp) to indicate the app is running and processing (done - check end time)
   - exception message
2. UNIT TEST COVERAGE AND UNIT TEST CASES
   - it should be 100% as that should be the threshold to ensure everything is tested
   - since the assumptions of the app also handle different http method - add unit test, check other possible scenarios 
   - Revisit the exclusions in pom file
3. NAMING CONVENTION
   - Improve naming conventions of methods name
4. SEPARATE LOGIC further
   - I.e.: separate the logic further (ie: checkLogFile is doing 2 jobs)
5. RUNNING THE APP AND JVM
   - JVM - specified the size into the command line to allow specific size in order to run. Try again after bug fix with batchProcessing logic
   - Using command line to tell the app which file to run from.
6. BUGS to FIX
   - BatchProcessing logic - The logic is only parsing the batchLogs not the whole logs
   - Read from Config properties


# Comments and realization through 100% unit test coverage
1. 100% number is quite an arbitrary number, during the refactoring of unit tests and tried to achieved 100% unit tests coverage, I am asking the following questions:
   - Do I really need to cover all the lines or branches
   - What are the benefit of testing the logs line and if the batchCounter increment?
   - If the increment didn't work, the app would have lead to many other bugs
   - Depending on the unit test coverage tools we are using, they sort of have a different metrics and measure on how to measure the unit test coverage. 
2. The reason for setting it at 80%, apart from testing the functionality and the edge cases, it is at least a good percentage to showed the client / team that there's a good amount of unit test coverage

# JVM tuning and learning
1. A few factors involved and dependent on how the log file application processing the logs.
2. In this application, the logReader read the log in the batch of 1000, store in the memory and analyzed the data all at once.
3. Due to the nature of how this application is written, the whole read log file will be stored in memory, hence it requires a significant heap size higher than the log file itself.
4. Given an example of the log file being around 5GB, It would be a good idea to set the heap size for this application around 7-8GB
5. Monitor memory usage - using `JConsole`
6. Adjust the heap size accordingly depending on how the app perform
7. Garbage collection `-XX:+UseG1GC` can also be enabled as this is designed to handle large heaps memory
   - it helps by breaking down the HEAP into manageable regions
   - ie: big toys room like our app
   - divide room into zones - so only look into few zones to find toys or to clean up, instead of searching whole room
   - quick clean up - normally set a time frame and get magic helper to clean every x interval time
   - moving toys to make space - if one zone gets full, the helper also move some toys from full zone to empty zone. Make more space and keep things organised
   - special zone for big toys = if there's big data, it gets special zone - the helper knows how to handle them without making a mess
   - that's how garbage collection keeps memory room tidy and ensure program run smoothly without long pauses.

# JVM learning
1. JDK - Java Development Kit
   - this is where java code compile
   - require to convert code to bytecodes in order to run on the machine, which the machine is called JVM
   - once downloaded a JDK, it comes with updated JRE and JVM
2. JRE - Java Runtime Environment
   - JRE contains all the in-built classes and other extra files and libraries that required to run the code
   - JRE validates the byte code and loads classes
   - JVM is part of JRE
3. JVM - Java Virtual Machine
   - running the bytecodes that the computer understand and able to run the code
   - codes compiled through JDK and run the code through JVM
   - JDK is upper layer, mid layer JRE, inner layer JVM
4. classpath tag `-cp` 
   - specifying the classpath for JVM, it tells JVM to find the compiled classes or JAR files needed to run the app
   - in another word, classpath is a parameter in JVM / Java compiler that specifies the location of user defined classes and packages
   - to run Java app from terminal, the `-cp` allows developer to define where the Java runtime should look for the compiled bytecodes that needs to execute.
   - so without modifying the code, the app can be run with the following command line without repackaged:
     `java -cp path/to/your.jar au.com.org.WebServerLogAnalyzerApp /path/to/logfile.log`