package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LogReaderTest {

    private LogReader logReader;
    private BatchCounter batchCounter;
    private Logger mockLogger;
    private String nonEmptyLogFilePath;
    private String emptyLogFilePath;
    private String nonExistentLogFilePath = "non_existent_file.log";
    private int batchSize = 1;

    private static final String TEST_LOG_FILE = "test_logs.log";

    @Before
    public void setUp() {
        batchCounter = mock(BatchCounter.class);
        mockLogger = mock(Logger.class);
        logReader = new LogReader(batchCounter, mockLogger);

        nonEmptyLogFilePath = "src/test/resources/test_log_file.log";
        emptyLogFilePath = "src/test/resources/empty.log";
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the test_logs file if it exists
        Path path = Paths.get(TEST_LOG_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }


    @Test
    public void testReadLines_with_nonEmptyLogFile() throws IOException {

        List<String> logs = logReader.readLines(nonEmptyLogFilePath, batchSize);

        assertEquals(1, batchSize);
        assertNotNull(String.valueOf(true), logs.size() > 0);
    }

    @Test
    public void testReadLines_with_emptyFile() {
        LogFileEmptyException exception = assertThrows(LogFileEmptyException.class, () ->
                logReader.readLines(emptyLogFilePath, 2));

        assertEquals("No logs available for analysis.", exception.getMessage());
        verify(mockLogger).info("No logs available for analysis.");
    }


    @Test
    public void testReadLine_with_IOException() {
        IOException exception = assertThrows(IOException.class, () ->
                logReader.readLines(nonExistentLogFilePath, 2));

        assertEquals("General I/O Exception: "+ nonExistentLogFilePath + " (No such file or directory)", exception.getMessage());
    }

    @Test
    public void testProcessBatchMethod() throws IOException, LogFileEmptyException {
        String logFilePath = "src/test/resources/test_log_file.log";
        int batchSize = 2;
        String logs = "log1\nlog2\nlog3\nlog4\nlog5\n";

        // Create a test log file
        createTestLogFile(logFilePath, logs);

        logReader.readLines(logFilePath, batchSize);

        // Verify that processBatch (increment) is called 3 times (2 full batches + 1 remaining batch)
        verify(batchCounter, times(3)).increment();
    }

    @Test
    public void testReadLines_processesBatches_correctly() throws IOException, LogFileEmptyException {
        String logFilePath = "src/test/resources/test_log_file.log";
        int batchSize = 2;
        String logs = "log1\nlog2\nlog3\nlog4\n";

        // Create a test log file
        createTestLogFile(logFilePath, logs);

        List<String> result = logReader.readLines(logFilePath, batchSize);

        // Verify that processBatch is called twice and contains the expected logs
        verify(batchCounter, times(2)).increment();

        // Verify the total logs read
        assertEquals(4, result.size());
    }

    @Test
    public void testReadLines_ProgressLogMessage() throws IOException, LogFileEmptyException {
        String logFilePath = "src/test/resources/test_log_file.log";
        int batchSize = 1000;
        StringBuilder logs = new StringBuilder();

        // Create a large number of logs to test the progress logging
        for (int i = 1; i <= 3000; i++) {
            logs.append("log").append(i).append("\n");
        }

        // Create a test log file
        createTestLogFile(logFilePath, logs.toString());

        logReader.readLines(logFilePath, batchSize);

        // Verify the progress logging
        verify(mockLogger, times(3)).info(matches("Processed \\d+ lines so far..."));
        verify(batchCounter, times(3)).increment();
    }


    private void createTestLogFile(String logFilePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilePath), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

}