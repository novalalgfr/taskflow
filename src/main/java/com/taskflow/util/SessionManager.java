package com.taskflow.util;

import com.taskflow.entity.User;
import com.taskflow.entity.User.UserRole;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public void logout() {
        this.currentUser = null;
    }
    
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
    
    public UserRole getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
    
    public Integer getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : null;
    }
    
    public boolean hasRole(UserRole role) {
        return currentUser != null && currentUser.getRole() == role;
    }
    
    public boolean isAdmin() {
        return hasRole(UserRole.ADMIN);
    }
    
    public boolean isProjectManager() {
        return hasRole(UserRole.PROJECT_MANAGER);
    }
    
    public boolean isTeamMember() {
        return hasRole(UserRole.TEAM_MEMBER);
    }
    
    public boolean isReporter() {
        return hasRole(UserRole.REPORTER);
    }
}