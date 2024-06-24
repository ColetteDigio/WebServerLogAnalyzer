package au.com.org.webServerAnalyzer.config;

import au.com.org.config.ConfigLoader;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class ConfigLoaderTest {

    @Test
    public void testLoadPropertiesFileValid() throws IOException {
        Properties properties = ConfigLoader.loadPropertiesFile("valid-config.properties");
        assertNotNull(properties);
        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }

    @Test
    public void testLoadPropertiesFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            ConfigLoader.loadPropertiesFile("non_existent_config.properties");
        });
        assertEquals("Config file non_existent_config.properties not found", exception.getMessage());
    }
}
