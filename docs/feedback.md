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
    - Batch processing is implemented - see [performance-testing.md](performance-testing.md) for more details
    - Tools such as kafka and scheduler might be useful when there's a need to process large amount of data
6. Static method - treat it as it was a bigger exercise. We won't be using static method everywhere (done)
7. QA manager - what other cases would you think of to make the app more production ready?
   - empty file (done)
   - encoding (done)
   - handling large log file (done)
   - LOGGING! (done)
   - validate IP format (done)
   - What if the IP OR URL of a log is empty - skip the line (done)
   - more mentioned in [future-implementation-ideas.md](future-implementation-ideas.md)


## Initial assumptions and thoughts
* Most active URLs - made 2 assumptions here
   1. take the whole url without extracting the domain
   2. extract the `http` out of url
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
1. 100% number is quite vague, what am I trying to achieve here? Do I want to test all the lines or branches
2. Depending on the unit test coverage tools we are using, they sort of have a different metrics on how to measure the unit test coverage. 
3. Setting it at 80%, apart from testing the functionality and the edge cases, it is at least a metric number that showed the client that there's a good amount of unit test coverage

# JVM tuning and learning
1. A few factors involved and dependent on how the log file application processing the logs.
2. In this application, the logReader read the log in the batch of 1000, store in the memory and analyzed the data all at once.
3. Due to the nature of how this application is written, the whole read log file will be stored in memory, hence it requires a significant heap size higher than the log file itself.
4. In order to reduce the heap memory size, it would be a good idea to process the file in chunks as well.
5. To tune the required heap size for this application, being with a heap size of 7-8GB
6. Monitor memory usage - using `JConsole`
7. Adjust the heap size accordingly depending on how the app perform
8. Garbage collection tuning