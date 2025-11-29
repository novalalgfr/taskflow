package com.taskflow.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "labels",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_label_project",
        columnNames = {"label_name", "project_id"}
    ),
    indexes = {
        @Index(name = "idx_project_id", columnList = "project_id")
    }
)
public class Label {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Integer labelId;
    
    @Column(name = "label_name", nullable = false, length = 50)
    private String labelName;
    
    @Column(name = "color_code", nullable = false, length = 7)
    private String colorCode; // HEX color, e.g., "#FF5733"
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    public Label() {
    }
    
    public Label(String labelName, String colorCode, Project project) {
        this.labelName = labelName;
        this.colorCode = colorCode;
        this.project = project;
    }
    
    public Integer getLabelId() {
        return labelId;
    }
    
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
    
    public String getLabelName() {
        return labelName;
    }
    
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
    
    public String getColorCode() {
        return colorCode;
    }
    
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "Label{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", project=" + (project != null ? project.getProjectName() : "None") +
                '}';
    }
}