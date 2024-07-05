package au.com.org.webServerAnalyzer;

import au.com.org.config.ConfigLoader;
import org.apache.log4j.Logger;

import java.io.IOException;
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

    public void run(String logFilePath) { //TODO Test run Method?
        try {
            String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.info("Starting the WebServerLogAnalyzer application at " + startTime + "...");

            List<String> lines = readFile(logFilePath);

            Result result = getResult(lines);

            // count occurrences of IPs and URLs
            Map<String, Integer> ipCount = logAnalyzer.countOccurrences(result.ipAddresses());
            Map<String, Integer> urlCount = logAnalyzer.countOccurrences(result.urls());

            // get top 3 Ips and Urls
            List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
            List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

            // print result
            LogDetails logDetails = new LogDetails(ipCount.size(), top3VisitedUrls, top3ActiveIps);
            printResults(logDetails, top3ActiveIps, top3VisitedUrls);

            String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.info("WebServerLogAnalyzer application completed successfully at " + endTime);
        } catch (IOException e) {
            logger.error("Application crashed: " + e.getMessage(), e);
        }
    }

    private Result getResult(List<String> lines) {
        // extract IPs and URLs
        List<String> ipAddresses = logParser.extractIpAddresses(lines);
        List<String> urls = logParser.extractUrls(lines);
        Result result = new Result(ipAddresses, urls);
        return result;
    }

    private record Result(List<String> ipAddresses, List<String> urls) {
    }

    private List<String> readFile(String logFilePath) throws IOException {
        // Load configuration properties and process logs
        Properties properties = configLoader.loadPropertiesFile("config.properties");

        // Use the log file path from the command line if provided, otherwise use the path from properties
        if (logFilePath == null) {
            logFilePath = properties.getProperty("log.file.path");
        }

        // read lines from log file
        List<String> lines = readLines(logFilePath);
        return lines;
    }

    static void printResults(LogDetails logDetails, List<String> top3ActiveIps, List<String> top3VisitedUrls) {
        System.out.println("\n");
        System.out.println("Total Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");

        System.out.println("Top 3 Active IPs: ");
        top3ActiveIps.forEach(System.out::println);

        System.out.println("\n");
        System.out.println("Top 3 Visited URLs: ");
        top3VisitedUrls.forEach(System.out::println);
    }

    List<String> readLines(String logFilePath) throws IOException {
        List<String> logs = logReader.readLines(logFilePath);
        return logs;
    }
}