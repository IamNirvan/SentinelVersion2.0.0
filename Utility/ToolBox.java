/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel.Utility;

import java.awt.Component;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sentinel.FrontEnd.DashboardForm;
import sentinel.Sentinel;
import sentinel.User.Account;
import sentinel.FrontEnd.PayloadManagementForm;
import sentinel.FrontEnd.PasswordTestingForm;

/**
 *
 * @author Shalin Kulawardane
 */
public class ToolBox {
    private static Account account;
    private static PayloadManagementForm passwordManagement;
    private static PasswordTestingForm passwordTester;
    private static DashboardForm dashboard;    
    
    public static void setAccount(Account validAccount) { account = validAccount; }
    
    public static Account getAccount() { return account; }

    public static void setPasswordManagement(PayloadManagementForm ValidPasswordManagement) { passwordManagement = ValidPasswordManagement; }
    
    public static PayloadManagementForm getPasswordManagemaner() { return passwordManagement; }
    
    public static void setPasswordTester(PasswordTestingForm ValidPasswordTester) { passwordTester = ValidPasswordTester; }
    
    public static PasswordTestingForm getPasswordTester() { return passwordTester; }
    
    public static void setDashboard(DashboardForm validDashboard) { dashboard = validDashboard; }
    
    public static DashboardForm getDashboard() { return dashboard; }
    
    public static void triggerException(Exception exception) { System.out.println(exception + "\nMessage: " + exception.getMessage()); }
    
    public static int triggerOptionPane(Component parent, String message, String title, int option, int messageType) {
        return JOptionPane.showConfirmDialog(parent, message, title, option, messageType);
        
    }
    
    public static void triggerMessageDialog(Component parent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parent, message, title, messageType);
        
    }
    
    final public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme());
            UIManager.put( "Button.arc", 0 );
            UIManager.put( "Component.arc", 0 );
            UIManager.put( "ProgressBar.arc", 6 );
            UIManager.put( "TextComponent.arc", 0 );
            UIManager.put( "Component.focusWidth", 1 );
            UIManager.put( "Component.focusColor", Sentinel.ACCENT_COLOR );
            UIManager.put( "ScrollBar.showButtons", true );
            UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
            UIManager.put( "ScrollBar.width", 14);
            
        } catch(UnsupportedLookAndFeelException ex){ ToolBox.triggerException(ex); }
   
    }
    
    public static void textFieldFocusGained(JTextField textField, String text) {
        /*  This method is the global focus gained handling function for the text fields in
            in this form.
            It takes a JTextField object as the parameter textField. 
            This represents the object that should be controlled. 
            The text parameter represents the default text for that field.        
        */
        
        if(textField.getText().equals(text)) { textField.setText(null); }
        textField.setForeground(Sentinel.FOCUSED_COLOR);

    }

    public static void textFieldFocusLost(JTextField textField, String text) {    
        /*  This the global focus lost handler for text fields in this form.
            It takes a parameter: 'textField' that resembles the taxtField object that should be handled.
            The 'text' parameter represents the default text for that field.
        */
        
        if(textField.getText().length() == 0) { textField.setText(text); }
        textField.setForeground(Sentinel.UNFOCUSED_COLOR);        

    }
    
    public static void passwordFieldFocusGained(JPasswordField passwordField, JLabel iconLbl, String text) {    
        if(String.valueOf(passwordField.getPassword()).equals(text)) {
            passwordField.setText(null);
            passwordField.setEchoChar('*');
            iconLbl.setIcon(Sentinel.PASSWORD_VISIBLE_ICON);

        }
        
        passwordField.setForeground(Sentinel.FOCUSED_COLOR);

    }
    
    public static void passwordFieldFocusLost(JPasswordField passwordField, JLabel iconLbl, String text) {
        passwordField.setForeground(Sentinel.UNFOCUSED_COLOR);

        if(passwordField.getPassword().length == 0) {
            passwordField.setText(text);
            passwordField.setEchoChar('\u0000');
            iconLbl.setIcon(Sentinel.PASSWORD_INVISIBLE_ICON);

        }        

    }
    
    public static byte[] convertToByte(char[] array) {
        final byte[] RESULT = new byte[array.length];

        for(int i = 0; i < array.length; i++) { RESULT[i] = (byte)array[i]; }

        return RESULT;

    }

    public static char[] convertToChar(byte[] array)  {
        final char[] RESULT = new char[array.length];

        for(int i = 0; i < array.length; i++)  { RESULT[i] = (char)array[i]; }

        return RESULT;

    }
    
    public static HashSet<Character> convertToHashSet(char[] array) {
        Character[] newArray = new Character[array.length];

        for(int i = 0; i < array.length; i++) { newArray[i] = (Character) array[i]; }

        return new HashSet<>(Arrays.asList(newArray));        

    }
    
}
