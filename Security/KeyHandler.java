/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import sentinel.Database.ConnectionHandler;
import sentinel.Utility.ToolBox;

/**
 *
 * @author Shalin Kulawardane
 */
public class KeyHandler {

    public static void generateNewKeyStore(String path, char[] password) {
        try(OutputStream outputStream = new FileOutputStream(path)) {
            final KeyStore KEY_STORE = KeyStore.getInstance("JCEKS");
            // Load an empty keystore
            KEY_STORE.load(null, password);
            // Store the newly created keystore in the appropriate file.
            KEY_STORE.store(outputStream, password);

        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex) { ToolBox.triggerException(ex); } 
        
    }

    public static void loadToKeyStore(SecretKey secretKey, char[] password, String path) {
        try(InputStream inputStream = new FileInputStream(path)) {
            final KeyStore KEY_STORE = KeyStore.getInstance("JCEKS");
            // Load the keystore by passing the file path and keystore password:
            KEY_STORE.load(inputStream, password);
            // Store the key inside the keystore with an alias and set the password to keystorePassword:
            KEY_STORE.setKeyEntry("key", secretKey, password, null);

            try(OutputStream outputStream = new FileOutputStream(path)) { KEY_STORE.store(outputStream, password); }

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) { ToolBox.triggerException(ex); } 
        
    }

    public static SecretKey generateSecretKey() {
        try {
            // Get a key generator that uses AES:
            KeyGenerator KEY_GENERATOR = KeyGenerator.getInstance("AES");
            KEY_GENERATOR.init(256); // generate a 256-bit key
            // Return the new secret key
            return KEY_GENERATOR.generateKey();

        } catch (NoSuchAlgorithmException ex) { ToolBox.triggerException(ex); }

        return null;

    }
    
    public static IvParameterSpec getIV() {
        // Set the size of the IV to the block size used by AES (128 bits) 
        final byte[] IV = new byte[16];        
        new SecureRandom().nextBytes(IV);
        return new IvParameterSpec(IV);

    }

    public static SecretKey retrieveSecretKey(char[] password, String path) {
        try(InputStream inputStream = new FileInputStream(path)) {
            final KeyStore KEY_STORE = KeyStore.getInstance("JCEKS");
            KEY_STORE.load(inputStream, password);
            // Get the secret key by passing the alias and key password(key password = keystore password):
            return (SecretKey) KEY_STORE.getKey("key", password);            

        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException ex) 
        { ToolBox.triggerException(ex); } 

        return null;
        
    }
   
    public static boolean destroyKeyStore(String path) {
        try { 
            Files.delete(Paths.get(path)); 
            return true;
            
        } catch (IOException ex) { ToolBox.triggerException(ex); }

        return false;
        
    }
    
    public static ResultSet getKeyStorePaths() {
        try { return ConnectionHandler.getConnection().prepareStatement("SELECT `Username` FROM `useraccount`").executeQuery(); } 
        catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return null;

 
    }

    public static boolean changeKeystorePassword(String path, char[] oldPassword, char[] newPassword) {
        final SecretKey KEY = retrieveSecretKey(oldPassword, path);
        boolean result = false;
        
        if(destroyKeyStore(path)){
            generateNewKeyStore(path, newPassword);
            loadToKeyStore(KEY, newPassword, path);
            result = true;
            
        }
        
        return result;
        
    }
    
}
