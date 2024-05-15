package au.com.org.webServerAnalyzer;

import au.com.org.webServerAnalyzer.LogParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class LogParserTest {

    LogParser logParser = new LogParser();
    private static final String MOST_ACTIVE_IP = "72.44.32.11";
    private static final String SECOND_ACTIVE_IP = "72.44.32.10";
    private static final String THIRD_ACTIVE_IP = "168.41.191.9";
    private static final String MOST_ACTIVE_URL = "/to-an-error";
    private static final String SECOND_ACTIVE_URL = "/how-to";
    private static final String THIRD_ACTIVE_URL = "/docs/";

    List<String> logs = List.of(
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
    public void testParseIpAddresses() throws FileNotFoundException {

        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);

        assertEquals(4, ipCount.size());

        assertEquals(4, ipCount.size());
        assertEquals(Integer.valueOf(4), ipCount.get(MOST_ACTIVE_IP));
        assertEquals(Integer.valueOf(3), ipCount.get(SECOND_ACTIVE_IP));
        assertEquals(Integer.valueOf(2), ipCount.get(THIRD_ACTIVE_IP));
    }

    @Test
    public void testParseUrls() {

        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        assertEquals(4, urlCount.size());
        assertEquals(Integer.valueOf(4), urlCount.get(MOST_ACTIVE_URL));
        assertEquals(Integer.valueOf(3), urlCount.get(SECOND_ACTIVE_URL));
        assertEquals(Integer.valueOf(2), urlCount.get(THIRD_ACTIVE_URL));
    }

    @Test
    public void testExtractedPath_With_ValidURI() throws URISyntaxException {

        String fullURL = "http://example.com/path/to/resource";
        String extractedPath = logParser.extractPath(fullURL);

        assertEquals("/path/to/resource", extractedPath);
    }

    @Test
    public void testExtractedPath_With_InvalidURI() {
        String fullURL = "http:\\invalid-uri";
        assertThrows(URISyntaxException.class, () -> {
            logParser.extractPath(fullURL);
        });
    }


}