package com.taskflow.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main Application Frame (Dashboard)
 */
public class MainFrame extends JFrame {
    
    public MainFrame() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("TaskFlow - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Placeholder
        JLabel label = new JLabel("Main Dashboard - Coming Soon!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }
}