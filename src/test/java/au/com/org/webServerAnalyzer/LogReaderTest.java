package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class LogReaderTest {

    private LogReader logReader;
    private BatchCounter batchCounter;
    private String nonEmptyLogFilePath;
    private String emptyLogFilePath;
    private String nonExistentLogFilePath = "src/test/java/resources/non_existent_log.txt";
    private int batchSize = 1;

    @Before
    public void setUp() {
        batchCounter = new BatchCounter();
        logReader = new LogReader(batchCounter);

        nonEmptyLogFilePath = "src/test/java/resources/test_log_file.log";
        emptyLogFilePath = "src/test/java/resources/empty.log";
    }



    @Test
    public void testReadLogsFromFile_nonEmptyLogFile() throws FileNotFoundException {

        List<String> logs = logReader.readLogsFromFile(nonEmptyLogFilePath, batchSize);

        assertEquals(10, batchCounter.getCount());
        assertEquals(1, batchSize);
        assertNotNull(String.valueOf(true), logs.size() > 0);
    }

    @Test
    public void testReadLogsFromFile_emptyFile()  {
        String filePath = "src/test/java/resources/empty.log";
        assertThrows(LogFileEmptyException.class, () -> {
            logReader.readLogsFromFile(filePath, batchSize);
        });
    }

    @Test
    public void testLogFileNotFound() {
        String nonExistentFilePath = "i_dont_exist_file.log";
        assertThrows(FileNotFoundException.class, () -> {
            logReader.readLogsFromFile(nonExistentFilePath, batchSize);
        });
    }




}