/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sentinel.Utility.ToolBox;
import sentinel.Security.KeyHandler;
import sentinel.Security.Security;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import sentinel.Database.ConnectionHandler;

/**
 *
 * @author Shalin Kulawardane
 */
public class Account {
    private char[] masterPassword;
    private final String USERNAME;
    private byte[] salt;
    private final String KEY_STORE_PATH;
    
    public Account(String username, char[] password) {
        this.USERNAME = username;
        this.salt = Security.getSalt(USERNAME, password);
        this.masterPassword = Security.hashPassword(ToolBox.convertToByte(password), this.salt).toCharArray();
        this.KEY_STORE_PATH = "src\\Assets\\Security\\" + username + "_Keystore.keystore";

    }
        
    public void reset() {
        Security.overrideData(this.masterPassword);
        Security.overrideData(this.salt);
        
    }
    
    public char[] getPassword() { return this.masterPassword; }
    
    public String getKeyStorePath() { return this.KEY_STORE_PATH; }
    
    public static String getDate(String username) { 
        try (final PreparedStatement STATEMENT = ConnectionHandler.getConnection().prepareStatement(
                "SELECT creationDate FROM sentinel.useraccount WHERE `Username` = ?" )) {
            STATEMENT.setString(1, username);
            final ResultSet RESULT_SET = STATEMENT.executeQuery();
            while(RESULT_SET.next()) { return RESULT_SET.getString(1); }
            
        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return null;

    }

    public static String getLastLogin(String username) { 
        try (final PreparedStatement STATEMENT = ConnectionHandler.getConnection().prepareStatement(
                "SELECT LastLogin FROM sentinel.useraccount WHERE `Username` = ?" )) {
            STATEMENT.setString(1, username);
            final ResultSet RESULT_SET = STATEMENT.executeQuery();
            while(RESULT_SET.next()) { return RESULT_SET.getString(1); }
            
        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return null;

    }

    public String getUsername() { return this.USERNAME; }
        
    public boolean login() {        
        try(PreparedStatement STATEMENT = ConnectionHandler.getConnection().
                    prepareStatement("SELECT * FROM useraccount WHERE username = ? AND masterPassword = ?")) {
            STATEMENT.setString(1, USERNAME);
            STATEMENT.setString(2, new String(this.masterPassword));
            return STATEMENT.executeQuery().next();

        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return false;        

    }
    
    public void setLastActiveEntry(String lastActiveTime) {
        try (final PreparedStatement STATEMENT = ConnectionHandler.getConnection().prepareStatement(
                "UPDATE useraccount SET LastLogin = ? WHERE username = ?" )) {
            STATEMENT.setString(1, lastActiveTime);
            STATEMENT.setString(2, this.USERNAME);
            STATEMENT.executeUpdate();

        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
    }
    
    public boolean register() {
        /*  This method will register a user provided .
            It will return true if registration was successful, and false if
            not.
        */
        
        try (final PreparedStatement STATEMENT = ConnectionHandler.getConnection().prepareStatement(
                "INSERT INTO useraccount(Username, MasterPassword, CreationDate) VALUES(?, ?, ?)" )) {
            STATEMENT.setString(1, USERNAME);
            STATEMENT.setString(2, new String(this.masterPassword));
            STATEMENT.setString(3, DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));

            if(STATEMENT.executeUpdate() == 1) {
                // Create a new key store:
                KeyHandler.generateNewKeyStore(this.KEY_STORE_PATH, this.masterPassword);                
                // Add a new secret key entry:
                KeyHandler.loadToKeyStore(KeyHandler.generateSecretKey(), this.masterPassword, this.KEY_STORE_PATH);
                
                return true;
    
            }        

        } catch (SQLException ex) { ToolBox.triggerException(ex); }

        return false;
 
    }
     
    public boolean removeAccount() {
        try(final PreparedStatement STATEMENT = ConnectionHandler.getConnection().
                prepareStatement("DELETE FROM `useraccount` WHERE `useraccount`.`Username` = ?")) {
            STATEMENT.setString(1, USERNAME);
            
            if(STATEMENT.executeUpdate() == 1) {
                KeyHandler.destroyKeyStore(this.KEY_STORE_PATH);
                return true;                

            }

        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return false;        

    }
        
    public boolean changePassword(char[] newPassword){
        char[] oldPassword = this.masterPassword;
        this.salt = Security.getSalt(USERNAME, newPassword);
        this.masterPassword = Security.hashPassword(ToolBox.convertToByte(newPassword), this.salt).toCharArray();                
        
        try(final PreparedStatement STATEMENT = ConnectionHandler.getConnection().
                prepareStatement("UPDATE `useraccount` SET `masterPassword` = ? WHERE `useraccount`.`Username` = ?")){            
            STATEMENT.setString(1, new String(this.masterPassword));
            STATEMENT.setString(2, this.USERNAME);
            
            if(STATEMENT.executeUpdate() == 1) {  return KeyHandler.changeKeystorePassword(this.KEY_STORE_PATH, 
                    oldPassword, this.masterPassword); }
            
        } catch (SQLException ex) { ToolBox.triggerException(ex); } 

        return false;        

    }
    
}
