## Testing Performance
1. Tools - JConsole, JMH, MBean (https://www.baeldung.com/java-management-extensions)
2. How to test the current speed, memory usage, what to look out for?
    - what is the performance goals? response time, CPU, memory usage?
3. Identify the part of the code that slowing down the execution, and is it possible to refactor to improve its efficiency?
4. Create a log file big enough to crash the app after knowing the metrics 
5. Retest the code and observe its efficiency? test it again with refactored codes and see how it improves
* Using profiling in intelliJ was first preference but community CE version do not have such feature

* Set a *goal* for performance to be improved
* In this application, the goal of the performance testing to avoid `running out of memory`
* Currently, according to MBean, the application is using 34.6Mb to run the app, the maximum available memory is 17.2GB
- Initial scenario when reading `programming-task-example-data.log`
![Initial-memory-usage.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2FInitial-memory-usage.png)

* To test how the application behave, I wrote a script to create a large log file (exceeding 17.2GB)
    - After imported the `large-log-file.log`, and run the application again 
    - Note: the `large-log-file.log` has been removed from this app, only used for performance testing purpose, please ask if you want the file
     ![reached-max-capacity.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Freached-max-capacity.png)
  
    - The log is showing the application ran into out of memory error due to the HEAP memory being use
      ![out-of-memory-error.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Fout-of-memory-error.png)
      ![max-heap-capacity.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Fmax-heap-capacity.png)

* To resolve running out of memory issue, splitting the log reading into batch of 1000, below are the diagram of the result:
  ![improved-memory-usage-graphs.png](src%2Fmain%2Fresources%2Fimages%2Fimproved-memory-usage-graphs.png)
  ![improved-heap-memory.png](src%2Fmain%2Fresources%2Fimages%2Fimproved-heap-memory.png)
  ![log-info-after-batch-processing.png](src%2Fmain%2Fresources%2Fimages%2Flog-info-after-batch-processing.png)

* Re-test the memory usage by running the original `programming-task-example-data.log` and observe its memory usage.
![post-batch-processing-result.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Fpost-batch-processing-result.png)
![post-batch-processing-heap-memory-usage.png](..%2Fsrc%2Fmain%2Fresources%2Fimages%2Fpost-batch-processing-heap-memory-usage.png)

* Result
- Overall HEAP memory usage has improved, the application can now process a 38gb file under 30 seconds (manually timed)

* Came across this https://medium.com/@AlexanderObregon/java-virtual-machine-optimization-techniques-0c7dfb0a61cd
```dockerfile
// Java code to monitor memory usage
Runtime runtime = Runtime.getRuntime();
long totalMemory = runtime.totalMemory(); // Total memory currently in use
long freeMemory = runtime.freeMemory(); // Free memory available within the total memory
long usedMemory = totalMemory - freeMemory; // Memory used by the Java application

System.out.println("Total Memory: " + totalMemory);
System.out.println("Free Memory: " + freeMemory);
System.out.println("Used Memory: " + usedMemory);
```
- append this code into `WebServerLogAnalyzerApp`, it allows you to quickly observer the memory usage by java application, free memory available, and total memory in use