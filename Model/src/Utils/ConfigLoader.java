package src.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadProperties(String filename) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        }
        return properties;
    }
}
