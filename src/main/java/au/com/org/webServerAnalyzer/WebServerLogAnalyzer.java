package au.com.org.webServerAnalyzer;

import au.com.org.mbean.LogAnalyzerMcBean;
import au.com.org.mbean.LogAnalyzerMonitor;
import org.apache.log4j.Logger;

import javax.management.*;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WebServerLogAnalyzer {

    private static final Logger logger = Logger.getLogger(WebServerLogAnalyzer.class);


    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        logger.info("Starting the WebServerLogAnalyzer application...");

        // File path
        String logFilePath = "src/main/resources/programming-task-example-data.log";

        // Read the Log
        List<String> logs = getLogsFromFile(logFilePath);

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

    private static List<String> getLogsFromFile(String logFilePath) throws FileNotFoundException {

        BatchCounter batchCounter = new BatchCounter();

        LogReader logReader = new LogReader(batchCounter);
        List<String> logs = logReader.readLogsFromFile(logFilePath, 1000);
        return logs;
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

            CountDownLatch latch = new CountDownLatch(1);
            latch.await();
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                 MBeanRegistrationException | NotCompliantMBeanException | InterruptedException e) {
            logger.error("Something went wrong while registering MBean for performance test!", e);

        }
    }
}
