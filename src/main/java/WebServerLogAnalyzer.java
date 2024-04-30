import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WebServerLogAnalyzer {
    public static void main(String[] args) {

        List<String> logs = new ArrayList<>();

        // Read from the log file
        String logFilePath = "/Users/coletteliu/Documents/assessment/WebServerLogAnalyzer/src/main/resources/programming-task-example-data.log";
        // TODO - make the filepath "prettier"

        try {
            BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
                System.out.println(line);
            }
        } catch (IOException ex) {
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

            String url = log.split("\"")[1].split(" ")[1];
            urlCount.put(url, urlCount.getOrDefault(url, 0) + 1);
            // TODO the URLs structure are not consistent, check and observe

            System.out.println("URL Count: "+ urlCount);
        }

        List<String> top3ActiveIps = getTop3(ipCount, 3);
        List<String> top3VisitedURLs = getTop3(urlCount, 3);

        return new LogDetails(ipCount.size(),top3VisitedURLs, top3ActiveIps);
    }

    // a method that will look at each pair of <Ips and number> && <Urls and number>
    private static List<String> getTop3(Map<String, Integer> map, int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
