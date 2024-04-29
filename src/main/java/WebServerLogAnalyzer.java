import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebServerLogAnalyzer {

    public static void main(String[] args) {

        List<String> logs = new ArrayList<>();

        // Read from the log file
        String logFilePath = "/Users/coletteliu/Documents/assessment/WebServerLogAnalyzer/src/main/resources/programming-task-example-data.log";

        try {
            BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            String line;
            while((line = br.readLine()) != null) {
                logs.add(line);
                System.out.println(line);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }


    }
}
