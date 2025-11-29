package com.taskflow.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
public class Issue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Integer issueId;
    
    @Column(name = "issue_key", unique = true, nullable = false, length = 20)
    private String issueKey;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
    
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "issue_type", nullable = false)
    private IssueType issueType = IssueType.TASK;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IssueStatus status = IssueStatus.BACKLOG;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority = Priority.MEDIUM;
    
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    
    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;
    
    @Column(name = "estimated_hours", precision = 5, scale = 2)
    private BigDecimal estimatedHours = BigDecimal.ZERO;
    
    @Column(name = "remaining_hours", precision = 5, scale = 2)
    private BigDecimal remainingHours = BigDecimal.ZERO;
    
    @Column(name = "story_points")
    private Integer storyPoints = 0;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "resolution")
    private Resolution resolution = Resolution.UNRESOLVED;
    
    @ManyToOne
    @JoinColumn(name = "parent_issue_id")
    private Issue parentIssue;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;
    
    public enum IssueType {
        TASK, BUG, FEATURE, ENHANCEMENT, STORY
    }
    
    public enum IssueStatus {
        BACKLOG, TODO, IN_PROGRESS, IN_REVIEW, DONE, CLOSED
    }
    
    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    public enum Resolution {
        FIXED, WONT_FIX, DUPLICATE, CANNOT_REPRODUCE, UNRESOLVED
    }
    
    public Issue() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
    
    public Issue(String issueKey, Project project, String title, User reporter) {
        this();
        this.issueKey = issueKey;
        this.project = project;
        this.title = title;
        this.reporter = reporter;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
    
    public Integer getIssueId() {
        return issueId;
    }
    
    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }
    
    public String getIssueKey() {
        return issueKey;
    }
    
    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public Sprint getSprint() {
        return sprint;
    }
    
    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public IssueType getIssueType() {
        return issueType;
    }
    
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }
    
    public IssueStatus getStatus() {
        return status;
    }
    
    public void setStatus(IssueStatus status) {
        this.status = status;
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public User getAssignee() {
        return assignee;
    }
    
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
    
    public User getReporter() {
        return reporter;
    }
    
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
    
    public BigDecimal getEstimatedHours() {
        return estimatedHours;
    }
    
    public void setEstimatedHours(BigDecimal estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
    
    public BigDecimal getRemainingHours() {
        return remainingHours;
    }
    
    public void setRemainingHours(BigDecimal remainingHours) {
        this.remainingHours = remainingHours;
    }
    
    public Integer getStoryPoints() {
        return storyPoints;
    }
    
    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public Resolution getResolution() {
        return resolution;
    }
    
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }
    
    public Issue getParentIssue() {
        return parentIssue;
    }
    
    public void setParentIssue(Issue parentIssue) {
        this.parentIssue = parentIssue;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    @Override
    public String toString() {
        return "Issue{" +
                "issueId=" + issueId +
                ", issueKey='" + issueKey + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", issueType=" + issueType +
                '}';
    }
}