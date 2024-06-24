package au.com.org.webServerAnalyzer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LogAnalyzerTest {
    private LogAnalyzer logAnalyzer = new LogAnalyzer();

    @Test
    public void testGetUniqueCount() {
        List<String> logs = new ArrayList<>();
        logs.add("50.112.00.28 - - [11/Jul/2018:15:49:46 +0200] \"GET /faq HTTP/1.1\"");
        logs.add("50.112.00.28 - - [11/Jul/2018:15:49:46 +0200] \"GET /faq HTTP/1.1\"");
        logs.add("112.00.11 - admin [11/Jul/2018:17:31:56 +0200] \"GET /asset.js HTTP/1.1\"");
        logs.add("72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\"");
        logs.add("72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\"");

        int uniqueCount = logAnalyzer.getUniqueCount(logs);

        assertEquals(3, uniqueCount);
    }
    @Test
    public void testGetTop3() {

        Map<String, Integer> ipCount =
                Map.of(
                        "72.44.32.11", 4,
                        "72.44.32.10", 3,
                        "168.41.191.9", 2);

        Map<String, Integer> urlCount =
                Map.of(
                        "/to-an-error", 4,
                        "/how-to", 3,
                        "/docs/", 2);

        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        assertEquals(List.of(
                "72.44.32.11 - 4 requests",
                "72.44.32.10 - 3 requests",
                "168.41.191.9 - 2 requests"), top3ActiveIps);

        assertEquals(List.of(
                "/to-an-error - 4 requests",
                "/how-to - 3 requests",
                "/docs/ - 2 requests"), top3VisitedUrls);
    }
}

