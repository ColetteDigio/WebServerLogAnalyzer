package au.com.org;

import au.com.org.config.ConfigLoader;
import au.com.org.webServerAnalyzer.BatchCounter;
import au.com.org.webServerAnalyzer.LogAnalyzer;
import au.com.org.webServerAnalyzer.LogParser;
import au.com.org.webServerAnalyzer.LogReader;
import au.com.org.webServerAnalyzer.WebServerLogAnalyzer;

public class WebServerLogAnalyzerApp {
    public static void main(String[] args) {
        String logFilePath = null;
        if (args.length >= 1) {
            logFilePath = args[0];
        }

        ConfigLoader configLoader = new ConfigLoader();
        BatchCounter batchCounter = new BatchCounter();
        LogReader logReader = new LogReader(batchCounter);
        LogParser logParser = new LogParser();
        LogAnalyzer logAnalyzer = new LogAnalyzer();

        WebServerLogAnalyzer analyzer = new WebServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
        analyzer.run(logFilePath);

    }

}