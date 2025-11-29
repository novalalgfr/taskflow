package com.taskflow.controller;

import com.taskflow.config.HibernateConfig;
import com.taskflow.entity.User;
import com.taskflow.service.UserService;
import com.taskflow.util.SessionManager;
import org.hibernate.SessionFactory;

public class LoginController {
    
    private UserService userService;
    
    public LoginController() {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        this.userService = new UserService(sessionFactory);
    }
    
    public boolean login(String username, String password) {
		try {
			System.out.println("🔐 Login attempt - Username: " + username);
			
			if (username == null || username.trim().isEmpty()) {
				throw new RuntimeException("Username cannot be empty.");
			}
			
			if (password == null || password.trim().isEmpty()) {
				throw new RuntimeException("Password cannot be empty.");
			}
			
			System.out.println("📡 Calling userService.authenticate()...");
			User user = userService.authenticate(username.trim(), password);
			
			System.out.println("✅ Authentication successful: " + user.getUsername());
			
			SessionManager.getInstance().setCurrentUser(user);
			
			return true;
		} catch (Exception e) {
			System.err.println("❌ Login error: " + e.getMessage());
			e.printStackTrace(); // PENTING: Print full stack trace
			throw new RuntimeException(e.getMessage());
		}
	}
    
    public void logout() {
        SessionManager.getInstance().logout();
    }
    
    public User getCurrentUser() {
        return SessionManager.getInstance().getCurrentUser();
    }
    
    public boolean isLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }
}