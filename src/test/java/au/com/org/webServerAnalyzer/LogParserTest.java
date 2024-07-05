package au.com.org.webServerAnalyzer;

import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@RunWith(MockitoJUnitRunner.class)
public class LogParserTest {

    @InjectMocks
    private LogParser logParser;

    @Mock
    private Logger logger;

    @Captor
    private ArgumentCaptor<String> logMessageCaptor;

    @Before
    public void setUp() {
        logParser = new LogParser();
    }


    List<String> lines = List.of(
            "72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\" 500 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\"",
            "72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\" 500 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\"",
            "72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\" 500 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\"",
            "72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"GET /to-an-error HTTP/1.1\" 500 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\"",
            "72.44.32.10 - - [09/Jul/2018:15:48:07 +0200] \"GET /how-to HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\" junk extra",
            "72.44.32.10 - - [09/Jul/2018:15:48:07 +0200] \"GET /how-to HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\" junk extra",
            "72.44.32.10 - - [09/Jul/2018:15:48:07 +0200] \"GET /how-to HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\" junk extra",
            "168.41.191.9 - - [09/Jul/2018:22:56:45 +0200] \"GET /docs/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/20100101 Firefox/6.0 456 789",
            "168.41.191.9 - - [09/Jul/2018:22:56:45 +0200] \"GET /docs/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/20100101 Firefox/6.0 456 789",
            "168.41.191.43 - - [11/Jul/2018:17:43:40 +0200] \"GET /moved-permanently HTTP/1.1\" 301 3574 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_7) AppleWebKit/534.24 (KHTML, like Gecko) RockMelt/0.9.58.494 Chrome/11.0.696.71 Safari/534.24");

    @Test
    public void testParseIpAddresses() {

        List<String> ipAddresses = logParser.extractIpAddresses(lines);

        assertEquals(10, ipAddresses.size());
        assertTrue(ipAddresses.contains("72.44.32.11"));
        assertTrue(ipAddresses.contains("72.44.32.10"));
        assertTrue(ipAddresses.contains("168.41.191.9"));
        assertTrue(ipAddresses.contains("168.41.191.43"));
    }

    @Test
    public void testExtractIpAddressesWithInvalidAndValidIps() {
        List<String> logs = Arrays.asList(
                "50.112.00.11 - admin [11/Jul/2018:17:31:56 +0200] \"GET /asset.js HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6\"",
                "invalid-ip - admin [11/Jul/2018:17:31:56 +0200] \"GET /asset.js HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6\"",
                "168.41.191.9 - - [09/Jul/2018:22:56:45 +0200] \"GET /docs/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/20100101 Firefox/6.0\""
        );

        List<String> ipAddresses = logParser.extractIpAddresses(logs);

        assertEquals(2, ipAddresses.size());
        assertTrue(ipAddresses.contains("50.112.00.11"));
        assertTrue(ipAddresses.contains("168.41.191.9"));
    }

//    @Test //TODO how to mock Logger?
//    public void testExtractIpFromLinesWithInvalidIp() {
//        Logger mockLogger = logger;
//
//        String logLine = "999.999.999.999 - - [10/Jul/2020:14:56:02 -0700] \"GET / HTTP/1.1\" 200 1234";
////        String result = logParser.extractIpFromLines(logLine, mockLogger);
//
//
//        assertNull(result);
//        verify(logger).warn(logMessageCaptor.capture());
//        assertEquals("Skipping log line due to invalid IP address: " + logLine, logMessageCaptor.getValue());
//    }


    @Test
    public void testExtractUrls() {
        List<String> urls = logParser.extractUrls(lines);

        assertEquals(10, urls.size());
        assertTrue(urls.contains("/to-an-error"));
        assertTrue(urls.contains("/how-to"));
        assertTrue(urls.contains("/docs/"));
        assertTrue(urls.contains("/moved-permanently"));
    }

    @Test
    public void testExtractUrlsWithVariedHttpMethods() {
        List<String> logs = List.of(
                "72.44.32.11 - - [11/Jul/2018:17:42:07 +0200] \"POST /to-an-error HTTP/1.1\" 500 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\"",
                "72.44.32.10 - - [09/Jul/2018:15:48:07 +0200] \"PUT /how-to HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0\" junk extra",
                "168.41.191.9 - - [09/Jul/2018:22:56:45 +0200] \"DELETE /docs/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/20100101 Firefox/6.0\"",
                "168.41.191.43 - - [11/Jul/2018:17:43:40 +0200] \"HEAD /moved-permanently HTTP/1.1\" 301 3574 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_7) AppleWebKit/534.24 (KHTML, like Gecko) RockMelt/0.9.58.494 Chrome/11.0.696.71 Safari/534.24\""
        );

        List<String> urls = logParser.extractUrls(logs);

        assertEquals(4, urls.size());
        assertTrue(urls.contains("/to-an-error"));
        assertTrue(urls.contains("/how-to"));
        assertTrue(urls.contains("/docs/"));
        assertTrue(urls.contains("/moved-permanently"));
    }

    @Test
    public void testValidIp() {
        assertTrue(logParser.isValidIp("192.168.0.1"));
        assertTrue(logParser.isValidIp("0.0.0.0"));
        assertTrue(logParser.isValidIp("255.255.255.255"));
    }

    @Test
    public void testInvalidIp() {
        assertFalse(logParser.isValidIp("256.256.256.256")); // each part exceeds 255
        assertFalse(logParser.isValidIp("192.168.0"));       // incomplete IP
        assertFalse(logParser.isValidIp("192.168.0.1.1"));   // too many parts
        assertFalse(logParser.isValidIp("192.168.0.-1"));    // negative part
        assertFalse(logParser.isValidIp("abc.def.ghi.jkl")); // non-numeric
        assertFalse(logParser.isValidIp("123.456.789.0"));   // parts exceed 255
        assertFalse(logParser.isValidIp(" "));               // empty string
        assertFalse(logParser.isValidIp(null));              // null
    }

    @Test
    public void testValidUrl() {
        assertTrue(logParser.isValidUrl("http://example.com"));
        assertTrue(logParser.isValidUrl("https://example.com"));
        assertTrue(logParser.isValidUrl("ftp://example.com"));
        assertTrue(logParser.isValidUrl("http://example.com/path?query=param#fragment"));
    }

    @Test
    public void testInvalidUrl() {
        assertFalse(logParser.isValidUrl("http://"));            // no domain
        assertFalse(logParser.isValidUrl("://example.com"));     // no protocol
        assertFalse(logParser.isValidUrl("http:// example.com"));// space in URL
        assertFalse(logParser.isValidUrl(" "));                  // empty string
        assertFalse(logParser.isValidUrl(null));                 // null
    }

}