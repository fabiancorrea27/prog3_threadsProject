package co.edu.uptc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigValue {
    public static String getProperty(String key){
        Properties properties = new Properties();
        String value = "";
        try {
            properties.load(new FileInputStream("resources\\Config.properties"));
            value = properties.getProperty(key);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
}
