package au.com.org.webServerAnalyzer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebServerLogAnalyzerTest {

    @Mock
    private LogReader logReader;

    @InjectMocks
    private WebServerLogAnalyzer webServerLogAnalyzer;

    private List<String> logLines;

    @Before
    public void setUp() throws IOException {
        // Hardcoded log file lines
        logLines = Arrays.asList(
                "192.168.0.1 - - [10/Jul/2020:14:56:02 -0700] \"GET / HTTP/1.1\" 200 1234",
                "192.168.0.2 - - [10/Jul/2020:14:56:03 -0700] \"GET /about HTTP/1.1\" 200 1234"
        );

        // Mock log file lines
        when(logReader.readLines("test_log_path")).thenReturn(logLines);
    }

    @Test
    public void testReadLines() throws IOException {
        List<String> result = webServerLogAnalyzer.readLines("test_log_path");

        // Verify log reading
        verify(logReader).readLines("test_log_path");

        // Assert the result
        assertEquals(logLines, result);
    }

    @Test
    public void testPrintResults() {
        // Hardcoded IPs and URLs data
        List<String> top3ActiveIps = Arrays.asList("192.168.0.1 - 1 requests", "192.168.0.2 - 1 requests");
        List<String> top3VisitedUrls = Arrays.asList("/ - 1 requests", "/about - 1 requests");
        LogDetails logDetails = new LogDetails(2, top3VisitedUrls, top3ActiveIps);

        // Redirect System.out to a ByteArrayOutputStream to capture the print output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Invoke the printResults method
        WebServerLogAnalyzer.printResults(logDetails, top3ActiveIps, top3VisitedUrls);

        // Assert the output
        String expectedOutput = "\n" + "\n" +
                "Total Unique IP addresses: 2\n" +
                "\n" +
                "Top 3 Active IPs: \n" +
                "192.168.0.1 - 1 requests\n" +
                "192.168.0.2 - 1 requests\n" +
                "\n" +
                "\n" +
                "Top 3 Visited URLs: \n" +
                "/ - 1 requests\n" +
                "/about - 1 requests\n";

        assertEquals(expectedOutput, outContent.toString());

        // Reset System.out
        System.setOut(null);
    }


//    @Test
//    public void testRun() throws IOException {
//        // Mock configuration properties
//        Properties properties = new Properties();
//        properties.setProperty("log.file.path", "default_log_path");
//        when(configLoader.loadPropertiesFile("config.properties")).thenReturn(properties);
//
//        // Mock log file lines
//        List<String> logLines = Arrays.asList(
//                "192.168.0.1 - - [10/Jul/2020:14:56:02 -0700] \"GET / HTTP/1.1\" 200 1234",
//                "192.168.0.2 - - [10/Jul/2020:14:56:03 -0700] \"GET /about HTTP/1.1\" 200 1234"
//        );
//        when(logReader.readLines("default_log_path")).thenReturn(logLines);
//
//        // Mock IP and URL extraction
//        List<String> ipAddresses = Arrays.asList("192.168.0.1", "192.168.0.2");
//        List<String> urls = Arrays.asList("/", "/about");
//        when(logParser.extractIpAddresses(logLines)).thenReturn(ipAddresses);
//        when(logParser.extractUrls(logLines)).thenReturn(urls);
//
//        // Mock IP and URL counting
//        Map<String, Integer> ipCount = new HashMap<>();
//        ipCount.put("192.168.0.1", 1);
//        ipCount.put("192.168.0.2", 1);
//        Map<String, Integer> urlCount = new HashMap<>();
//        urlCount.put("/", 1);
//        urlCount.put("/about", 1);
//        when(logAnalyzer.countOccurrences(ipAddresses)).thenReturn(ipCount);
//        when(logAnalyzer.countOccurrences(urls)).thenReturn(urlCount);
//
//        // Mock top 3 IPs and URLs
//        List<String> top3ActiveIps = Arrays.asList("192.168.0.1 - 1 requests", "192.168.0.2 - 1 requests");
//        List<String> top3VisitedUrls = Arrays.asList("/ - 1 requests", "/about - 1 requests");
//        when(logAnalyzer.getTop3(ipCount, 3)).thenReturn(top3ActiveIps);
//        when(logAnalyzer.getTop3(urlCount, 3)).thenReturn(top3VisitedUrls);
//
//        // Run the analyzer
//        webServerLogAnalyzer.run(null);
//
//        // Verify log reading
//        verify(logReader).readLines("default_log_path");
//
//        // Verify IP and URL extraction
//        verify(logParser).extractIpAddresses(logLines);
//        verify(logParser).extractUrls(logLines);
//
//        // Verify IP and URL counting
//        verify(logAnalyzer).countOccurrences(ipAddresses);
//        verify(logAnalyzer).countOccurrences(urls);
//
//        // Verify top 3 IPs and URLs retrieval
//        verify(logAnalyzer).getTop3(ipCount, 3);
//        verify(logAnalyzer).getTop3(urlCount, 3);
//    }
//
//    @Test
//    public void testRunWithIOException() throws IOException {
//        // Mock configuration properties
//        Properties properties = new Properties();
//        properties.setProperty("log.file.path", "default_log_path");
//        when(configLoader.loadPropertiesFile("config.properties")).thenReturn(properties);
//
//        // Mock IOException
//        when(logReader.readLines("default_log_path")).thenThrow(new IOException("Test IOException"));
//
//        // Run the analyzer
//        webServerLogAnalyzer.run(null);
//
//        // We cannot directly verify logging in this case, but we can check that no further interactions happened
//        verifyNoInteractions(logParser);
//        verifyNoInteractions(logAnalyzer);
//    }
}
