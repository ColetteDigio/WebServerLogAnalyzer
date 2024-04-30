import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WebServerLogAnalyzerTest {

    @Test
    public void testLogReading() {
        List<String> logs = new ArrayList<>();

        String logFilePath = "src/main/resources/programming-task-example-data.log";

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // test if the number of lines read matches the expected number of lines from the file
        int expectedNumberOfLines = 23;
        assertEquals(expectedNumberOfLines, logs.size());
    }

    @Test
    public void testGetLogDetails() {

        // Mock Logs
        List<String> logs = new ArrayList<>();
        logs.add("177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");
        logs.add("177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");
        logs.add("79.125.00.21 - - [10/Jul/2018:20:03:40 +0200] \"GET /newsletter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)\"");
        logs.add("79.125.00.21 - - [10/Jul/2018:20:03:40 +0200] \"GET /newsletter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)\"");
        logs.add("79.125.00.21 - - [10/Jul/2018:20:03:40 +0200] \"GET /newsletter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)\"");
        logs.add("72.44.32.10 - - [09/Jul/2018:15:48:20 +0200] \"GET /download/counter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");
        logs.add("72.44.32.10 - - [09/Jul/2018:15:48:20 +0200] \"GET /download/counter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");
        logs.add("72.44.32.10 - - [09/Jul/2018:15:48:20 +0200] \"GET /download/counter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");
        logs.add("72.44.32.12 - - [09/Jul/2018:15:48:20 +0200] \"GET /download/counter/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"");

        LogDetails logDetails = WebServerLogAnalyzer.getLogDetails(logs);

        // assert the UniqueIpCounts
        assertEquals(4, logDetails.uniqueIpAddresses());

       // assert top 3 most active IPs
        assertEquals(List.of("79.125.00.21","72.44.32.10", "177.71.128.21"), WebServerLogAnalyzer.getLogDetails(logs).top3ActiveIps());

        // assert top 3 most active Urls
        assertEquals(List.of("/download/counter/", "/newsletter/", "/intranet-analytics/"), WebServerLogAnalyzer.getLogDetails(logs).top3VisitedURLs());
        // TODO this test is tested with mock logs, and it has consistent URL structure. re-visit the data
    }






}