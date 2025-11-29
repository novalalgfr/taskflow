package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "attachments",
    indexes = {
        @Index(name = "idx_issue_id", columnList = "issue_id")
    }
)
public class Attachment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Integer attachmentId;
    
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
    
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    
    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;
    
    @Column(name = "file_size", nullable = false)
    private Long fileSize; // dalam bytes
    
    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;
    
    @Column(name = "uploaded_date", nullable = false, updatable = false)
    private LocalDateTime uploadedDate;
    
    public Attachment() {
        this.uploadedDate = LocalDateTime.now();
    }
    
    public Attachment(Issue issue, String fileName, String filePath, Long fileSize, User uploadedBy) {
        this();
        this.issue = issue;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadedBy = uploadedBy;
    }
    
    public Integer getAttachmentId() {
        return attachmentId;
    }
    
    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }
    
    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public Long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    public User getUploadedBy() {
        return uploadedBy;
    }
    
    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    
    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }
    
    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
    
    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", issue=" + (issue != null ? issue.getIssueId() : "None") +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", uploadedBy=" + (uploadedBy != null ? uploadedBy.getUsername() : "None") +
                ", uploadedDate=" + uploadedDate +
                '}';
    }
}