package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class LogReaderTest {


    private LogReader logReader;

    @Before
    public void setUp() {
        logReader = new LogReader();
    }


    private String filePath = "src/test/resources/test_log_file.log";
    private String nonExistentLogFilePath = "I_dont_exists_log.txt";
    private String emptyFilePath = "src/test/resources/empty.log";

    @Test
    public void testReadLogsFromFile() throws IOException {
        List<String> logs = logReader.readLines(filePath);
        assertEquals(5, logs.size());
    }

    @Test
    public void testLogFileNotFound() {
        assertThrows(IOException.class, () -> {
            logReader.readLines(nonExistentLogFilePath);
        });
    }

    @Test
    public void testLogFileIsEmpty() {
        assertThrows(LogFileEmptyException.class, () -> {
            logReader.readLines(emptyFilePath);
        });
    }


}
