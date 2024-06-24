package au.com.org.mbean;

import java.net.URISyntaxException;
import java.util.List;

public interface LogAnalyzerMcBean {

    int getUniqueIpCount();

    List<String> getTop3ActiveIps();

    List<String> getTop3VisitedUrls() throws URISyntaxException;
}
