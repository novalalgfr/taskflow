package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "team_members",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_team_user",
        columnNames = {"team_id", "user_id"}
    )
)
public class TeamMember {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Integer teamMemberId;
    
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "joined_date", nullable = false, updatable = false)
    private LocalDateTime joinedDate;
    
    @Column(name = "role_in_team", length = 50)
    private String roleInTeam = "Member";
    
    public TeamMember() {
        this.joinedDate = LocalDateTime.now();
    }
    
    public TeamMember(Team team, User user, String roleInTeam) {
        this();
        this.team = team;
        this.user = user;
        this.roleInTeam = roleInTeam;
    }
    
    public Integer getTeamMemberId() {
        return teamMemberId;
    }
    
    public void setTeamMemberId(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDateTime getJoinedDate() {
        return joinedDate;
    }
    
    public void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }
    
    public String getRoleInTeam() {
        return roleInTeam;
    }
    
    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }
    
    @Override
    public String toString() {
        return "TeamMember{" +
                "teamMemberId=" + teamMemberId +
                ", team=" + (team != null ? team.getTeamName() : "None") +
                ", user=" + (user != null ? user.getFullName() : "None") +
                ", roleInTeam='" + roleInTeam + '\'' +
                ", joinedDate=" + joinedDate +
                '}';
    }
}