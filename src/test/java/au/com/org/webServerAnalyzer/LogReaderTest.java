package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import au.com.org.webServerAnalyzer.LogReader;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class LogReaderTest {

    LogReader logReader = new LogReader();

    @Test
    public void testReadLogsFromFile() throws FileNotFoundException {

        String filePath = "src/test/java/resources/test_log_file.log";
        List<String> logs = logReader.readLogsFromFile(filePath);

        assertEquals(10, logs.size());
        assertNotNull(String.valueOf(true), logs.size() > 0);
    }

    @Test
    public void testReadLogsFromFile_emptyFile()  {
        String filePath = "src/test/java/resources/empty.log";
        assertThrows(LogFileEmptyException.class, () -> {
            logReader.readLogsFromFile(filePath);
        });
    }

    @Test
    public void testLogFileNotFound() {
        String nonExistentFilePath = "i_dont_exist_file.log";
        assertThrows(FileNotFoundException.class, () -> {
            logReader.readLogsFromFile(nonExistentFilePath);
        });
    }




}