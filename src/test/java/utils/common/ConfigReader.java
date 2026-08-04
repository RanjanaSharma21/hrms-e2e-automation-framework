package utils.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigReader {

    private static final Properties property = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
            property.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        return property.getProperty(key);
    }
}

