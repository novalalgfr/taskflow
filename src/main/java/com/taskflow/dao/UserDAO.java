package com.taskflow.dao;

import com.taskflow.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO extends BaseDAO<User, Integer> {
    
    public UserDAO(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }
    
    public Optional<User> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.username = :username", 
                User.class
            );
            query.setParameter("username", username);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by username: " + e.getMessage(), e);
        }
    }
    
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.email = :email", 
                User.class
            );
            query.setParameter("email", email);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
    }
    
    public List<User> findByRole(String role) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.role = :role ORDER BY u.fullName", 
                User.class
            );
            query.setParameter("role", role);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by role: " + e.getMessage(), e);
        }
    }
    
    public List<User> findByStatus(String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.status = :status ORDER BY u.fullName", 
                User.class
            );
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by status: " + e.getMessage(), e);
        }
    }
    
    public List<User> findActiveUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.status = 'ACTIVE' ORDER BY u.fullName", 
                User.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding active users: " + e.getMessage(), e);
        }
    }
    
    public List<User> searchByName(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE LOWER(u.fullName) LIKE LOWER(:keyword) ORDER BY u.fullName", 
                User.class
            );
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching users: " + e.getMessage(), e);
        }
    }
    
    public boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", 
                Long.class
            );
            query.setParameter("username", username);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking username existence: " + e.getMessage(), e);
        }
    }
    
    public boolean existsByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", 
                Long.class
            );
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking email existence: " + e.getMessage(), e);
        }
    }
    
    public Long countByRole(String role) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.role = :role", 
                Long.class
            );
            query.setParameter("role", role);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting users by role: " + e.getMessage(), e);
        }
    }
    
    public void updateLastLogin(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                "UPDATE User u SET u.lastLogin = CURRENT_TIMESTAMP WHERE u.userId = :userId"
            );
            query.setParameter("userId", userId);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating last login: " + e.getMessage(), e);
        }
    }
    
    public List<User> findTeamMembers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.role IN ('TEAM_MEMBER', 'PROJECT_MANAGER') AND u.status = 'ACTIVE' ORDER BY u.fullName", 
                User.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding team members: " + e.getMessage(), e);
        }
    }
    
    public List<User> findByTeamId(Integer teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                "SELECT u FROM User u JOIN u.teamMembers tm WHERE tm.team.teamId = :teamId ORDER BY u.fullName", 
                User.class
            );
            query.setParameter("teamId", teamId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by team: " + e.getMessage(), e);
        }
    }
}