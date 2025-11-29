package com.taskflow.view;

import com.taskflow.entity.User;
import com.taskflow.entity.User.UserRole;
import com.taskflow.util.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    
    private User currentUser;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private List<SidebarButton> menuButtons = new ArrayList<>();
    
    // --- PALET WARNA (Sama dengan LoginFrame) ---
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);    // Blue 600
    private static final Color BG_COLOR = new Color(248, 250, 252);       // Slate 50
    
    // Sidebar Colors
    private static final Color SIDEBAR_BG = new Color(15, 23, 42);        // Slate 900 (Lebih gelap agar elegan)
    private static final Color SIDEBAR_TEXT = new Color(148, 163, 184);   // Slate 400
    private static final Color SIDEBAR_TEXT_ACTIVE = Color.WHITE;
    private static final Color SIDEBAR_HOVER = new Color(30, 41, 59);     // Slate 800
    private static final Color SIDEBAR_ACTIVE_BG = PRIMARY_COLOR;         // Blue 600
    
    private static final String FONT_NAME = "Inter";

    public MainFrame() {
        // Simulasi user jika null (untuk testing tampilan saja, hapus block ini di production)
        if (SessionManager.getInstance().getCurrentUser() == null) {
            this.currentUser = new User(); // Mock
            this.currentUser.setFullName("Admin User");
            this.currentUser.setRole(UserRole.ADMIN);
        } else {
            this.currentUser = SessionManager.getInstance().getCurrentUser();
        }
        
        initComponents();
    }
    
    private void initComponents() {
        setTitle("TaskFlow - Workspace");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        // 1. Setup Sidebar
        sidebarPanel = createSidebar();
        
        // 2. Setup Content Area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Padding konten agar tidak mepet
        
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Load default view
        loadDashboard();
        
        add(mainPanel);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(260, getHeight()));
        
        // --- Header (Logo) ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(SIDEBAR_BG);
        topPanel.setBorder(new EmptyBorder(30, 25, 40, 25));
        
        JLabel logoLabel = new JLabel("📋 TaskFlow");
        logoLabel.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        logoLabel.setForeground(Color.WHITE);
        
        JLabel roleLabel = new JLabel(getRoleDisplayName().toUpperCase());
        roleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 10));
        roleLabel.setForeground(new Color(96, 165, 250)); // Blue 400 for accent
        roleLabel.setBorder(new EmptyBorder(5, 2, 0, 0));

        topPanel.add(logoLabel);
        topPanel.add(roleLabel);
        
        // --- Menu Items ---
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(0, 15, 0, 15)); // Margin kiri kanan untuk tombol
        
        // Helper untuk menambah menu
        addMenu(menuPanel, "Dashboard", "🏠", e -> loadDashboard());
        addMenu(menuPanel, "Projects", "📁", e -> loadProjects());
        addMenu(menuPanel, "Issues", "🐞", e -> loadIssues());
        
        if (currentUser.getRole() == UserRole.ADMIN || currentUser.getRole() == UserRole.PROJECT_MANAGER) {
            menuPanel.add(createSectionTitle("MANAGEMENT"));
            addMenu(menuPanel, "Teams", "👥", e -> loadTeams());
        }
        
        if (currentUser.getRole() == UserRole.ADMIN) {
            addMenu(menuPanel, "Users", "👤", e -> loadUsers());
        }
        
        menuPanel.add(createSectionTitle("SYSTEM"));
        addMenu(menuPanel, "Reports", "📊", e -> loadReports());
        addMenu(menuPanel, "Settings", "⚙️", e -> loadSettings());
        
        // Spacer untuk mendorong profil ke bawah
        menuPanel.add(Box.createVerticalGlue());

        // --- Bottom (User Profile) ---
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(SIDEBAR_HOVER); // Sedikit lebih terang dari bg sidebar
        userPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel userName = new JLabel(currentUser.getFullName());
        userName.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        userName.setForeground(Color.WHITE);
        
        JLabel logoutText = new JLabel("Click to Logout");
        logoutText.setFont(new Font(FONT_NAME, Font.PLAIN, 11));
        logoutText.setForeground(SIDEBAR_TEXT);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(userName);
        textPanel.add(logoutText);
        
        userPanel.add(new JLabel("👤  "), BorderLayout.WEST);
        userPanel.add(textPanel, BorderLayout.CENTER);
        
        // Logout action
        userPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleLogout();
            }
        });

        sidebar.add(topPanel, BorderLayout.NORTH);
        sidebar.add(menuPanel, BorderLayout.CENTER);
        sidebar.add(userPanel, BorderLayout.SOUTH);
        
        return sidebar;
    }
    
    // --- Helper Component: Section Title ---
    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, Font.BOLD, 10));
        label.setForeground(new Color(100, 116, 139));
        label.setBorder(new EmptyBorder(20, 10, 10, 10));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void addMenu(JPanel panel, String text, String icon, java.awt.event.ActionListener action) {
        SidebarButton btn = new SidebarButton(text, icon);
        btn.addActionListener(e -> {
            // Reset semua button
            for (SidebarButton b : menuButtons) {
                b.setActive(false);
            }
            // Set button ini aktif
            btn.setActive(true);
            // Jalankan action
            action.actionPerformed(e);
        });
        
        // Set dashboard aktif default
        if (menuButtons.isEmpty()) btn.setActive(true);
        
        menuButtons.add(btn);
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing antar menu
    }
    
    // --- View Loaders (Content Switching) ---
    
    private void setHeader(String title, String subtitle) {
        contentPanel.removeAll();
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        JLabel lblSubtitle = new JLabel(subtitle);
        lblSubtitle.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 116, 139));
        
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(lblSubtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer ke konten
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void loadDashboard() {
		contentPanel.removeAll();
		DashboardPanel dashboardPanel = new DashboardPanel(currentUser);
		contentPanel.add(dashboardPanel, BorderLayout.CENTER);
		refreshUI();
	}
    
    private void loadProjects() {
        setHeader("Projects", "Manage all your ongoing projects");
        refreshUI();
    }
    
    private void loadIssues() { setHeader("Issues", "Track bugs and issues"); refreshUI(); }
    private void loadTeams() { setHeader("Teams", "Manage team members"); refreshUI(); }
    private void loadUsers() { setHeader("Users", "System user administration"); refreshUI(); }
    private void loadReports() { setHeader("Reports", "View analytical reports"); refreshUI(); }
    private void loadSettings() { setHeader("Settings", "Configure your preferences"); refreshUI(); }
    
    private void refreshUI() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    // --- Helper: Simple Card Dashboard ---
    private JPanel createCard(String title, String value) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(200, 120));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        lblTitle.setForeground(new Color(100, 116, 139));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        lblValue.setForeground(PRIMARY_COLOR);
        
        card.add(lblTitle);
        card.add(Box.createVerticalGlue());
        card.add(lblValue);
        
        return card;
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Logout from TaskFlow?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
    
    private String getRoleDisplayName() {
        if(currentUser.getRole() == null) return "User";
        return currentUser.getRole().toString().replace("_", " ");
    }
    
    // =================================================================================
    //  CUSTOM SIDEBAR BUTTON CLASS
    //  Ini adalah rahasia tampilan sidebar yang cantik
    // =================================================================================
    private class SidebarButton extends JButton {
        private boolean isActive = false;
        private Color normalColor = SIDEBAR_TEXT;
        private Color activeColor = SIDEBAR_TEXT_ACTIVE;
        
        public SidebarButton(String text, String icon) {
            super("  " + icon + "   " + text); // Spasi manual untuk padding icon
            setFont(new Font(FONT_NAME, Font.PLAIN, 14));
            setForeground(normalColor);
            
            // Hilangkan dekorasi default Swing
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(SwingConstants.LEFT);
            
            // Set padding dalam tombol
            setBorder(new EmptyBorder(12, 15, 12, 15));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // Full width
            setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!isActive) {
                        setBackground(SIDEBAR_HOVER);
                        setContentAreaFilled(true); // Aktifkan background paint
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!isActive) {
                        setContentAreaFilled(false); // Matikan background paint
                    }
                }
            });
        }
        
        public void setActive(boolean active) {
            this.isActive = active;
            if (active) {
                setForeground(activeColor);
                setFont(new Font(FONT_NAME, Font.BOLD, 14));
                setBackground(SIDEBAR_ACTIVE_BG);
                setContentAreaFilled(true);
            } else {
                setForeground(normalColor);
                setFont(new Font(FONT_NAME, Font.PLAIN, 14));
                setContentAreaFilled(false);
            }
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isActive || getModel().isRollover()) {
                g2d.setColor(getBackground());
                // Menggambar Rounded Rectangle untuk background tombol
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
            
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ex) {}
            new MainFrame().setVisible(true);
        });
    }
}