package au.com.org.webServerAnalyzer;

import au.com.org.config.ConfigLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebServerLogAnalyzerTest {

    private ConfigLoader configLoader;
    private LogReader logReader;
    private LogParser logParser;
    private LogAnalyzer logAnalyzer;
    private WebServerLogAnalyzer webServerLogAnalyzer;

    @Before
    public void setUp() {
        configLoader = mock(ConfigLoader.class);
        logReader = mock(LogReader.class);
        logParser = mock(LogParser.class);
        logAnalyzer = mock(LogAnalyzer.class);

        webServerLogAnalyzer = new WebServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
    }

    @Test
    public void testReadLines() throws IOException {
        // Arrange
        List<String> logs = Arrays.asList(
                "192.168.1.1 - - [some date] \"GET /index.html HTTP/1.1\" 200 123",
                "192.168.1.2 - - [some date] \"GET /about.html HTTP/1.1\" 200 123"
        );
        when(logReader.readLines("logfile.log")).thenReturn(logs);

        // Act
        List<String> resultLogs = webServerLogAnalyzer.readLines("logfile.log");

        // Assert
        assertEquals(logs, resultLogs);
        verify(logReader, times(1)).readLines("logfile.log");
    }

//    @Test
//    public void testAnalyseLogs() throws URISyntaxException {
//        // Arrange
//        List<String> logs = Arrays.asList(
//                "192.168.1.1 - - [some date] \"GET /index.html HTTP/1.1\" 200 123",
//                "192.168.1.2 - - [some date] \"GET /about.html HTTP/1.1\" 200 123"
//        );
//
//        Map<String, Integer> ipCount = Map.of("192.168.1.1", 2, "192.168.1.2", 1);
//        Map<String, Integer> urlCount = Map.of("/index.html", 1, "/about.html", 1);
//        when(logParser.parseIpAddresses(logs)).thenReturn(ipCount);
//        when(logParser.parseUrls(logs)).thenReturn(urlCount);
//
//        List<String> top3ActiveIps = Arrays.asList("192.168.1.1 - 2 requests", "192.168.1.2 - 1 request");
//        List<String> top3VisitedUrls = Arrays.asList("/index.html - 1 request", "/about.html - 1 request");
//        when(logAnalyzer.getTop3(ipCount, 3)).thenReturn(top3ActiveIps);
//        when(logAnalyzer.getTop3(urlCount, 3)).thenReturn(top3VisitedUrls);
//
//        // Act
//        webServerLogAnalyzer.analyseLogs(logs);
//
//        // Assert
//        verify(logParser, times(1)).parseIpAddresses(logs);
//        verify(logParser, times(1)).parseUrls(logs);
//        verify(logAnalyzer, times(1)).getTop3(ipCount, 3);
//        verify(logAnalyzer, times(1)).getTop3(urlCount, 3);
//    }
}
