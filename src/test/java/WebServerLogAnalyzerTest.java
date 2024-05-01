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
        assertEquals(List.of("72.44.32.11", "72.44.32.10", "168.41.191.9"), WebServerLogAnalyzer.getLogDetails(logs).top3ActiveIps());

        // assert top 3 most active Urls
        assertEquals(List.of("/to-an-error", "/how-to", "/docs/"), WebServerLogAnalyzer.getLogDetails(logs).top3VisitedURLs());
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