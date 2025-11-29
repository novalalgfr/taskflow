package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;
    
    @Column(name = "team_name", nullable = false, length = 100)
    private String teamName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "team_lead_id")
    private User teamLead;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    public Team() {
        this.createdDate = LocalDateTime.now();
    }
    
    public Team(String teamName, String description, User teamLead) {
        this();
        this.teamName = teamName;
        this.description = description;
        this.teamLead = teamLead;
    }
    
    public Integer getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getTeamLead() {
        return teamLead;
    }
    
    public void setTeamLead(User teamLead) {
        this.teamLead = teamLead;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamLead=" + (teamLead != null ? teamLead.getFullName() : "None") +
                ", createdDate=" + createdDate +
                '}';
    }
}