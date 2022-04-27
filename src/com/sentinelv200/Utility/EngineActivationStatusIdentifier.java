package com.sentinelv200.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EngineActivationStatusIdentifier {
    private static final String FILE_PATH = System.getProperty("user.dir") + 
    "\\src\\com\\sentinelv200\\Assets\\Properties\\engineProperties.PROPERTIES";
    private static final Properties PROPERTIES = new Properties();    

    public static boolean checkActivationStatus(String engineName) {
        try {
            PROPERTIES.load(new FileInputStream(FILE_PATH));
            return Boolean.parseBoolean(PROPERTIES.getProperty(engineName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
