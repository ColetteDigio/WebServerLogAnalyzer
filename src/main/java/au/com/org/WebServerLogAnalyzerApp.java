package au.com.org;

import au.com.org.config.ConfigLoader;
import au.com.org.webServerAnalyzer.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class WebServerLogAnalyzerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebServerLogAnalyzerApp.class, args);
    }

    public void run(String... args) {
        ConfigLoader configLoader = configLoader();
        BatchCounter batchCounter = batchCounter();
        LogReader logReader = logReader(batchCounter);
        LogParser logParser = logParser();
        LogAnalyzer logAnalyzer = logAnalyzer();

        WebServerLogAnalyzer analyzer = webServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
        analyzer.run();
    }

    @Bean
    public ConfigLoader configLoader() {
        return new ConfigLoader();
    }

    @Bean
    public BatchCounter batchCounter() {
        return new BatchCounter();
    }

    @Bean
    public LogReader logReader(BatchCounter batchCounter) {
        return new LogReader(batchCounter);
    }

    @Bean
    public LogParser logParser() {
        return new LogParser();
    }

    @Bean
    public LogAnalyzer logAnalyzer() {
        return new LogAnalyzer();
    }

    @Bean
    public WebServerLogAnalyzer webServerLogAnalyzer(ConfigLoader configLoader, LogReader logReader, LogParser logParser, LogAnalyzer logAnalyzer) {
        return new WebServerLogAnalyzer(configLoader, logReader, logParser, logAnalyzer);
    }

}
