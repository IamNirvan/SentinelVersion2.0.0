/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Utility;

import sentinel.Database.ConnectionHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import sentinel.Engines.Affire;

/**
 *
 * @author Shalin Kulawardane
 */
public class Diagnostics  {
    public final static int NUM_OF_CHECKS = 2;
    public static String filePath = PropertiesManager.getPath();
    
    
    public boolean locatePropertiesFile() { return new File(filePath).exists(); }
             
    public boolean locateKeystore(String path) { return new File(path).exists(); }
    
    public boolean checkConnection() { return ConnectionHandler.getConnection() != null; }
    
    public boolean isPropertiesFileEmpty() {
        try(Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            while(scanner.hasNextLine()) { return scanner.hasNext(); }

        } catch (FileNotFoundException ex) { ToolBox.triggerException(ex); }

        return false;

    }
    
    public boolean locatePasswordTestingResources() {
        return (Affire.getPath("rockyou").exists()) && 
                (Affire.getPath("wordlist").exists());
               
    }

}