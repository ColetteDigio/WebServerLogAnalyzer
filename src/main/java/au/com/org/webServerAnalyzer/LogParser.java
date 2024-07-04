package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//TODO Currently extracting URLS and IPS and also keep count
// TODO breakdown to just extract URLS and IPS
// TODO Let log analyzer sort out the count

public class LogParser {
    private static final Logger logger = Logger.getLogger(LogParser.class);

    // Regular expression to validate IPv4 addresses
    private static final Pattern IP_PATTERN = Pattern.compile(
            "^([0-9]{1,3}\\.){3}[0-9]{1,3}$");

    // Regular expression to match log lines with optional extra information
    private static final Pattern LOG_ENTRY_PATTERN = Pattern.compile(
            "^(\\S+)\\s+-\\s+\\S+\\s+\\[.*?\\]\\s+\"\\S+\\s+(\\S+)\\s+HTTP/\\S+\"\\s+\\d+\\s+\\d+.*$");

    public List<String> extractIpAddresses(List<String> logs) {

        List<String> ipAddresses = new ArrayList<>();

        for (String log : logs) {
            // Create a matcher to find IP addresses in the log entry using the defined pattern
            extractIpsFromLog(log, ipAddresses);
        }

        return ipAddresses;
    }

    private void extractIpsFromLog(String log, List<String> ipAddresses) {
        Matcher ipMatcher = LOG_ENTRY_PATTERN.matcher(log);
        if (ipMatcher.find()) {

            // Extract the IP address from the log entry
            String ip = ipMatcher.group(1);


            // validate the extracted IP address
            if (isValidIp(ip)) {
                ipAddresses.add(ip);
            } else {
                logger.warn("Skipping log line due to invalid IP address: " + log);
            }
        } else {
            logger.warn("Skipping log line due to unmatched format: " + log);
        }
    }

    public List<String> extractUrls(List<String> logs) throws URISyntaxException {

        List<String> urls = new ArrayList<>();

        for (String log : logs) {

            // Create a matcher to find IP addresses in the log entry using the defined pattern
            Matcher urlMatcher = LOG_ENTRY_PATTERN.matcher(log);
            if (urlMatcher.find()) {

                // Extract the URL from the log entry
                String url = urlMatcher.group(2); // extract the matched substring captured by second capturing group, which represent URLs

               // Validate the URL
                if (isValidUrl(url)) {
                    urls.add(url);
                } else {
                    logger.warn("Skipping log line due to invalid URL: " + log);
                }
            } else {
                logger.warn("Skipping log line due to unmatched format: " + log);
            }
        }
        return urls;
    }

    boolean isValidIp(String ip) { // TODO check for Null?
        // Create a matcher to match the IP address against the IP_PATTERN regex
        Matcher matcher = IP_PATTERN.matcher(ip);

        // If the IP address doesn't match the regex pattern, return false
        if (!matcher.matches()) {
            return false;
        }
        // Split the IP address into its individual parts using the period (.) as a delimiter
        String[] parts = ip.split("\\.");

        // Convert the part to an integer
        for (String part : parts) {
            int value = Integer.parseInt(part);

            // Check if the integer value is outside the valid range for an IP address part (0-255)
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }

    boolean isValidUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        } catch (NullPointerException e) {
            logger.info("this log line contains no URL: " + e.getMessage()); // TODO improve log message
            return false;
        }
    }

}
