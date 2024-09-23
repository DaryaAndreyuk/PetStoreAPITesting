package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(getPathToResourceFile("config.properties")));
        } catch (IOException e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static String getPathToResourceFile(String fileName) {
        String projectDir = System.getProperty("user.dir");
        Path filePath = Paths.get(projectDir, "src", "main", "resources", fileName);
        return filePath.toAbsolutePath().toString();
    }
}
