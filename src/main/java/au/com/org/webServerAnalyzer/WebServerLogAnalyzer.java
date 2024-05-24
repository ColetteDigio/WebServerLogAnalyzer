package au.com.org.webServerAnalyzer;

import au.com.org.config.ConfigLoader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WebServerLogAnalyzer {

    private static final Logger logger = Logger.getLogger(WebServerLogAnalyzer.class);
    private final ConfigLoader configLoader;
    private final LogReader logReader;
    private final LogParser logParser;
    private final LogAnalyzer logAnalyzer;

    // create an instance of WebServerLogAnalyzer
    public WebServerLogAnalyzer(ConfigLoader configLoader, LogReader logReader, LogParser logParser, LogAnalyzer logAnalyzer) {
        this.configLoader = configLoader;
        this.logReader = logReader;
        this.logParser = logParser;
        this.logAnalyzer = logAnalyzer;
    }

    public void run() {
        try {
            logger.info("Starting the WebServerLogAnalyzer application...");
            // Load configuration properties and process logs
            loadConfigurationAndProcessLogs();
            logger.info("WebServerLogAnalyzer application completed successfully.");
        } catch (IOException | URISyntaxException e) {
            logger.error("Application crashed: " + e.getMessage(), e);
            // TODO - future implementation - implement logic to clean up and restart/ retry the application.
        }
    }

    // load configuration method, then process logs
    void loadConfigurationAndProcessLogs() throws IOException, URISyntaxException {
        String configFile = System.getProperty("app.config", "local-config.properties");
        Properties properties = configLoader.loadProperties(configFile);

        // File path
        String logFilePath = properties.getProperty("log.file.path", "src/main/resources/programming-task-example-data.log");

        // Read the Log
        List<String> logs = getLogsFromFile(logFilePath);

        // Process log file
        processLogs(logs);
    }

    // Read logs
    public List<String> getLogsFromFile(String logFilePath) throws FileNotFoundException {
        return logReader.readLogsFromFile(logFilePath, 1000);
    }

    // process logs
    public void processLogs(List<String> logs) throws URISyntaxException {
        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);
        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        // Analyze the logs
        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        // Print the result
        printLogResults(ipCount, top3VisitedUrls, top3ActiveIps);
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
