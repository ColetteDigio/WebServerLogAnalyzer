## Feedback / More TODOs
1. How is the jar file execute in production?
   - used `maven-assembly-plugin`
   - this tool will create an executable JAR file that includes all dependencies
   - run `mvn clean package` will create this JAR file and made it executable JAR that includes all dependencies
   - in reality, it should test the packaged JAR file in staging or test env to ensure everything works as expected.

2. HTTP method - should also take in other http methods (done)

3. The result list that is showing - how do I know which 1 has the 1 request? (done)

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