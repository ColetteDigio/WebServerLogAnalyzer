package au.com.org.webServerAnalyzer;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class LogReaderTest {

    LogReader logReader = new LogReader();

    @Test
    public void testReadLogsFromFile() throws FileNotFoundException {

        String filePath = "src/test/java/resources/test_log_file.log";
        List<String> logs = logReader.readLogsFromFile(filePath);

        assertEquals(10, logs.size());
    }

    @Test
    public void testLogFileNotFound() {
        String nonExistentFilePath = "i_dont_exist_file.log";
        assertThrows(FileNotFoundException.class, () -> {
            logReader.readLogsFromFile(nonExistentFilePath);
        });
    }


}