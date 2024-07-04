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

    public List<String>readLines(String logFilePath) throws LogFileEmptyException, IOException {

        List<String> fullLogs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFilePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                fullLogs.add(line);
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
        return fullLogs;
    }

    private boolean isEmptyFile(List<String> logs) {
        return logs.isEmpty();
    }
}






