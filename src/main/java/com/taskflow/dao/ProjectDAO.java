package com.taskflow.dao;

import com.taskflow.entity.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProjectDAO extends BaseDAO<Project, Integer> {
    
    public ProjectDAO(SessionFactory sessionFactory) {
        super(Project.class, sessionFactory);
    }
    
    public Optional<Project> findByProjectKey(String projectKey) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.projectKey = :projectKey", 
                Project.class
            );
            query.setParameter("projectKey", projectKey);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding project by key: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findByProjectManagerId(Integer managerId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.projectManager.userId = :managerId ORDER BY p.createdDate DESC", 
                Project.class
            );
            query.setParameter("managerId", managerId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding projects by manager: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findByTeamId(Integer teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.team.teamId = :teamId", 
                Project.class
            );
            query.setParameter("teamId", teamId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding projects by team: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findByStatus(String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.status = :status ORDER BY p.priority DESC", 
                Project.class
            );
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding projects by status: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findActiveProjects() {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.status = 'ACTIVE' ORDER BY p.priority DESC, p.startDate DESC", 
                Project.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding active projects: " + e.getMessage(), e);
        }
    }
    
    public List<Project> searchByName(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE LOWER(p.projectName) LIKE LOWER(:keyword) ORDER BY p.projectName", 
                Project.class
            );
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching projects: " + e.getMessage(), e);
        }
    }
    
    public List<Project> filterProjects(String status, String priority, Integer teamId, Integer managerId) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Project p WHERE 1=1");
            
            if (status != null && !status.isEmpty()) {
                hql.append(" AND p.status = :status");
            }
            if (priority != null && !priority.isEmpty()) {
                hql.append(" AND p.priority = :priority");
            }
            if (teamId != null) {
                hql.append(" AND p.team.teamId = :teamId");
            }
            if (managerId != null) {
                hql.append(" AND p.projectManager.userId = :managerId");
            }
            
            hql.append(" ORDER BY p.priority DESC, p.createdDate DESC");
            
            Query<Project> query = session.createQuery(hql.toString(), Project.class);
            
            if (status != null && !status.isEmpty()) {
                query.setParameter("status", status);
            }
            if (priority != null && !priority.isEmpty()) {
                query.setParameter("priority", priority);
            }
            if (teamId != null) {
                query.setParameter("teamId", teamId);
            }
            if (managerId != null) {
                query.setParameter("managerId", managerId);
            }
            
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error filtering projects: " + e.getMessage(), e);
        }
    }
    
    public Long countByStatus(String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Project p WHERE p.status = :status", 
                Long.class
            );
            query.setParameter("status", status);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting projects by status: " + e.getMessage(), e);
        }
    }
    
    public Long countByPriority(String priority) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Project p WHERE p.priority = :priority", 
                Long.class
            );
            query.setParameter("priority", priority);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting projects by priority: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findProjectsEndingSoon(int daysAhead) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.targetEndDate IS NOT NULL " +
                "AND p.targetEndDate BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, :days) " +
                "AND p.status IN ('PLANNING', 'ACTIVE') " +
                "ORDER BY p.targetEndDate ASC", 
                Project.class
            );
            query.setParameter("days", daysAhead);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding projects ending soon: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findOverdueProjects() {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "FROM Project p WHERE p.targetEndDate < CURRENT_DATE " +
                "AND p.actualEndDate IS NULL " +
                "AND p.status NOT IN ('COMPLETED', 'CANCELLED') " +
                "ORDER BY p.targetEndDate ASC", 
                Project.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding overdue projects: " + e.getMessage(), e);
        }
    }
    
    public boolean existsByProjectKey(String projectKey) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Project p WHERE p.projectKey = :projectKey", 
                Long.class
            );
            query.setParameter("projectKey", projectKey);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking project key existence: " + e.getMessage(), e);
        }
    }
    
    public List<Project> findProjectsByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery(
                "SELECT DISTINCT p FROM Project p " +
                "LEFT JOIN p.team t " +
                "LEFT JOIN t.teamMembers tm " +
                "WHERE p.projectManager.userId = :userId " +
                "OR tm.user.userId = :userId " +
                "ORDER BY p.priority DESC, p.createdDate DESC", 
                Project.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding projects by user: " + e.getMessage(), e);
        }
    }
}