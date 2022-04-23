/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import sentinel.Database.ConnectionHandler;
import sentinel.Utility.ToolBox;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Shalin Kulawardane
 */
public class Security {
    
    public static String encryptData(byte[] clearText, SecretKey key, IvParameterSpec iv) {
        /*  This method will use AES encryption, PKCS5padding and an initialization vector (IV)
            to encrypt the data and will return the Base64 encoded String version of the cipher. 
            Otherwise, it will return 'null'.
        */
        
        try {
            final Cipher CIPHER = Cipher.getInstance("AES"); 
            CIPHER.init(Cipher.ENCRYPT_MODE, key);
            // Return the encoded encrypted text.
            return Base64.getEncoder().encodeToString(CIPHER.doFinal(clearText));

        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) 
        { ToolBox.triggerException(ex); }
        
        return null;
        
    }

    public static String decryptData(byte[] cipherText, SecretKey key, IvParameterSpec iv) {
        /*  This method takes 'encryptedData' as a parameter, and will 
            decode it and return the decrypted version of the input. Otherwise, 
            it will return 'null'.
        */
        
        try {
            final Cipher CIPHER = Cipher.getInstance("AES"); 
            CIPHER.init(Cipher.DECRYPT_MODE, key);
            return new String(CIPHER.doFinal(Base64.getDecoder().decode(cipherText)));

        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) { ToolBox.triggerException(ex); }

        return null;        

    }
 
    public static String hashPassword(byte[] password, byte[] salt) {
        try {
            final MessageDigest MESSAGE_DIGEST = MessageDigest.getInstance("SHA-256");
            MESSAGE_DIGEST.update(salt);
            final byte[] DIGEST = MESSAGE_DIGEST.digest(password);
            final StringBuilder STRING_BUILDER = new StringBuilder();
            
            for(int i = 0; i < DIGEST.length; i++) { STRING_BUILDER.append(Integer.toString((DIGEST[i] & 0xff) + 0x100, 16).substring(1)); }

            return STRING_BUILDER.toString();

        } catch (NoSuchAlgorithmException ex) { ToolBox.triggerException(ex); } 
        
        return null;        

    }

    public static byte[] getSalt(String username, char[] password) {
        /*  This method generates a hash based on the user name and 
            master password
        */

        final char[] RESULT = new char[(password.length + username.length())];
        System.arraycopy(username.toCharArray(), 0, RESULT, 0, username.length());
        System.arraycopy(password, 0, RESULT, 0, password.length);
        return ToolBox.convertToByte(RESULT);

    }
    
    public static boolean checkIfPasswordsMatch(String username, char[] password) {
        try(final PreparedStatement STATEMENT = ConnectionHandler.getConnection().prepareStatement("SELECT * FROM useraccount WHERE Username = ? "
                + "AND MasterPassword = ?")) {
            STATEMENT.setString(1, username);
            STATEMENT.setString(2, hashPassword(ToolBox.convertToByte(password), getSalt(username, password)));            

            while(STATEMENT.executeQuery().next()) { return true; }
            
        } catch (SQLException ex) { ToolBox.triggerException(ex); }
        
        return false;

    }

    public static void overrideData(char[] data) { Arrays.fill(data, '0'); }
 
    public static void overrideData(byte[] data) { Arrays.fill(data, (byte)0); }
    
}
