package au.com.org.mbean;

import org.apache.log4j.Logger;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MBeanRegistration {

    private static final Logger logger = Logger.getLogger(MBeanRegistration.class);

    public static void registerMBeanForPerformanceTest(List<String> logs) {
        logger.warn("Performance monitoring ENABLED");
        // Create LogAnalyzer MBean
        LogAnalyzerMcBean logAnalyzerMcBean = new LogAnalyzerMonitor(logs);

        // Register MBean
        try {
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
