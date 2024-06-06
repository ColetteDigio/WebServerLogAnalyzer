package au.com.org;

import au.com.org.config.ConfigLoader;
import au.com.org.webServerAnalyzer.*;

public class WebServerLogAnalyzerApp {
    public static void main(String[] args) {
        String logFilePath = null;
        if (args.length >= 1) {
            logFilePath = args[0];
        }

        ConfigLoader configLoader = new ConfigLoader();
        BatchCounter batchCounter = new BatchCounter();
        LogReader logReader = new LogReader(batchCounter);
        LogParser logParser = new LogParser();
        LogAnalyzer logAnalyzer = new LogAnalyzer();

        WebServerLogAnalyzer analyzer = new WebServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
        analyzer.run(logFilePath);

//        // Java code to monitor memory usage
//        Runtime runtime = Runtime.getRuntime();
//        long totalMemory = runtime.totalMemory(); // Total memory currently in use
//        long freeMemory = runtime.freeMemory(); // Free memory available within the total memory
//        long usedMemory = totalMemory - freeMemory; // Memory used by the Java application
//
//        System.out.println("Total Memory: " + totalMemory);
//        System.out.println("Free Memory: " + freeMemory);
//        System.out.println("Used Memory: " + usedMemory);
    }

}