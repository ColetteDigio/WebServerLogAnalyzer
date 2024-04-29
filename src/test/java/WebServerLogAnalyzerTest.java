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



}