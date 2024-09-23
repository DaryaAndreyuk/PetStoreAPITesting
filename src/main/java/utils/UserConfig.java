package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
