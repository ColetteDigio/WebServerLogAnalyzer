package au.com.org.webServerAnalyzer;

import au.com.org.config.ConfigLoader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WebServerLogAnalyzer {

    private static final Logger logger = Logger.getLogger(WebServerLogAnalyzer.class);
    private final ConfigLoader configLoader;
    private final LogReader logReader;
    private final LogParser logParser;
    private final LogAnalyzer logAnalyzer;

    public WebServerLogAnalyzer(ConfigLoader configLoader, LogReader logReader, LogParser logParser, LogAnalyzer logAnalyzer) {
        this.configLoader = configLoader;
        this.logReader = logReader;
        this.logParser = logParser;
        this.logAnalyzer = logAnalyzer;
    }

    public void run(String logFilePath) {
        try {
            String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.info("Starting the WebServerLogAnalyzer application at " + startTime + "...");

            // Load configuration properties and process logs
            Properties properties = configLoader.loadPropertiesFile("config.properties");

            // Use the log file path from the command line if provided, otherwise use the path from properties
            if (logFilePath == null) {
                logFilePath = properties.getProperty("log.file.path");
            }

            // Read and process logs
            readAndProcessLogs(logFilePath);

            String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.info("WebServerLogAnalyzer application completed successfully at " + endTime);
        } catch (IOException | URISyntaxException e) {
            logger.error("Application crashed: " + e.getMessage(), e);
        }
    }

    List<String> readAndProcessLogs(String logFilePath) throws IOException, URISyntaxException {

        // Read the Log
        List<String> logs = logReader.readLines(logFilePath, 1000);

        // Process log file
        analyseLogs(logs);
        return logs;
    }

    public void analyseLogs(List<String> logs) throws URISyntaxException {
        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);
        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        // Analyze the logs
        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        // Print the result
        printLogResults(ipCount, top3VisitedUrls, top3ActiveIps);

        // Commented out to separate the performance test from actual task itself
//        MBeanRegistration.registerMBeanForPerformanceTest(logs);

    }

    public void printLogResults(Map<String, Integer> ipCount, List<String> top3VisitedUrls, List<String> top3ActiveIps) {
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