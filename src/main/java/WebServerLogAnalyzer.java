import java.io.BufferedReader;
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
    public static void main(String[] args) {

        List<String> logs = new ArrayList<>();

        // Read from the log file
        String logFilePath = "src/main/resources/programming-task-example-data.log";

        try {
            BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException ex) {
            // TODO handle the error gracefully
            ex.printStackTrace();
        }

        LogDetails logDetails = getLogDetails(logs);
        System.out.println("Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");
        System.out.println("Top 3 Active IPs: " + logDetails.top3ActiveIps() + "\n");
        System.out.println("Top 3 Visited URLs: " + logDetails.top3VisitedURLs() + "\n");
    }

    public static LogDetails getLogDetails(List<String> logs) {
        Map<String, Integer> ipCount = new HashMap<>();
        Map<String, Integer> urlCount = new HashMap<>();

        for (String log : logs) {
            String ip = log.split(" - ")[0];
            ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1);

            structurePathData(log, urlCount);
        }

//        // print out IPs to observe the number of times IP is visited
//        System.out.println("\n***   SUMMARIES OF IPs Count   ***");
//        ipCount.forEach((key, value) ->  System.out.println(key + " = " + value));
//
//        // print out URLs to observe the structure
//        System.out.println("\n***   SUMMARIES OF URLs Count   ***");
//        urlCount.forEach((key, value) ->  System.out.println(key + " = " + value));

        // Get Top 3 after sorting out the data
        List<String> top3ActiveIps = getTop3(ipCount, 3);
        List<String> top3VisitedURLs = getTop3(urlCount, 3);

        return new LogDetails(ipCount.size(),top3VisitedURLs, top3ActiveIps);
    }

    // clean up the URLs structure, ignore domain and looking at the url path only
    private static void structurePathData(String log, Map<String, Integer> urlCount) {
        String fullURL = log.substring(log.indexOf("GET ") + 4, log.indexOf(" HTTP"));

        String path = "";

        if (fullURL.startsWith("http")) {
            try {
                URI uri = new URI(fullURL);
                path = uri.getPath();
            } catch (URISyntaxException e) {
                // TODO handle the error gracefully
                e.printStackTrace();
            }
        } else {
            path = fullURL;
        }

        if (urlCount.containsKey(path)) {
            urlCount.put(path, urlCount.get(path) + 1); // increment the instance
        } else if (!path.isEmpty()) {
            // ignores all the spaces at the bottom of the log
            urlCount.put(path, 1); // create an instance
        }
    }

    // a method that will look at each pair of <Ips and number> && <Urls and number> and sorting it out in order
    private static List<String> getTop3(Map<String, Integer> map, int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
