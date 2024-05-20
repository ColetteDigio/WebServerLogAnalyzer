package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LogReader {

    private final Logger logger = Logger.getLogger(LogReader.class);
    private BatchCounter batchCounter;

    public LogReader(BatchCounter batchCounter) {
        this.batchCounter = batchCounter;
    }

    public List<String> readLogsFromFile(String logFilePath, int batchSize) throws FileNotFoundException, LogFileEmptyException {

        List<String> batchLogs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFilePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                batchLogs.add(line);
                if (batchLogs.size() == batchSize) { // Checks if the batchLogs list has reached the specified batchSize
                    processBatch(batchLogs);
                    // If the batch size is reached, the processBatch method is called to process the current batch of logs.
                    // This method can perform any processing logic needed, such as storing the batch in a database, at the moment, it is keeping count of the number of batch in 1000s.
                    batchLogs.clear(); // Clear the batchLogs list after processing preparing for next batch of processing
                }
            }

            // Verify if the log file is empty or not
            checkLogFile(batchLogs);

        } catch (FileNotFoundException ex) {
            logger.error("Error: The log file is not found!");
            throw new FileNotFoundException("Error: File not found!");
        } catch (IOException ex) {
            logger.error("General I/O Exception ");
            System.out.println("General I/O exception:" + ex.getMessage());
        }
        logger.info("Total number of batches processed: " + batchCounter.getCount());
        return batchLogs;
    }

    // Extract this into separate method for readability
    private void checkLogFile(List<String> batchLogs) {
        if (!batchLogs.isEmpty()) {
            processBatch(batchLogs);
        }

        // Check if the log is empty
        if (batchCounter.getCount() == 0) {
            logger.error("Error: The log file is empty.");
            throw new LogFileEmptyException("Error: The log file is empty");
        }
    }

    private void processBatch(List<String> batch) {
        batchCounter.increment(); // Increment the batch counter
    }

}

