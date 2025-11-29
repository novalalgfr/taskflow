package com.taskflow.dao;

import com.taskflow.entity.Issue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class IssueDAO extends BaseDAO<Issue, Integer> {
    
    public IssueDAO(SessionFactory sessionFactory) {
        super(Issue.class, sessionFactory);
    }

    public List<Issue> findByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId ORDER BY i.createdDate DESC",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by project: " + e.getMessage(), e);
        }
    }

    public List<Issue> findByProjectIdAndStatus(Integer projectId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId AND i.status = :status",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by project and status: " + e.getMessage(), e);
        }
    }

    public List<Issue> findBySprintId(Integer sprintId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.sprint.sprintId = :sprintId",
                Issue.class
            );
            query.setParameter("sprintId", sprintId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by sprint: " + e.getMessage(), e);
        }
    }

    public List<Issue> findBacklogByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId AND i.sprint IS NULL",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding backlog issues: " + e.getMessage(), e);
        }
    }

    public List<Issue> findByAssigneeId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.assignee.userId = :userId ORDER BY i.priority DESC, i.dueDate ASC",
                Issue.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by assignee: " + e.getMessage(), e);
        }
    }

    public List<Issue> findByAssigneeIdAndStatus(Integer userId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.assignee.userId = :userId AND i.status = :status",
                Issue.class
            );
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by assignee and status: " + e.getMessage(), e);
        }
    }

    public List<Issue> findUnassignedByProjectId(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId AND i.assignee IS NULL",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding unassigned issues: " + e.getMessage(), e);
        }
    }

    public List<Issue> findByReporterId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.reporter.userId = :userId ORDER BY i.createdDate DESC",
                Issue.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding issues by reporter: " + e.getMessage(), e);
        }
    }

    public List<Issue> searchByKeyword(Integer projectId, String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId " +
                "AND (LOWER(i.title) LIKE LOWER(:keyword) OR LOWER(i.description) LIKE LOWER(:keyword))",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching issues: " + e.getMessage(), e);
        }
    }

    public Optional<Issue> findByIssueKey(String issueKey) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.issueKey = :issueKey",
                Issue.class
            );
            query.setParameter("issueKey", issueKey);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding issue by key: " + e.getMessage(), e);
        }
    }

    public List<Issue> filterIssues(Integer projectId, String status, String priority,
                                    String issueType, Integer assigneeId) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Issue i WHERE i.project.projectId = :projectId");

            if (status != null && !status.isEmpty()) {
                hql.append(" AND i.status = :status");
            }
            if (priority != null && !priority.isEmpty()) {
                hql.append(" AND i.priority = :priority");
            }
            if (issueType != null && !issueType.isEmpty()) {
                hql.append(" AND i.issueType = :issueType");
            }
            if (assigneeId != null) {
                hql.append(" AND i.assignee.userId = :assigneeId");
            }

            Query<Issue> query = session.createQuery(hql.toString(), Issue.class);
            query.setParameter("projectId", projectId);

            if (status != null && !status.isEmpty()) {
                query.setParameter("status", status);
            }
            if (priority != null && !priority.isEmpty()) {
                query.setParameter("priority", priority);
            }
            if (issueType != null && !issueType.isEmpty()) {
                query.setParameter("issueType", issueType);
            }
            if (assigneeId != null) {
                query.setParameter("assigneeId", assigneeId);
            }

            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error filtering issues: " + e.getMessage(), e);
        }
    }

    public Long countByProjectIdAndStatus(Integer projectId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(i) FROM Issue i WHERE i.project.projectId = :projectId AND i.status = :status",
                Long.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("status", status);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting issues by status: " + e.getMessage(), e);
        }
    }

    public Long countByProjectIdAndPriority(Integer projectId, String priority) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(i) FROM Issue i WHERE i.project.projectId = :projectId AND i.priority = :priority",
                Long.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("priority", priority);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting issues by priority: " + e.getMessage(), e);
        }
    }

    public List<Issue> findUpcomingDeadlines(Integer projectId, int daysAhead) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId " +
                "AND i.dueDate IS NOT NULL " +
                "AND i.dueDate BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, :days) " +
                "AND i.status NOT IN ('DONE', 'CLOSED') " +
                "ORDER BY i.dueDate ASC",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            query.setParameter("days", daysAhead);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding upcoming deadlines: " + e.getMessage(), e);
        }
    }

    public List<Issue> findOverdueIssues(Integer projectId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.project.projectId = :projectId " +
                "AND i.dueDate < CURRENT_DATE " +
                "AND i.status NOT IN ('DONE', 'CLOSED') " +
                "ORDER BY i.dueDate ASC",
                Issue.class
            );
            query.setParameter("projectId", projectId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding overdue issues: " + e.getMessage(), e);
        }
    }

    public List<Issue> findSubTasks(Integer parentIssueId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Issue> query = session.createQuery(
                "FROM Issue i WHERE i.parentIssue.issueId = :parentIssueId",
                Issue.class
            );
            query.setParameter("parentIssueId", parentIssueId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding sub-tasks: " + e.getMessage(), e);
        }
    }

    public String generateNextIssueKey(String projectKey) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(i) FROM Issue i WHERE i.issueKey LIKE :pattern",
                Long.class
            );
            query.setParameter("pattern", projectKey + "-%");
            Long count = query.uniqueResult();
            return projectKey + "-" + (count + 1);
        } catch (Exception e) {
            throw new RuntimeException("Error generating issue key: " + e.getMessage(), e);
        }
    }
}
