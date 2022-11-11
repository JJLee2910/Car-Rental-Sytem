package main.frontend.util;

import javax.swing.*;

public class SwingUtils {
    /** general functions used in frontend
     * display message info
     * display error message info
     */
    public static void promptMessageInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void promptMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.ERROR_MESSAGE);
    }
}
