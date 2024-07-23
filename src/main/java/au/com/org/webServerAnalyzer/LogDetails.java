package au.com.org.webServerAnalyzer;

import java.util.List;

public record LogDetails(
        int uniqueIpAddresses,
        List<String> top3VisitedURLs,
        List<String> top3ActiveIps) {
}
