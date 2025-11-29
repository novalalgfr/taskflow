package com.taskflow.view;

import com.taskflow.config.HibernateConfig;
import com.taskflow.dao.IssueDAO;
import com.taskflow.dao.ProjectDAO;
import com.taskflow.dao.UserDAO;
import com.taskflow.entity.Issue;
import com.taskflow.entity.Project;
import com.taskflow.entity.User;
import com.taskflow.entity.User.UserRole;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardPanel extends JPanel {
    
    private User currentUser;
    
    private ProjectDAO projectDAO;
    private IssueDAO issueDAO;
    private UserDAO userDAO;

	private DefaultTableModel tableModel;
    
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    private static final Color WARNING_COLOR = new Color(234, 179, 8);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color INFO_COLOR = new Color(59, 130, 246);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color TEXT_LIGHT = new Color(100, 116, 139);
    private static final Color BG_COLOR = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    
    private static final String FONT_NAME = "Inter";
    
    public DashboardPanel(User user) {
        this.currentUser = user;
        
        this.projectDAO = new ProjectDAO(HibernateConfig.getSessionFactory());
        this.issueDAO = new IssueDAO(HibernateConfig.getSessionFactory());
        this.userDAO = new UserDAO(HibernateConfig.getSessionFactory());
        
        initComponents();
        loadDashboardData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        
        JPanel headerPanel = createHeaderPanel();
        JPanel statsPanel = createStatsPanel();
        JPanel contentPanel = createContentPanel();
        
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(contentPanel);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG_COLOR);
        
        JLabel welcomeLabel = new JLabel("Welcome back, " + currentUser.getFullName() + "!");
        welcomeLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel dateLabel = new JLabel(getCurrentDate());
        dateLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        dateLabel.setForeground(TEXT_LIGHT);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(dateLabel);
        
        panel.add(leftPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 20, 0));
        panel.setBackground(BG_COLOR);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        
        if (currentUser.getRole() == UserRole.ADMIN) {
            panel.add(createStatCard("Total Users", "0", "👤", INFO_COLOR));
            panel.add(createStatCard("Total Projects", "0", "📁", PRIMARY_COLOR));
            panel.add(createStatCard("Total Issues", "0", "✓", WARNING_COLOR));
            panel.add(createStatCard("Active Teams", "0", "👥", SUCCESS_COLOR));
        } else if (currentUser.getRole() == UserRole.PROJECT_MANAGER) {
            panel.add(createStatCard("My Projects", "0", "📁", PRIMARY_COLOR));
            panel.add(createStatCard("Total Tasks", "0", "✓", INFO_COLOR));
            panel.add(createStatCard("Team Members", "0", "👥", SUCCESS_COLOR));
            panel.add(createStatCard("Overdue", "0", "⚠", ERROR_COLOR));
        } else if (currentUser.getRole() == UserRole.TEAM_MEMBER) {
            panel.add(createStatCard("My Tasks", "0", "✓", PRIMARY_COLOR));
            panel.add(createStatCard("In Progress", "0", "🔄", INFO_COLOR));
            panel.add(createStatCard("Completed", "0", "✅", SUCCESS_COLOR));
            panel.add(createStatCard("Overdue", "0", "⚠", ERROR_COLOR));
        } else { // REPORTER
            panel.add(createStatCard("Reported Issues", "0", "🐛", PRIMARY_COLOR));
            panel.add(createStatCard("Open", "0", "📭", WARNING_COLOR));
            panel.add(createStatCard("Resolved", "0", "✅", SUCCESS_COLOR));
            panel.add(createStatCard("Closed", "0", "🔒", TEXT_LIGHT));
        }
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, String icon, Color iconColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setForeground(iconColor);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_BG);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        valueLabel.setForeground(TEXT_COLOR);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(valueLabel);
        
        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(BG_COLOR);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        
        panel.add(createTasksCard());
        panel.add(createActivityCard());
        
        return panel;
    }
    
    private JPanel createTasksCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_BG);
        
        JLabel titleLabel = new JLabel(getTasksCardTitle());
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        
        JButton viewAllButton = new JButton("View All →");
        viewAllButton.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        viewAllButton.setForeground(PRIMARY_COLOR);
        viewAllButton.setBorderPainted(false);
        viewAllButton.setContentAreaFilled(false);
        viewAllButton.setFocusPainted(false);
        viewAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(viewAllButton, BorderLayout.EAST);
        
        String[] columns = {"Task", "Priority", "Status", "Due Date"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        table.setRowHeight(45);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(239, 246, 255));
        table.setSelectionForeground(TEXT_COLOR);
        table.getTableHeader().setFont(new Font(FONT_NAME, Font.BOLD, 13));
        table.getTableHeader().setBackground(BG_COLOR);
        table.getTableHeader().setForeground(TEXT_COLOR);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.CENTER);
        card.add(scrollPane, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createActivityCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("Recent Activity");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        
        JPanel activityListPanel = new JPanel();
        activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));
        activityListPanel.setBackground(CARD_BG);
        
        activityListPanel.add(createActivityItem(
            "John Doe created task TASK-123",
            "2 hours ago",
            "📝"
        ));
        activityListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        activityListPanel.add(createActivityItem(
            "Jane Smith completed TASK-122",
            "4 hours ago",
            "✅"
        ));
        activityListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        activityListPanel.add(createActivityItem(
            "New project 'Mobile App' created",
            "Yesterday",
            "📁"
        ));
        activityListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        activityListPanel.add(createActivityItem(
            "Bob assigned you to TASK-125",
            "2 days ago",
            "👤"
        ));
        
        JScrollPane scrollPane = new JScrollPane(activityListPanel);
        scrollPane.setBorder(null);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.CENTER);
        card.add(scrollPane, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createActivityItem(String text, String time, String icon) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(CARD_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_BG);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        textLabel.setForeground(TEXT_COLOR);
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_LIGHT);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(textLabel);
        textPanel.add(timeLabel);
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private String getTasksCardTitle() {
        switch (currentUser.getRole()) {
            case ADMIN:
                return "Recent Projects";
            case PROJECT_MANAGER:
                return "My Projects";
            case TEAM_MEMBER:
                return "My Tasks";
            case REPORTER:
                return "My Reported Issues";
            default:
                return "Tasks";
        }
    }
    
    private void loadDashboardData() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    loadStatistics();
                    loadTasksData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
    
    private void loadStatistics() {
        try {
            if (currentUser.getRole() == UserRole.ADMIN) {
                long totalUsers = userDAO.count();
                long totalProjects = projectDAO.count();
                long totalIssues = issueDAO.count();
                
                updateStatCard(0, String.valueOf(totalUsers));
                updateStatCard(1, String.valueOf(totalProjects));
                updateStatCard(2, String.valueOf(totalIssues));
                updateStatCard(3, "0");
                
            } else if (currentUser.getRole() == UserRole.PROJECT_MANAGER) {
                List<Project> myProjects = projectDAO.findByProjectManagerId(currentUser.getUserId());
                long totalTasks = 0;
                for (Project p : myProjects) {
                    totalTasks += issueDAO.findByProjectId(p.getProjectId()).size();
                }
                
                updateStatCard(0, String.valueOf(myProjects.size()));
                updateStatCard(1, String.valueOf(totalTasks));
                updateStatCard(2, "0");
                updateStatCard(3, "0");
                
            } else if (currentUser.getRole() == UserRole.TEAM_MEMBER) {
                List<Issue> myTasks = issueDAO.findByAssigneeId(currentUser.getUserId());
                long inProgress = issueDAO.findByAssigneeIdAndStatus(currentUser.getUserId(), "IN_PROGRESS").size();
                long completed = issueDAO.findByAssigneeIdAndStatus(currentUser.getUserId(), "DONE").size();
                
                updateStatCard(0, String.valueOf(myTasks.size()));
                updateStatCard(1, String.valueOf(inProgress));
                updateStatCard(2, String.valueOf(completed));
                updateStatCard(3, "0");
                
            } else { // REPORTER
                List<Issue> reportedIssues = issueDAO.findByReporterId(currentUser.getUserId());
                
                updateStatCard(0, String.valueOf(reportedIssues.size()));
                updateStatCard(1, "0");
                updateStatCard(2, "0");
                updateStatCard(3, "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTasksData() {
        List<Issue> tasks = new java.util.ArrayList<>();
        
        try {
            switch (currentUser.getRole()) {
                case ADMIN:
                    tasks = issueDAO.findAll(); 
                    break;
                    
                case PROJECT_MANAGER:
                    List<Project> pmProjects = projectDAO.findByProjectManagerId(currentUser.getUserId());
                    if (pmProjects != null && !pmProjects.isEmpty()) {
                        for (Project project : pmProjects) {
                            List<Issue> projectIssues = issueDAO.findByProjectId(project.getProjectId());
                            if (projectIssues != null) {
                                tasks.addAll(projectIssues);
                            }
                        }
                    }
                    break;
                    
                case TEAM_MEMBER:
                    tasks = issueDAO.findByAssigneeId(currentUser.getUserId());
                    break;
                    
                case REPORTER:
                    tasks = issueDAO.findByReporterId(currentUser.getUserId());
                    break;
            }
            
            // Update UI harus dilakukan di Event Dispatch Thread
            final List<Issue> finalTasks = tasks;
            SwingUtilities.invokeLater(() -> updateTaskTable(finalTasks));
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }

	private void updateTaskTable(List<Issue> tasks) {
        if (tableModel == null) return;
        
        tableModel.setRowCount(0);
        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy");
        
        for (Issue issue : tasks) {
            String title = issue.getTitle();
            
            String priority = issue.getPriority() != null ? issue.getPriority().name() : "-";
            String status = issue.getStatus() != null ? issue.getStatus().name().replace("_", " ") : "-";
            
            String dueDateStr = "-";
            if (issue.getDueDate() != null) {
                dueDateStr = issue.getDueDate().format(formatter);
            }
            
            tableModel.addRow(new Object[]{title, priority, status, dueDateStr});
        }
    }
    
    private void updateStatCard(int index, String value) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = ((JPanel) ((JScrollPane) getComponent(0)).getViewport().getView()).getComponents();
            if (components.length > 2) {
                JPanel statsPanel = (JPanel) components[2];
                if (statsPanel.getComponentCount() > index) {
                    JPanel card = (JPanel) statsPanel.getComponent(index);
                    JPanel textPanel = (JPanel) card.getComponent(1);
                    JLabel valueLabel = (JLabel) textPanel.getComponent(2);
                    valueLabel.setText(value);
                }
            }
        });
    }
    
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        return sdf.format(new java.util.Date());
    }
}