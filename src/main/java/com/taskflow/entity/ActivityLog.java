package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "activity_logs",
    indexes = {
        @Index(name = "idx_issue_id", columnList = "issue_id"),
        @Index(name = "idx_project_id", columnList = "project_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_timestamp", columnList = "timestamp")
    }
)
public class ActivityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;
    
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = true)
    private Issue issue;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "action", nullable = false, length = 50)
    private String action;
    
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;
    
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;
    
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
    
    public ActivityLog() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ActivityLog(User user, String action) {
        this();
        this.user = user;
        this.action = action;
    }
    
    public ActivityLog(Issue issue, User user, String action, String oldValue, String newValue) {
        this();
        this.issue = issue;
        this.user = user;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public ActivityLog(Project project, User user, String action, String oldValue, String newValue) {
        this();
        this.project = project;
        this.user = user;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public Integer getLogId() {
        return logId;
    }
    
    public void setLogId(Integer logId) {
        this.logId = logId;
    }
    
    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getOldValue() {
        return oldValue;
    }
    
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
    
    public String getNewValue() {
        return newValue;
    }
    
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "ActivityLog{" +
                "logId=" + logId +
                ", issue=" + (issue != null ? issue.getIssueId() : "None") +
                ", project=" + (project != null ? project.getProjectName() : "None") +
                ", user=" + (user != null ? user.getUsername() : "None") +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}