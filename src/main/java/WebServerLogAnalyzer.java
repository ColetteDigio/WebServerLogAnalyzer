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

        // 1. read the log
        String logFilePath = "src/main/resources/programming-task-example-data.log";
        List<String> logs = getLogsFromFile(logFilePath);

        // 2. retrieve the data required
        LogDetails logDetails = getLogDetails(logs);
        System.out.println("\n");
        System.out.println("Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");
        System.out.println("Top 3 Active IPs: " + logDetails.top3ActiveIps() + "\n");
        System.out.println("Top 3 Visited URLs: " + logDetails.top3VisitedURLs() + "\n");
    }

    // Method to read logs from logs file
    public static List<String> getLogsFromFile(String logFilePath) throws FileNotFoundException {

        List<String> logs = new ArrayList<>();

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

    // retrieve data
    public static LogDetails getLogDetails(List<String> logs) throws URISyntaxException {
        Map<String, Integer> ipCount = new HashMap<>(); // instantiate <key, value> pair of IP address and count
        Map<String, Integer> urlCount = new HashMap<>(); // instantiate <key, value> pair of URL and count

        for (String log : logs) { // loop through the logs
            String ip = log.split(" ")[0]; // Splitting the log string by " " delimiter and extracting the IP address at index [0]
            ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1); // Incrementing count of occurrences of the IP address in a map called ipCount.

//            // assumption 1. process the whole URL as a whole
            String url = log.split("\"")[1].split(" ")[1];
//            // splits the log string using double quotes (") as the delimiter and selects the second part (index 1) of the resulting array
//            // It further splits the selected part using space as the delimiter and selects the second part (index 1) of the resulting array.
            urlCount.put(url, urlCount.getOrDefault(url, 0) + 1); // Incrementing count of occurrences of the URL in a map called urlCount.

//             assumption 2. cleaning up URL structure by removing http and domain
//            String fullURL = log.substring(log.indexOf("GET ") + 4, log.indexOf(" HTTP")); // extracts the substring between these two indices. Adding 4 to the index of "GET " skips past the "GET " string to extract the URL.
//            String path = fullURL.startsWith("http") ? extractPath(fullURL) : fullURL; // sets the path variable, if it contains http, extract path, if not, take the whole URL
//            urlCount.put(path, urlCount.getOrDefault(path, 0) + 1); // Incrementing count of occurences of the URL called urlCount
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
        return map.entrySet().stream() // convert map entries to stream of map.entry object, each entry is <key, value> pair
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // compares values of the entries in descending order then in reverse order
                .limit(n)// limit the steam to the first n entries
                .map(Map.Entry::getKey) // only interested at the keys
                .collect(Collectors.toList()); // returns keys into list
    }


    // This method is only used if path were extracted out of full URL
    public static String extractPath(String fullURL) throws URISyntaxException {
        try {
            URI uri = new URI(fullURL); // validate the URI format
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
