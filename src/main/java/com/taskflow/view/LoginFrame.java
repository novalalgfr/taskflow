package com.taskflow.view;

import com.taskflow.controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    
    private LoginController loginController;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;
    private JCheckBox chkRememberMe;
    
    // Warna
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);      // Blue 600
    private static final Color SECONDARY_COLOR = new Color(59, 130, 246);   // Blue 500
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color TEXT_LIGHT = new Color(100, 116, 139);
    private static final Color BG_COLOR = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    
    // --- SETTING LEBAR FORM ---
    // Diperlebar menjadi 550
    private static final int FORM_WIDTH = 550; 
    private static final int INPUT_HEIGHT = 50; 

    // Nama Font Diubah ke Inter
    private static final String FONT_NAME = "Inter";

    public LoginFrame() {
        this.loginController = new LoginController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("TaskFlow - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Split layar 50:50
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(CARD_BG);
        
        mainPanel.add(createLeftPanel());
        mainPanel.add(createRightPanel());
        
        add(mainPanel);
    }
    
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        leftPanel.setLayout(new GridBagLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        JLabel iconLabel = new JLabel("📋");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80)); 
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("TaskFlow");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Project Management System");
        subtitleLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel featuresPanel = new JPanel();
        featuresPanel.setOpaque(false);
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        String[] features = {
            "✓ Manage projects & tasks efficiently",
            "✓ Track team progress in real-time",
            "✓ Collaborate with your team"
        };
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
            featureLabel.setForeground(new Color(255, 255, 255, 230));
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            featureLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
            featuresPanel.add(featureLabel);
        }
        
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(featuresPanel);
        
        leftPanel.add(contentPanel);
        
        return leftPanel;
    }
    
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(CARD_BG);
        rightPanel.setLayout(new GridBagLayout()); // Center vertical & horizontal
        
        JPanel formContainer = new JPanel();
        formContainer.setBackground(CARD_BG);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        
        // --- Header Text ---
        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER
        
        JLabel descLabel = new JLabel("Please enter your credentials to continue");
        descLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        descLabel.setForeground(TEXT_LIGHT);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER
        
        // --- Input Fields ---
        
        // Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        lblUsername.setForeground(TEXT_COLOR);
        // Note: Alignment Label dihandle oleh Wrapper Panel nanti
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT));
        txtUsername.setMaximumSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT)); // Fix width agar tidak melar sembarangan
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(226, 232, 240)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        txtUsername.setBackground(BG_COLOR);
        
        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        lblPassword.setForeground(TEXT_COLOR);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT));
        txtPassword.setMaximumSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT)); // Fix width
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(226, 232, 240)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        txtPassword.setBackground(BG_COLOR);
        txtPassword.addActionListener(e -> handleLogin());
        
        // Options (Remember Me & Forgot Password)
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);
        optionsPanel.setMaximumSize(new Dimension(FORM_WIDTH, 25)); // Samakan lebar dengan form width
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER PANEL
        
        chkRememberMe = new JCheckBox("Remember me");
        chkRememberMe.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        chkRememberMe.setForeground(TEXT_LIGHT);
        chkRememberMe.setOpaque(false);
        chkRememberMe.setFocusPainted(false);
        
        JLabel lblForgotPassword = new JLabel("<html><u>Forgot password?</u></html>");
        lblForgotPassword.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        lblForgotPassword.setForeground(PRIMARY_COLOR);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        optionsPanel.add(chkRememberMe, BorderLayout.WEST);
        optionsPanel.add(lblForgotPassword, BorderLayout.EAST);
        
        // Error Message
        lblError = new JLabel(" ");
        lblError.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        lblError.setForeground(ERROR_COLOR);
        lblError.setVisible(false);
        
        // --- Sign In Button ---
        btnLogin = new JButton("Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(PRIMARY_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(PRIMARY_COLOR.brighter());
                } else {
                    GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), 0, SECONDARY_COLOR
                    );
                    g2d.setPaint(gradient);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        btnLogin.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnLogin.setMaximumSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT)); // Fix width
        btnLogin.setPreferredSize(new Dimension(FORM_WIDTH, INPUT_HEIGHT));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER BUTTON
        btnLogin.addActionListener(e -> handleLogin());
        
        // --- Footer (Sign Up) ---
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signupPanel.setOpaque(false);
        signupPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER
        
        JLabel lblNoAccount = new JLabel("Don't have an account?");
        lblNoAccount.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        lblNoAccount.setForeground(TEXT_LIGHT);
        
        JLabel lblSignup = new JLabel("<html><u>Sign up</u></html>");
        lblSignup.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        lblSignup.setForeground(PRIMARY_COLOR);
        lblSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        signupPanel.add(lblNoAccount);
        signupPanel.add(lblSignup);
        
        // --- Adding Components to Container ---
        formContainer.add(Box.createVerticalGlue()); 
        
        formContainer.add(welcomeLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        formContainer.add(descLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 50)));
        
        // Helper method untuk membungkus Label + Input agar rapi tapi tetap center secara keseluruhan
        formContainer.add(createInputBlock(lblUsername, txtUsername));
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        
        formContainer.add(createInputBlock(lblPassword, txtPassword));
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        
        formContainer.add(optionsPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        
        // Panel error juga diset max width agar alignment pas
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        errorPanel.setOpaque(false);
        errorPanel.setMaximumSize(new Dimension(FORM_WIDTH, 20));
        errorPanel.add(lblError);
        errorPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // CENTER
        formContainer.add(errorPanel);
        
        formContainer.add(Box.createRigidArea(new Dimension(0, 25)));
        formContainer.add(btnLogin);
        formContainer.add(Box.createRigidArea(new Dimension(0, 25)));
        formContainer.add(signupPanel);
        
        formContainer.add(Box.createVerticalGlue());
        
        rightPanel.add(formContainer);
        return rightPanel;
    }

    // Method baru untuk membuat block input (Label + Field) yang rapi
    private JPanel createInputBlock(JLabel label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        // Set lebar panel sama dengan lebar form
        panel.setMaximumSize(new Dimension(FORM_WIDTH, 80)); 
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Panelnya di tengah
        
        // Label rata kiri relative terhadap panel ini
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT); // Field rata kiri relative panel
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(field);
        
        return panel;
    }
    
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        lblError.setVisible(false);
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }
        
        btnLogin.setEnabled(false);
        btnLogin.setText("Signing in...");
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            private String errorMessage = null;
            
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    return loginController.login(username, password);
                } catch (Exception ex) {
                    errorMessage = ex.getMessage();
                    return false;
                }
            }
            
            @Override
			protected void done() {
				try {
					boolean success = get();
					if (success) {						
						SwingUtilities.invokeLater(() -> {
							dispose();
							new MainFrame().setVisible(true); 
						});
						
					} else {
						showError(errorMessage != null ? errorMessage : "Login failed.");
						btnLogin.setEnabled(true);
						btnLogin.setText("Sign In");
					}
				} catch (Exception ex) {
					showError("An error occurred during login.");
					btnLogin.setEnabled(true);
					btnLogin.setText("Sign In");
				}
			}
        };
        worker.execute();
    }
    
    private void showError(String message) {
        lblError.setText("⚠ " + message);
        lblError.setVisible(true);
    }
    
    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        private Color color;
        
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}