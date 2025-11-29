package com.taskflow;

import com.taskflow.view.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point untuk TaskFlow Application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   TaskFlow Application Started   ");
        System.out.println("=================================");
        
        // Set Look and Feel (optional - untuk UI yang lebih modern)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting Look and Feel: " + e.getMessage());
        }
        
        // Run Swing application in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}