package au.com.org;

import au.com.org.webServerAnalyzer.LogAnalyzer;
import au.com.org.webServerAnalyzer.LogParser;
import au.com.org.webServerAnalyzer.LogReader;
import au.com.org.webServerAnalyzer.WebServerLogAnalyzer;

public class WebServerLogAnalyzerApp {
    public static void main(String[] args) {

        var logFilePath = args.length >= 1 ? args[0] : null;

        LogReader logReader = new LogReader();
        LogParser logParser = new LogParser();
        LogAnalyzer logAnalyzer = new LogAnalyzer();

        WebServerLogAnalyzer analyzer = new WebServerLogAnalyzer(logReader, logParser, logAnalyzer);
        analyzer.run(logFilePath);

    }
}
