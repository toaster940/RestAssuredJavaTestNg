package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileReader {

    private Properties properties;

    public String getProperty(String key) {

        BufferedReader bufferedReader;
        java.io.FileReader fileReader;
        String propertyFilePath = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";

        try {
            fileReader = new java.io.FileReader(propertyFilePath);
            bufferedReader = new BufferedReader(fileReader);
            properties = new Properties();

            try {
                properties.load(bufferedReader);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties not found at " + propertyFilePath);
        }
        return properties.getProperty(key);
    }
}
