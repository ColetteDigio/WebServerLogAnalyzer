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

    private final String nonExistentLogFilePath = "I_dont_exists_log.txt";
    private final String emptyFilePath = "src/test/resources/empty.log";

    @Test
    public void test_ReadLines() throws IOException {
        String filePath = "src/test/resources/test_log_file.log";
        List<String> logs = logReader.readLines(filePath);
        assertEquals(5, logs.size());
    }

    @Test
    public void test_LogFile_NotFound() {
        assertThrows(IOException.class, () -> {
            logReader.readLines(nonExistentLogFilePath);
        });
    }

    @Test
    public void test_LogFile_IsEmpty() {
        assertThrows(LogFileEmptyException.class, () -> {
            logReader.readLines(emptyFilePath);
        });
    }
}
