/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import sentinel.Utility.PropertiesManager;
import sentinel.Utility.ToolBox;
/**
 *
 * @author Shalin Kulawardane
 */
public class ConnectionHandler {

    public static Connection getConnection() {
        Properties PROPERTIES = new Properties(); 

        try(InputStream inputStream = new FileInputStream(PropertiesManager.getPath())) {
            PROPERTIES.load(inputStream);
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(PROPERTIES.getProperty("serverName"));
            dataSource.setDatabaseName(PROPERTIES.getProperty("databaseName"));
            dataSource.setUser(PROPERTIES.getProperty("userName"));
            dataSource.setPassword(String.valueOf(PROPERTIES.getProperty("serverPassword")));
            dataSource.setPortNumber(Integer.parseInt(String.valueOf(PROPERTIES.getProperty("portNumber"))));
            return dataSource.getConnection();

        } catch (FileNotFoundException ex) { ToolBox.triggerException(ex); } 
        catch (IOException | SQLException ex) { ToolBox.triggerException(ex); }
        
        return null;
 
    }
    
}
