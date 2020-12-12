package com.mainul35;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfig {

    private final Properties properties;

    public PropertyConfig() throws IOException {
        properties = new Properties();
        String propFileName = "static/application.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }
}
