package au.com.org.webServerAnalyzer;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LogAnalyzerTest {
    LogAnalyzer logAnalyzer = new LogAnalyzer();
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
        List<String> top3VistedUrls = logAnalyzer.getTop3(urlCount, 3);

        assertEquals(List.of(
                "72.44.32.11 - 4 requests",
                "72.44.32.10 - 3 requests",
                "168.41.191.9 - 2 requests"), top3ActiveIps);

        assertEquals(List.of(
                "/to-an-error - 4 requests",
                "/how-to - 3 requests",
                "/docs/ - 2 requests"), top3VistedUrls);
    }
}

