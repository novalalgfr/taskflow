package com.taskflow.view;

import javax.swing.*;
import java.awt.*;

/**
 * Login Frame - Entry point UI
 */
public class LoginFrame extends JFrame {
    
    public LoginFrame() {
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("TaskFlow - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center di layar
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title Label
        JLabel titleLabel = new JLabel("🚀 TaskFlow Project Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center Panel dengan Hello World
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        JLabel helloLabel = new JLabel("Hello World!");
        helloLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        helloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("Welcome to TaskFlow System");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(helloLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(descLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Application");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this, 
                "Hello from TaskFlow!\nDatabase ready to connect!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE
            );
            openMainFrame();
        });
        
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void openMainFrame() {
        // Nanti akan buka MainFrame
        JOptionPane.showMessageDialog(this, "MainFrame will be here!");
        // MainFrame mainFrame = new MainFrame();
        // mainFrame.setVisible(true);
        // this.dispose();
    }
}