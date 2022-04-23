/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentinel;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import java.awt.Color;
import javax.swing.ImageIcon;
import sentinel.FrontEnd.SplashScreenForm;
import sentinel.Utility.ToolBox;

/**
 *
 * @author Shalin Kulawardane
 */
public class Sentinel {
    // Set the colors here. These colors will be used in the other forms.
    public static final Color ACCENT_COLOR = new Color(98, 0 ,234);
    public static final Color DARK_DOMINANT_COLOR = new Color(0, 0, 0);
    public static final Color DOMINANT_COLOR = new Color(8, 8, 8);
    public static final Color RED_COLOR = new Color(214, 44, 73);
    public static final Color ORANGE_COLOR = new Color(255, 176, 59);
    public static final Color WHITE_COLOR = new Color(219, 227, 227);
    public static final Color UNFOCUSED_COLOR = new Color(102, 102, 102);
    public static final Color GREEN_COLOR = new Color(0, 205, 102);
    public static final Color FOCUSED_COLOR = ACCENT_COLOR;    
    public static final ImageIcon PASSWORD_VISIBLE_ICON = new ImageIcon("src\\Assets\\Images\\passwordVisible.png");
    public static final ImageIcon PASSWORD_INVISIBLE_ICON = new ImageIcon("src\\Assets\\Images\\passwordInvisible.png");
    public static final FlatDraculaIJTheme CLASS_NAME = new com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme();
    
    public static void main(String[] args) {
        try {            
            SplashScreenForm splashScreen = new SplashScreenForm();
            splashScreen.setVisible(true);

        } catch (Exception ex) { ToolBox.triggerException(ex); }
        
    }   
    
}
