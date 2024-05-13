package au.com.org.webServerAnalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogReader {
    public List<String> readLogsFromFile(String logFilePath) throws FileNotFoundException {
        List<String> logs = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Error: File not found!");
        } catch (IOException ex) {
            System.out.println("General I/O exception:" + ex.getMessage());
        }
        return logs;
    }
}
