package au.com.org.mbean;

import au.com.org.webServerAnalyzer.LogAnalyzer;
import au.com.org.webServerAnalyzer.LogParser;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class LogAnalyzerMonitor implements LogAnalyzerMcBean{

    private final LogAnalyzer logAnalyzer;

    private final List<String> logs;

    public LogAnalyzerMonitor(List<String> logs) {
        this.logs = logs;
        this.logAnalyzer = new LogAnalyzer();
    }

    @Override
    public int getUniqueIpCount() {
        return logAnalyzer.getUniqueCount(logs);
    }

    @Override
    public List<String> getTop3VisitedUrls() throws URISyntaxException {
        Map<String, Integer> urlCount = new LogParser().parseUrls(logs);
        return logAnalyzer.getTop3(urlCount, 3);
    }

    @Override
    public List<String> getTop3ActiveIps() {
        Map<String, Integer> ipCount = new LogParser().parseIpAddresses(logs);
        return logAnalyzer.getTop3(ipCount, 3);
    }
}
