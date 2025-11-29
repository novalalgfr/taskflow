package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "issue_dependencies",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_dependency",
        columnNames = {"issue_id", "depends_on_issue_id", "dependency_type"}
    ),
    indexes = {
        @Index(name = "idx_issue_id", columnList = "issue_id"),
        @Index(name = "idx_depends_on", columnList = "depends_on_issue_id")
    }
)
public class IssueDependency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dependency_id")
    private Integer dependencyId;
    
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
    
    @ManyToOne
    @JoinColumn(name = "depends_on_issue_id", nullable = false)
    private Issue dependsOnIssue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type", nullable = false)
    private DependencyType dependencyType = DependencyType.RELATED_TO;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    public enum DependencyType {
        BLOCKS, BLOCKED_BY, RELATED_TO
    }
    
    public IssueDependency() {
        this.createdDate = LocalDateTime.now();
    }
    
    public IssueDependency(Issue issue, Issue dependsOnIssue, DependencyType dependencyType) {
        this();
        this.issue = issue;
        this.dependsOnIssue = dependsOnIssue;
        this.dependencyType = dependencyType;
    }
    
    public Integer getDependencyId() {
        return dependencyId;
    }
    
    public void setDependencyId(Integer dependencyId) {
        this.dependencyId = dependencyId;
    }
    
    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public Issue getDependsOnIssue() {
        return dependsOnIssue;
    }
    
    public void setDependsOnIssue(Issue dependsOnIssue) {
        this.dependsOnIssue = dependsOnIssue;
    }
    
    public DependencyType getDependencyType() {
        return dependencyType;
    }
    
    public void setDependencyType(DependencyType dependencyType) {
        this.dependencyType = dependencyType;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public String toString() {
        return "IssueDependency{" +
                "dependencyId=" + dependencyId +
                ", issue=" + (issue != null ? issue.getIssueId() : "None") +
                ", dependsOnIssue=" + (dependsOnIssue != null ? dependsOnIssue.getIssueId() : "None") +
                ", dependencyType=" + dependencyType +
                ", createdDate=" + createdDate +
                '}';
    }
}