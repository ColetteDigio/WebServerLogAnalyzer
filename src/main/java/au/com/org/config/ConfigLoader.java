package au.com.org.config;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigLoader {
    private static final Logger logger = Logger.getLogger(ConfigLoader.class);

    public static Properties loadProperties(String configFile) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                logger.error("Sorry, unable to find " + configFile);
                throw new FileNotFoundException("Config file " + configFile + " not found");
            }
            properties.load(input);
        }
        return properties;
    }
}