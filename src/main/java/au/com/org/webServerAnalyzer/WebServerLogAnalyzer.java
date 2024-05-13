package au.com.org.webServerAnalyzer;

import au.com.org.webServerAnalyzer.LogAnalyzer;
import au.com.org.webServerAnalyzer.LogDetails;
import au.com.org.webServerAnalyzer.LogParser;
import au.com.org.webServerAnalyzer.LogReader;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class WebServerLogAnalyzer {
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        // File path
        String logFilePath = "src/main/resources/sample-log-file.log";

        // Read the Log
        LogReader logReader = new LogReader();
        List<String> logs = logReader.readLogsFromFile(logFilePath);

        // Process log file
        LogParser logParser = new LogParser();
        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);
        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        // Analyze the logs
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        // Print the result
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
