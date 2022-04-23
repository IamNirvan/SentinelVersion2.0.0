/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Shalin Kulawardane
 */
public class PropertiesManager {
    private static final String PROPERTIES_PATH = "src\\Assets\\Properties\\Properties.properties";
    private final String USER_NAME;
    private final String PASSWORD;
    private final String SERVER_NAME;
    private final String PORT;
    
    public PropertiesManager(String username, String password,  String serverName, String port) {
        this.USER_NAME = username;
        this.PASSWORD = password;
        this.SERVER_NAME = serverName;
        this.PORT = port;

    }
    
    public boolean configureSystemProperties() {
        /*  This method is responsible for setting up and/or configuring the properties file 
            for the system. It will obtain the required parameters from the configuration
            form. 
        */
        
        createPropertiesFile();        
        try {
            final Properties PROPERTIES = new Properties();
            OutputStream outputStream = new FileOutputStream(PROPERTIES_PATH);
            PROPERTIES.setProperty("userName", this.USER_NAME);
            PROPERTIES.setProperty("serverPassword", this.PASSWORD);
            PROPERTIES.setProperty("serverName", this.SERVER_NAME);
            PROPERTIES.setProperty("databaseName", "sentinel");
            PROPERTIES.setProperty("portNumber", this.PORT);
            PROPERTIES.store(outputStream, null);
            return true;            

        } catch (IOException ex) { ToolBox.triggerException(ex); }
    
        return false;        

    }
    
    public void createPropertiesFile() {
        final File FILE = new File(PROPERTIES_PATH);        
        try  { FILE.createNewFile(); }
        catch(IOException ex) { ToolBox.triggerException(ex); }        

    }

    public static String getPath() { return PROPERTIES_PATH; };    

}
