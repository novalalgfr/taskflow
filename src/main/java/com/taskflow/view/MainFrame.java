package com.taskflow.view;

import com.taskflow.entity.User;
import com.taskflow.entity.User.UserRole;
import com.taskflow.util.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    
    private User currentUser;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    
    // Colors - SAMA dengan LoginFrame
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SECONDARY_COLOR = new Color(59, 130, 246);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color TEXT_LIGHT = new Color(100, 116, 139);
    private static final Color BG_COLOR = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color SIDEBAR_BG = new Color(30, 41, 59);
    private static final Color SIDEBAR_HOVER = new Color(51, 65, 85);
    private static final Color SIDEBAR_ACTIVE = PRIMARY_COLOR;
    
    private static final String FONT_NAME = "Inter";
    
    private JButton activeMenuButton = null;
    
    public MainFrame() {
        this.currentUser = SessionManager.getInstance().getCurrentUser();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("TaskFlow - " + currentUser.getFullName());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        sidebarPanel = createSidebar();
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);
        
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        loadDashboard();
        
        add(mainPanel);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(280, getHeight()));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(SIDEBAR_BG);
        topPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
        
        JLabel logoLabel = new JLabel("📋 TaskFlow");
        logoLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        versionLabel.setForeground(TEXT_LIGHT);
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        topPanel.add(logoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(versionLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        menuPanel.add(createMenuButton("🏠 Dashboard", true, e -> loadDashboard()));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createMenuButton("📁 Projects", false, e -> loadProjects()));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createMenuButton("✓ Issues", false, e -> loadIssues()));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (currentUser.getRole() == UserRole.ADMIN || currentUser.getRole() == UserRole.PROJECT_MANAGER) {
            menuPanel.add(createMenuButton("👥 Teams", false, e -> loadTeams()));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        if (currentUser.getRole() == UserRole.ADMIN) {
            menuPanel.add(createMenuButton("👤 Users", false, e -> loadUsers()));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        menuPanel.add(createMenuButton("📊 Reports", false, e -> loadReports()));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createMenuButton("⚙️ Settings", false, e -> loadSettings()));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(SIDEBAR_BG);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 30, 20));
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(71, 85, 105));
        separator.setMaximumSize(new Dimension(240, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(10, 0));
        userPanel.setBackground(SIDEBAR_BG);
        userPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        userPanel.setMaximumSize(new Dimension(240, 60));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        avatarLabel.setForeground(Color.WHITE);
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(SIDEBAR_BG);
        
        JLabel nameLabel = new JLabel(currentUser.getFullName());
        nameLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel roleLabel = new JLabel(getRoleDisplayName());
        roleLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        roleLabel.setForeground(TEXT_LIGHT);
        
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(roleLabel);
        
        userPanel.add(avatarLabel, BorderLayout.WEST);
        userPanel.add(userInfoPanel, BorderLayout.CENTER);
        
        JButton logoutButton = createMenuButton("🚪 Logout", false, e -> handleLogout());
        
        bottomPanel.add(separator);
        bottomPanel.add(userPanel);
        bottomPanel.add(logoutButton);
        
        sidebar.add(topPanel, BorderLayout.NORTH);
        sidebar.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, boolean active, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(active ? SIDEBAR_ACTIVE : SIDEBAR_BG);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(12, 15, 12, 15));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(240, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button != activeMenuButton) {
                    button.setBackground(SIDEBAR_HOVER);
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (button != activeMenuButton) {
                    button.setBackground(SIDEBAR_BG);
                }
            }
        });
        
        button.addActionListener(e -> {
            if (activeMenuButton != null) {
                activeMenuButton.setBackground(SIDEBAR_BG);
            }
            button.setBackground(SIDEBAR_ACTIVE);
            activeMenuButton = button;
            action.actionPerformed(e);
        });
        
        if (active) {
            activeMenuButton = button;
        }
        
        return button;
    }
    
    private String getRoleDisplayName() {
        switch (currentUser.getRole()) {
            case ADMIN: return "Administrator";
            case PROJECT_MANAGER: return "Project Manager";
            case TEAM_MEMBER: return "Team Member";
            case REPORTER: return "Reporter";
            default: return "User";
        }
    }
    
    private void loadDashboard() {
        contentPanel.removeAll();
        contentPanel.add(new DashboardPanel(currentUser), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadProjects() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Projects Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadIssues() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Issues Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadTeams() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Teams Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadUsers() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Users Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadReports() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Reports Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadSettings() {
        contentPanel.removeAll();
        JLabel temp = new JLabel("Settings Module - Coming Soon", SwingConstants.CENTER);
        temp.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        temp.setForeground(TEXT_LIGHT);
        contentPanel.add(temp, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}