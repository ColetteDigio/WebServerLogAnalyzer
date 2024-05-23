package au.com.org.webServerAnalyzer;

import au.com.org.config.ConfigLoader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebServerLogAnalyzerTest {

    @Mock
    private ConfigLoader configLoader;

    @Mock
    private LogReader logReader;

    @Mock
    private LogParser logParser;

    @Mock
    private LogAnalyzer logAnalyzer;

    @InjectMocks
    private WebServerLogAnalyzer analyzer;

    @Captor
    private ArgumentCaptor<List<String>> logsCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLogsFromFile() throws IOException {
        List<String> logs = Arrays.asList("log1", "log2", "log3");
        when(logReader.readLogsFromFile(anyString(), anyInt())).thenReturn(logs);

        List<String> resultLogs = analyzer.getLogsFromFile("dummyPath");
        assertNotNull(resultLogs);
        assertEquals(3, resultLogs.size());
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetLogsFromFile_FileNotFound() throws IOException {
        when(logReader.readLogsFromFile(anyString(), anyInt())).thenThrow(new FileNotFoundException());

        analyzer.getLogsFromFile("dummyPath");
    }


    @Test
    public void testProcessLogs() throws URISyntaxException, IOException {
        List<String> logs = Arrays.asList(
                "127.0.0.1 - - [24/Feb/2021:13:00:00 +0000] \"GET /index.html HTTP/1.1\" 200 1043",
                "192.168.0.1 - - [24/Feb/2021:13:00:01 +0000] \"POST /form HTTP/1.1\" 200 512");

        Map<String, Integer> ipCount = Map.of("127.0.0.1", 1, "192.168.0.1", 1);
        Map<String, Integer> urlCount = Map.of("/index.html", 1, "/form", 1);

        when(logParser.parseIpAddresses(logs)).thenReturn(ipCount);
        when(logParser.parseUrls(logs)).thenReturn(urlCount);
        when(logAnalyzer.getTop3(ipCount, 3)).thenReturn(Arrays.asList("127.0.0.1 - 1 requests", "192.168.0.1 - 1 requests"));
        when(logAnalyzer.getTop3(urlCount, 3)).thenReturn(Arrays.asList("/index.html - 1 requests", "/form - 1 requests"));

        analyzer.processLogs(logs);

        verify(logParser).parseIpAddresses(logs);
        verify(logParser).parseUrls(logs);
        verify(logAnalyzer).getTop3(urlCount, 3);
    }

    @Test
    public void testPrintLogResults() throws URISyntaxException {
        List<String> logs = Arrays.asList(
                "127.0.0.1 - - [24/Feb/2021:13:00:00 +0000] \"GET /index.html HTTP/1.1\" 200 1043",
                "192.168.0.1 - - [24/Feb/2021:13:00:01 +0000] \"POST /form HTTP/1.1\" 200 512"
        );

        Map<String, Integer> ipCount = Map.of(
                "127.0.0.1", 1,
                "192.168.0.1", 1);
        Map<String, Integer> urlCount = Map.of(
                "/index.html", 1,
                "/form", 1);

        when(logParser.parseIpAddresses(logs)).thenReturn(ipCount);
        when(logParser.parseUrls(logs)).thenReturn(urlCount);
        when(logAnalyzer.getTop3(ipCount, 3)).thenReturn(Arrays.asList("127.0.0.1 - 1 requests", "192.168.0.1 - 1 requests"));
        when(logAnalyzer.getTop3(urlCount, 3)).thenReturn(Arrays.asList("/index.html - 1 requests", "/form - 1 requests"));

        analyzer.processLogs(logs);

        ArgumentCaptor<List> logsCaptor = ArgumentCaptor.forClass(List.class);
        verify(logAnalyzer, times(2)).getTop3(anyMap(), eq(3));
        // verifying the interactions with mocks here instead.
    }
}
