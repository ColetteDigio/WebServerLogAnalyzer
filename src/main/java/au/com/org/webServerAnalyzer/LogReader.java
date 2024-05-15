package au.com.org.webServerAnalyzer;

import au.com.org.exception.LogFileEmptyException;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LogReader {

    private final Logger logger = Logger.getLogger(LogReader.class);
    public List<String> readLogsFromFile(String logFilePath) throws FileNotFoundException {
        List<String> logs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFilePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
            br.close(); // close the BufferedReader

            // Check if the log is empty
            if(logs.isEmpty()) {
                logger.error("Error: The log file is empty.");
                throw new LogFileEmptyException("Error: The log file is empty");
            }

        } catch (FileNotFoundException ex) {
            logger.error("Error: The log file is not found!");
            throw new FileNotFoundException("Error: File not found!");
        } catch (IOException ex) {
            logger.error("General I/O Exception ");
            System.out.println("General I/O exception:" + ex.getMessage());
        }
        return logs;
    }
}
