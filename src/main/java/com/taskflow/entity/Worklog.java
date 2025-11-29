package com.taskflow.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "work_logs",
    indexes = {
        @Index(name = "idx_issue_id", columnList = "issue_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_work_date", columnList = "work_date")
    }
)
public class WorkLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worklog_id")
    private Integer worklogId;
    
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "hours_spent", nullable = false, precision = 5, scale = 2)
    private BigDecimal hoursSpent;
    
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    public WorkLog() {
        this.createdDate = LocalDateTime.now();
    }
    
    public WorkLog(Issue issue, User user, BigDecimal hoursSpent, LocalDate workDate) {
        this();
        this.issue = issue;
        this.user = user;
        this.hoursSpent = hoursSpent;
        this.workDate = workDate;
    }
    
    public Integer getWorklogId() {
        return worklogId;
    }
    
    public void setWorklogId(Integer worklogId) {
        this.worklogId = worklogId;
    }
    
    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public BigDecimal getHoursSpent() {
        return hoursSpent;
    }
    
    public void setHoursSpent(BigDecimal hoursSpent) {
        this.hoursSpent = hoursSpent;
    }
    
    public LocalDate getWorkDate() {
        return workDate;
    }
    
    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public String toString() {
        return "WorkLog{" +
                "worklogId=" + worklogId +
                ", issue=" + (issue != null ? issue.getIssueId() : "None") +
                ", user=" + (user != null ? user.getUsername() : "None") +
                ", hoursSpent=" + hoursSpent +
                ", workDate=" + workDate +
                ", createdDate=" + createdDate +
                '}';
    }
}