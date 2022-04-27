package com.sentinelv200.core.middlemen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface MiddleMan {
    void start();

    default boolean getEngineActivationStatus(String engineName){
        String path = System.getProperty("user.dir") + 
        "\\src\\com\\sentinelv200\\Assets\\Properties\\engineProperties.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            return Boolean.parseBoolean(properties.getProperty(engineName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    void addEngine();

    void removeEngine();
}
