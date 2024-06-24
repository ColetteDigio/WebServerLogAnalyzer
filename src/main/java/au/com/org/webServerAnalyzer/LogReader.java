package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LogReader {

    private Logger logger = Logger.getLogger(LogReader.class);
    private BatchCounter batchCounter;

    public LogReader(BatchCounter batchCounter) {
        this.batchCounter = batchCounter;
    }

    public LogReader(BatchCounter batchCounter, Logger logger) {
            this.batchCounter = batchCounter;
            this.logger = logger;
    }

    public List<String>readLines(String logFilePath,int batchSize) throws LogFileEmptyException, IOException {

        List<String> fullLogs = new ArrayList<>();
        List<String> batchLogs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFilePath), StandardCharsets.UTF_8))) {
            String line;
            int linesRead = 0;
            while ((line = br.readLine()) != null) {
                fullLogs.add(line);
                batchLogs.add(line);
                linesRead++;

                if (batchLogs.size() == batchSize) { // Checks if the batchLogs list has reached the specified batchSize of 1000
                    processBatch(batchLogs);
                    batchLogs.clear();
                }

                // provides information and keep user informed of the progress
                if (linesRead % 1000 == 0) {
                    logger.info("Processed " + linesRead + " lines so far...");
                }
            }

            // Process any remaining logs in the batchLogs list
            if (!batchLogs.isEmpty()) {
                processBatch(batchLogs);
            }

            // Check if the log file is empty
            if (isEmptyFile(fullLogs)) {
                logger.info("No logs available for analysis.");
                throw new LogFileEmptyException("No logs available for analysis.");
            }

        } catch (IOException ex) {
            logger.error("General I/O Exception: " + ex.getMessage());
            throw new IOException("General I/O Exception: " + ex.getMessage());
        }
        logger.info("Total number of batches processed: " + batchCounter.getCount());
        return fullLogs;
    }

    private boolean isEmptyFile(List<String> logs) {
        return logs.isEmpty();
    }

    private void processBatch(List<String> batch) {

        batchCounter.increment();
    }
}






