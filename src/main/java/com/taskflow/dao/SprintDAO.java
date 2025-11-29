package com.taskflow.dao;

import com.taskflow.entity.Sprint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SprintDAO extends BaseDAO<Sprint, Integer> {
    
    public SprintDAO(SessionFactory sessionFactory) {
        super(Sprint.class, sessionFactory);
    }
    
    public List<Sprint> findByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId ORDER BY s.startDate DESC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding sprints by project: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findByProjectIdAndStatus(Integer projectId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId AND s.status = :status ORDER BY s.startDate DESC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding sprints by project and status: " + e.getMessage(), e);
        }
    }
    
    public Optional<Sprint> findActiveSprintByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId AND s.status = 'ACTIVE'",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            query.setMaxResults(1);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding active sprint: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findUpcomingSprints(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId " +
                "AND s.status = 'NOT_STARTED' " +
                "AND s.startDate >= CURRENT_DATE " +
                "ORDER BY s.startDate ASC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding upcoming sprints: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findCompletedSprints(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId " +
                "AND s.status = 'COMPLETED' " +
                "ORDER BY s.endDate DESC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding completed sprints: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findSprintsByDateRange(Integer projectId, LocalDate startDate, LocalDate endDate) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId " +
                "AND ((s.startDate BETWEEN :startDate AND :endDate) " +
                "OR (s.endDate BETWEEN :startDate AND :endDate))",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding sprints by date range: " + e.getMessage(), e);
        }
    }
    
    public Long countByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(s) FROM Sprint s WHERE s.project.projectId = :projectId",
                Long.class
            );
            query.setParameter("projectId", projectId);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting sprints: " + e.getMessage(), e);
        }
    }
    
    public Long countByProjectIdAndStatus(Integer projectId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(s) FROM Sprint s WHERE s.project.projectId = :projectId AND s.status = :status",
                Long.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("status", status);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting sprints by status: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> searchByName(Integer projectId, String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId " +
                "AND LOWER(s.sprintName) LIKE LOWER(:keyword) " +
                "ORDER BY s.startDate DESC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching sprints: " + e.getMessage(), e);
        }
    }
    
    public boolean hasOverlappingSprint(Integer projectId, LocalDate startDate, LocalDate endDate, Integer excludeSprintId) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder(
                "SELECT COUNT(s) FROM Sprint s WHERE s.project.projectId = :projectId " +
                "AND s.status != 'COMPLETED' " +
                "AND ((s.startDate BETWEEN :startDate AND :endDate) " +
                "OR (s.endDate BETWEEN :startDate AND :endDate) " +
                "OR (:startDate BETWEEN s.startDate AND s.endDate))"
            );
            
            if (excludeSprintId != null) {
                hql.append(" AND s.sprintId != :excludeSprintId");
            }
            
            Query<Long> query = session.createQuery(hql.toString(), Long.class);
            query.setParameter("projectId", projectId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            
            if (excludeSprintId != null) {
                query.setParameter("excludeSprintId", excludeSprintId);
            }
            
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking overlapping sprints: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findOverdueSprints() {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.endDate < CURRENT_DATE " +
                "AND s.status = 'ACTIVE' " +
                "ORDER BY s.endDate ASC",
                Sprint.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding overdue sprints: " + e.getMessage(), e);
        }
    }
    
    public List<Sprint> findSprintsEndingSoon(int daysAhead) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.endDate BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, :days) " +
                "AND s.status = 'ACTIVE' " +
                "ORDER BY s.endDate ASC",
                Sprint.class
            );
            query.setParameter("days", daysAhead);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding sprints ending soon: " + e.getMessage(), e);
        }
    }
    
    public Optional<Sprint> findLatestSprintByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery(
                "FROM Sprint s WHERE s.project.projectId = :projectId " +
                "ORDER BY s.createdDate DESC",
                Sprint.class
            );
            query.setParameter("projectId", projectId);
            query.setMaxResults(1);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding latest sprint: " + e.getMessage(), e);
        }
    }
}