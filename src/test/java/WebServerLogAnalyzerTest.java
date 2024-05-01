import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class WebServerLogAnalyzerTest {

    private static final String MOST_ACTIVE_IP = "72.44.32.11";
    private static final String SECOND_ACTIVE_IP = "72.44.32.10";
    private static final String THIRD_ACTIVE_IP = "168.41.191.9";
    private static final String MOST_ACTIVE_URL = "/to-an-error";
    private static final String SECOND_ACTIVE_URL = "/how-to";
    private static final String THIRD_ACTIVE_URL = "/docs/";

    @Test
    public void testGetLogDetails() throws URISyntaxException {

        List<String> logs = new ArrayList<>();

        String logFilePath = "src/test/java/resources/test_log_file.log";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        LogDetails logDetails = WebServerLogAnalyzer.getLogDetails(logs);

        // assert the UniqueIpCounts
        assertEquals(4, logDetails.uniqueIpAddresses());

       // assert top 3 most active IPs
        assertEquals(List.of(MOST_ACTIVE_IP, SECOND_ACTIVE_IP, THIRD_ACTIVE_IP), WebServerLogAnalyzer.getLogDetails(logs).top3ActiveIps());

        // assert top 3 most active Urls
        assertEquals(List.of(MOST_ACTIVE_URL, SECOND_ACTIVE_URL, THIRD_ACTIVE_URL), WebServerLogAnalyzer.getLogDetails(logs).top3VisitedURLs());
    }

    @Test
    public void testLogFileNotFound() {
        String nonExistentFilePath = "i_dont_exist_file.log";
        assertThrows(FileNotFoundException.class, () -> {
            WebServerLogAnalyzer.getLogsFromFile(nonExistentFilePath);
        });
    }

    @Test
    public void testExtractedPath_With_ValidURI() throws URISyntaxException {
        String fullURL = "http://example.com/path/to/resource";
        String extractedPath = WebServerLogAnalyzer.extractPath(fullURL);

        assertEquals("/path/to/resource", extractedPath);
    }


    @Test
    public void testExtractedPath_With_InvalidURI() {
        String fullURL = "http:\\invalid-uri";
        assertThrows(URISyntaxException.class, () -> {
            WebServerLogAnalyzer.extractPath(fullURL);
        });
    }
}