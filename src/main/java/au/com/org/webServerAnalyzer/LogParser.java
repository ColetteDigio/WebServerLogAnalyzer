package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    private static final Logger logger = Logger.getLogger(LogParser.class);

    public Map<String, Integer> parseIpAddresses(List<String> logs) {

        Map<String, Integer> ipCount = new HashMap<>();

        for(String log: logs) {
            String ip = log.split(" ")[0]; // Splitting the log string by " " delimiter and extracting the IP address at index [0]
            ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1); // Incrementing count of occurrences of the IP in a map called ipCount.
        }

        return ipCount;
    }

//    // assumption 1. process the whole URL as a whole
    public Map<String, Integer> parseUrls(List<String> logs) {

        Map<String, Integer> urlCount = new HashMap<>();

        for(String log: logs) {
            String url = log.split("\"")[1].split(" ")[1];
            urlCount.put(url, urlCount.getOrDefault(url, 0) + 1);
        }
        return urlCount;
    }

//    public Map<String, Integer> parseUrls(List<String> logs) throws URISyntaxException {
//
//        Map<String, Integer> urlCount = new HashMap<>();
//
//        Pattern pattern = Pattern.compile("\"(\\w+)\\s+([^\\s]+)\\s+HTTP/\\d.\\d\""); // creates regular expression pattern using "Pattern.compile"
//
//        for(String log: logs) {
//            Matcher matcher = pattern.matcher(log); // create matcher object for the current log string
//            if(matcher.find()) {
//                String url = matcher.group(2); // extract the matched substring captured by second capturing group, which represent URLs
//
//                // clean up the url by extracting the path
//                String urlPath = extractPath(url);
//                urlCount.put(url, urlCount.getOrDefault(urlPath, 0) + 1); // Incrementing count of occurrences of the URL in a map called urlCount.
//            }
//        }
//        return urlCount;
//    }

    public String extractPath(String urlPath) throws URISyntaxException {
        try {
            URI uri = new URI(urlPath); // validate the URI format
            return uri.getPath();
        } catch (URISyntaxException e) {
            logger.error("Error: Invalid URI format");
            throw new URISyntaxException("Invalid URI format", e.getMessage());
        }
    }

}
