package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogParser {
    private static final Logger logger = Logger.getLogger(LogParser.class);

    // Regular expression to validate IPv4 addresses
    private static final Pattern IP_PATTERN = Pattern.compile(
            "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

    // Regular expression to match log lines with optional extra information
    private static final Pattern LOG_ENTRY_PATTERN = Pattern.compile(
            "^(\\S+)\\s+-\\s+\\S+\\s+\\[.*?\\]\\s+\"\\S+\\s+(\\S+)\\s+HTTP/\\S+\"\\s+\\d+\\s+\\d+.*$");

    public List<String > extractIpAddresses(List<String> lines) {

        List<String> ipAddresses = new ArrayList<>();

            for (String line : lines) {
                String ip = extractIpFromLines(line);
                if (ip != null) {
                    ipAddresses.add(ip);
                }
            }

            return ipAddresses;
        }



    public List<String> extractUrls(List<String> lines) {
        List<String> urls = new ArrayList<>();

        for (String line : lines) {
            String url = extractUrlFromLines(line);
            if (url != null) {
                urls.add(url);
            }
        }

        return urls;
    }

    String extractIpFromLines(String line) {
        Matcher matcher = LOG_ENTRY_PATTERN.matcher(line);
        if (!matcher.find()) {
            logger.warn("Skipping log line due to unmatched format: " + line);
            return null;
        }

        String ip = matcher.group(1);
        if (!isValidIp(ip)) {
            logger.warn("Skipping log line due to invalid IP address: " + line);
            return null;
        }

        return ip;
    }

    boolean isValidIp(String ip) {
        if (ip == null) return false;

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


    private String extractUrlFromLines(String line) {
        Matcher urlMatcher = LOG_ENTRY_PATTERN.matcher(line);
        if (!urlMatcher.find()) {
            logger.warn("Skipping log line due to unmatched format: " + line); // TODO what is the best practice here to handle situation like this? what are the things to consider?
            return null;
        }

        String url = urlMatcher.group(2);
        if (!isValidUrl(url)) {
            logger.warn("Skipping log line due to invalid URL: " + line);
            return null;
        }

        return url;
    }

    boolean isValidUrl(String url) {
        if (url == null) return false; // TODO check: reason for doing this is that it will return the null check earlier
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
