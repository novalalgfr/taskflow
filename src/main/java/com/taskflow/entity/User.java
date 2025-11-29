package com.taskflow.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.TEAM_MEMBER;
    
    public enum UserRole {
        ADMIN,
        PROJECT_MANAGER,
        TEAM_MEMBER,
        REPORTER
    }
    
    @Column(name = "avatar_path", length = 255)
    private String avatarPath;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    public enum UserStatus {
        ACTIVE,
        INACTIVE
    }
    
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    public User() {
        this.createdDate = LocalDateTime.now();
    }

    public User(String username, String password, String fullName, String email, UserRole role) {
        this();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public String getAvatarPath() {
        return avatarPath;
    }
    
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
    
    public UserStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}
