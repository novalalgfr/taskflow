package com.taskflow.service;

import com.taskflow.dao.UserDAO;
import com.taskflow.entity.User;
import com.taskflow.entity.User.UserRole;
import com.taskflow.entity.User.UserStatus;
import com.taskflow.util.PasswordHasher;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class UserService {
    
    private UserDAO userDAO;
    
    public UserService(SessionFactory sessionFactory) {
        this.userDAO = new UserDAO(sessionFactory);
    }
    
    public User authenticate(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (user.getStatus() == UserStatus.INACTIVE) {
                throw new RuntimeException("Account is inactive. Please contact administrator.");
            }
            
            if (PasswordHasher.verifyPassword(password, user.getPassword())) {
                userDAO.updateLastLogin(user.getUserId());
                return user;
            } else {
                throw new RuntimeException("Invalid username or password.");
            }
        } else {
            throw new RuntimeException("Invalid username or password.");
        }
    }
    
    public User register(String username, String password, String fullName, String email, UserRole role) {
        if (userDAO.existsByUsername(username)) {
            throw new RuntimeException("Username already exists.");
        }
        
        if (userDAO.existsByEmail(email)) {
            throw new RuntimeException("Email already exists.");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordHasher.hashPassword(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);
        
        return userDAO.save(user);
    }
    
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userDAO.findById(userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (!PasswordHasher.verifyPassword(oldPassword, user.getPassword())) {
                throw new RuntimeException("Old password is incorrect.");
            }
            
            user.setPassword(PasswordHasher.hashPassword(newPassword));
            userDAO.update(user);
        } else {
            throw new RuntimeException("User not found.");
        }
    }
    
    public User getUserById(Integer userId) {
        return userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
    
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}