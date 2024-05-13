package au.com.org.webServerAnalyzer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class WebServerLogAnalyzerTest {

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outputContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOutput);
    }

    @Test
    public void testMain() throws FileNotFoundException, URISyntaxException {
        WebServerLogAnalyzer.main(null);

        //Verify
        String expectedOutput = "\n" +
                "Total Unique IP addresses: 12\n" +
                "\n" +
                "Top 3 Active IPs: \n" +
                "50.112.00.12 - 5 requests\n" +
                "168.41.191.40 - 4 requests\n" +
                "50.112.00.11 - 3 requests\n" +
                "\n" + "\n" +
                "Top 3 Visited URLs: \n" +
                "/asset.css - 6 requests\n" +
                "/docs/manage-websites/ - 2 requests\n" +
                "/blog/2018/08/survey-your-opinion-matters/ - 1 requests";

        assertEquals(expectedOutput.trim(), outputContent.toString().trim());
    }

}

// TODO read from test file instead