package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "issue_labels",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_issue_label",
        columnNames = {"issue_id", "label_id"}
    ),
    indexes = {
        @Index(name = "idx_issue_id", columnList = "issue_id"),
        @Index(name = "idx_label_id", columnList = "label_id")
    }
)
public class IssueLabel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_label_id")
    private Integer issueLabelId;
    
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
    
    @ManyToOne
    @JoinColumn(name = "label_id", nullable = false)
    private Label label;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    public IssueLabel() {
        this.createdDate = LocalDateTime.now();
    }
    
    public IssueLabel(Issue issue, Label label) {
        this();
        this.issue = issue;
        this.label = label;
    }
    
    public Integer getIssueLabelId() {
        return issueLabelId;
    }
    
    public void setIssueLabelId(Integer issueLabelId) {
        this.issueLabelId = issueLabelId;
    }
    
    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public Label getLabel() {
        return label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public String toString() {
        return "IssueLabel{" +
                "issueLabelId=" + issueLabelId +
                ", issue=" + (issue != null ? issue.getIssueId() : "None") +
                ", label=" + (label != null ? label.getLabelName() : "None") +
                ", createdDate=" + createdDate +
                '}';
    }
}