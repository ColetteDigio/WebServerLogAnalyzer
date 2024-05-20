package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class WebServerLogAnalyzer {

    private static final Logger logger = Logger.getLogger(WebServerLogAnalyzer.class);

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        logger.info("Starting the WebServerLogAnalyzer application...");

        // File path
        String logFilePath = "src/main/resources/programming-task-example-data.log";

        // Read the Log
        List<String> logs = getLogsFromFile(logFilePath);

        // Process log file
        LogParser logParser = new LogParser();
        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);
        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        // Analyze the logs
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        // Print the result
        printLogResults(ipCount, top3VisitedUrls, top3ActiveIps);

        // Commented out to separate the performance test from actual task itself
//        MBeanRegistration.registerMBeanForPerformanceTest(logs);


        logger.info("WebServerLogAnalyzer application completed successfully.");
    }

    private static List<String> getLogsFromFile(String logFilePath) throws FileNotFoundException {

        BatchCounter batchCounter = new BatchCounter();

        LogReader logReader = new LogReader(batchCounter);
        List<String> logs = logReader.readLogsFromFile(logFilePath, 1000);
        return logs;
    }

    private static void printLogResults(Map<String, Integer> ipCount, List<String> top3VisitedUrls, List<String> top3ActiveIps) {
        LogDetails logDetails = new LogDetails(ipCount.size(), top3VisitedUrls, top3ActiveIps);
        System.out.println("\n");
        System.out.println("Total Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");

        System.out.println("Top 3 Active IPs: ");
        top3ActiveIps.forEach(System.out::println);

        System.out.println("\n");
        System.out.println("Top 3 Visited URLs: ");
        top3VisitedUrls.forEach(System.out::println);
    }

}
