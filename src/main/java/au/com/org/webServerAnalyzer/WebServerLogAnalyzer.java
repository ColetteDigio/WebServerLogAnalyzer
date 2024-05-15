package au.com.org.webServerAnalyzer;

import au.com.org.mbean.LogAnalyzerMcBean;
import au.com.org.mbean.LogAnalyzerMonitor;
import org.apache.log4j.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class WebServerLogAnalyzer {

    private static final Logger logger = Logger.getLogger(WebServerLogAnalyzer.class);

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        logger.info("Starting the WebServerLogAnalyzer application...");

        // File path
        String logFilePath = "src/test/resources/empty.log";

        // Read the Log
        LogReader logReader = new LogReader();
        List<String> logs = logReader.readLogsFromFile(logFilePath);

        // Process log file
        LogParser logParser = new LogParser();
        Map<String, Integer> ipCount = logParser.parseIpAddresses(logs);
        Map<String, Integer> urlCount = logParser.parseUrls(logs);

        // Analyze the logs
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> top3ActiveIps = logAnalyzer.getTop3(ipCount, 3);
        List<String> top3VisitedUrls = logAnalyzer.getTop3(urlCount, 3);

        // Print the result
        LogDetails logDetails = new LogDetails(ipCount.size(), top3VisitedUrls, top3ActiveIps);
        System.out.println("\n");
        System.out.println("Total Unique IP addresses: " + logDetails.uniqueIpAddresses() + "\n");

        System.out.println("Top 3 Active IPs: ");
        top3ActiveIps.forEach(System.out::println);

        System.out.println("\n");
        System.out.println("Top 3 Visited URLs: ");
        top3VisitedUrls.forEach(System.out::println);


        registerMBeanForPerformanceTest(logs);


        logger.info("WebServerLogAnalyzer application completed successfully.");
    }

    private static void registerMBeanForPerformanceTest(List<String> logs) {
        // Create LogAnalyzer MBean
        LogAnalyzerMcBean logAnalyzerMcBean = new LogAnalyzerMonitor(logs);

        // register MBean
        try{
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName objName = new ObjectName("au.com.org.webServerAnalyzer:type=LogAnalyzer");
            StandardMBean mBean = new StandardMBean(logAnalyzerMcBean, LogAnalyzerMcBean.class);
            mbs.registerMBean(mBean, objName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                 MBeanRegistrationException | NotCompliantMBeanException e) {
            logger.error("something is wrong here!");
            System.out.println();
        }
    }
}
