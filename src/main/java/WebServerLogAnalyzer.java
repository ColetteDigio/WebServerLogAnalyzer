import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebServerLogAnalyzer {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        String logFilePath = "src/main/resources/programming-task-example-data.log";
        List<String> logs = getLogsFromFile(logFilePath);

        LogDetails logDetails = getLogDetails(logs);
        System.out.println("\n");
        System.out.println("Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");
        System.out.println("Top 3 Active IPs: " + logDetails.top3ActiveIps() + "\n");
        System.out.println("Top 3 Visited URLs: " + logDetails.top3VisitedURLs() + "\n");
    }

    public static List<String> getLogsFromFile(String logFilePath) throws FileNotFoundException {

        List<String> logs = new ArrayList<>();

        // Read from the log file
        try {
            BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Error: File not found!");
        } catch (IOException ex) {
            System.out.println("General I/O exception:" + ex.getMessage());
        }
        return logs;
    }

    public static LogDetails getLogDetails(List<String> logs) throws URISyntaxException {
        Map<String, Integer> ipCount = new HashMap<>();
        Map<String, Integer> urlCount = new HashMap<>();

        for (String log : logs) {
            String ip = log.split(" - ")[0];
            ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1);

            // assumption 1. process the whole URL as a whole
            String url = log.split("\"")[1].split(" ")[1];
            urlCount.put(url, urlCount.getOrDefault(url, 0) + 1);

            // assumption 2. cleaning up URL structure by removing http and domain
//            String fullURL = log.substring(log.indexOf("GET ") + 4, log.indexOf(" HTTP"));
//            String path = fullURL.startsWith("http") ? extractPath(fullURL) : fullURL;
//            urlCount.put(path, urlCount.getOrDefault(path, 0) + 1);
        }

        // uncomment the code below and the method called inspectTotalCountOfIPAndURL to inspect breakdown count of IPs and URLs
//        inspectTotalCountOfIPAndURL(ipCount, urlCount);

        // Get Top 3 after sorting out the data
        List<String> top3ActiveIps = getTop3(ipCount, 3);
        List<String> top3VisitedURLs = getTop3(urlCount, 3);

        return new LogDetails(ipCount.size(),top3VisitedURLs, top3ActiveIps);
    }

    // a method that will look at each key value pair of <Ips and count> && <Urls and count> and sorting it out in reverse order
    private static List<String> getTop3(Map<String, Integer> map, int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    // this method is only used if path were extracted out of full URL
    public static String extractPath(String fullURL) throws URISyntaxException {
        try {
            URI uri = new URI(fullURL);
            return uri.getPath();
        } catch (URISyntaxException e) {
            throw new URISyntaxException("Invalid URI format", e.getMessage());
        }
    }

    // to print out and inspect total count of IPs and URLs
//    private static void inspectTotalCountOfIPAndURL(Map<String, Integer> ipCount, Map<String, Integer> urlCount) {
//        // print out IPs to observe the number of times IP is visited
//        System.out.println("\n***   SUMMARIES OF IPs Count   ***");
//        ipCount.forEach((key, value) ->  System.out.println(key + " = " + value));
//
//        // print out URLs to observe the structure
//        System.out.println("\n***   SUMMARIES OF URLs Count   ***");
//        urlCount.forEach((key, value) ->  System.out.println(key + " = " + value));
//    }
}
