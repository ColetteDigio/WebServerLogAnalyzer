package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Logger logger = Logger.getLogger(LogParser.class);

    // Regular expression to validate IPv4 addresses
    private static final Pattern IP_PATTERN = Pattern.compile(
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$");

    // Regular expression to match log lines with optional extra information
    private static final Pattern LOG_ENTRY_PATTERN = Pattern.compile(
            "^(\\S+)\\s+-\\s+\\S+\\s+\\[.*?\\]\\s+\"\\S+\\s+(\\S+)\\s+HTTP/\\S+\"\\s+\\d+\\s+\\d+.*$");

    public Map<String, Integer> parseIpAddresses(List<String> logs) {

        Map<String, Integer> ipCount = new HashMap<>();

        for (String log : logs) {
            Matcher matcher = LOG_ENTRY_PATTERN.matcher(log);
            if (matcher.find()) {
                String ip = matcher.group(1);

                // validate IP
                if (isValidIp(ip)) {
                    ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1);
                } else {
                    logger.warn("Skipping log line due to invalid IP address: " + log);
                }
            } else {
                logger.warn("Skipping log line due to unmatched format: " + log);
            }
        }

        return ipCount;
    }

    // assumption 1. process the whole URL as a whole
    public Map<String, Integer> parseUrls(List<String> logs) throws URISyntaxException {

        Map<String, Integer> urlCount = new HashMap<>();

        for (String log : logs) {
            Matcher matcher = LOG_ENTRY_PATTERN.matcher(log);
            if (matcher.find()) {
                String url = matcher.group(2); // extract the matched substring captured by second capturing group, which represent URLs
                if (url != null && isValidUrl(url)) {
                    urlCount.put(url, urlCount.getOrDefault(url, 0) + 1);
                } else {
                    logger.warn("Skipping log line due to invalid URL: " + log);
                }
            } else {
                logger.warn("Skipping log line due to unmatched format: " + log);
            }
        }
        return urlCount;
    }

    // assumption 2. clean up the URL structure
//    public Map<String, Integer> parseUrls(List<String> logs) throws URISyntaxException {
//
//        Map<String, Integer> urlCount = new HashMap<>();
//
//        Pattern pattern = Pattern.compile("\"(\\w+)\\s+([^\\s]+)\\s+HTTP/\\d.\\d\""); // creates regular expression pattern using "Pattern.compile"
//
//        for (String log : logs) {
//            Matcher matcher = pattern.matcher(log);
//            if (matcher.find()) {
//                String url = matcher.group(2); // extract the matched substring captured by second capturing group, which represent URLs
//
//                // clean up URL Path
//                String path = url.startsWith("http") ? extractPath(url) : url;
//
//                // Validate the URLs and if it's not null
//                if (url != null && isValidUrl(path)) {
//                    urlCount.put(path, urlCount.getOrDefault(path, 0) + 1);
//                } else {
//                    logger.warn("Skipping log line due to invalid URL: " + log);
//                }
//            } else {
//                logger.warn("Skipping log line due to unmatched format: " + log);
//            }
//        }
//        return urlCount;
//    }


    private boolean isValidIp(String ip) {
        Matcher matcher = IP_PATTERN.matcher(ip);
        if (!matcher.matches()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        for (String part : parts) {
            int value = Integer.parseInt(part);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidUrl(String url) {
        try {
            new URI(url); // If this doesn't throw an exception, the URL is valid
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }


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
