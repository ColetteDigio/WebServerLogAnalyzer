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
   - more mentioned in [future-implementation-ideas.md](future-implementation-ideas.md)


## Initial assumptions and thoughts
* Most active URLs - made 2 assumptions here
   1. take the whole url without extracting the domain
   2. extract the `http` out of url
* Think about :
   1. when the data / logs getting bigger, what are the solution to this?
   2. when the http methods are also including POST, PUT, DELETE? (Used Regex pattern matching)