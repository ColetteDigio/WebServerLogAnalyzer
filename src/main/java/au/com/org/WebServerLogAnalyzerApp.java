package au.com.org;

import au.com.org.config.ConfigLoader;
import au.com.org.webServerAnalyzer.*;

public class WebServerLogAnalyzerApp {
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        BatchCounter batchCounter = new BatchCounter();
        LogReader logReader = new LogReader(batchCounter);
        LogParser logParser = new LogParser();
        LogAnalyzer logAnalyzer = new LogAnalyzer();

        WebServerLogAnalyzer analyzer = new WebServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
        analyzer.run();
    }
}