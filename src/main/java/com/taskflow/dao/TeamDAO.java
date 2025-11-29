package com.taskflow.dao;

import com.taskflow.entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TeamDAO extends BaseDAO<Team, Integer> {
    
    public TeamDAO(SessionFactory sessionFactory) {
        super(Team.class, sessionFactory);
    }
    
    public Optional<Team> findByTeamName(String teamName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "FROM Team t WHERE t.teamName = :teamName",
                Team.class
            );
            query.setParameter("teamName", teamName);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding team by name: " + e.getMessage(), e);
        }
    }
    
    public List<Team> findByTeamLeadId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "FROM Team t WHERE t.teamLead.userId = :userId ORDER BY t.teamName",
                Team.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding teams by team lead: " + e.getMessage(), e);
        }
    }
    
    public List<Team> findTeamsByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "SELECT DISTINCT t FROM Team t " +
                "LEFT JOIN t.teamMembers tm " +
                "WHERE t.teamLead.userId = :userId OR tm.user.userId = :userId " +
                "ORDER BY t.teamName",
                Team.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding teams by user: " + e.getMessage(), e);
        }
    }
    
    public List<Team> searchByName(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "FROM Team t WHERE LOWER(t.teamName) LIKE LOWER(:keyword) ORDER BY t.teamName",
                Team.class
            );
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching teams: " + e.getMessage(), e);
        }
    }
    
    public Long countMembers(Integer teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(tm) FROM TeamMember tm WHERE tm.team.teamId = :teamId",
                Long.class
            );
            query.setParameter("teamId", teamId);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting team members: " + e.getMessage(), e);
        }
    }
    
    public Long countProjects(Integer teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Project p WHERE p.team.teamId = :teamId",
                Long.class
            );
            query.setParameter("teamId", teamId);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting team projects: " + e.getMessage(), e);
        }
    }
    
    public boolean existsByTeamName(String teamName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(t) FROM Team t WHERE t.teamName = :teamName",
                Long.class
            );
            query.setParameter("teamName", teamName);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking team name existence: " + e.getMessage(), e);
        }
    }
    
    public boolean isUserInTeam(Integer teamId, Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(tm) FROM TeamMember tm " +
                "WHERE tm.team.teamId = :teamId AND tm.user.userId = :userId",
                Long.class
            );
            query.setParameter("teamId", teamId);
            query.setParameter("userId", userId);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking user in team: " + e.getMessage(), e);
        }
    }
    
    public List<Team> findAllWithMemberCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "SELECT DISTINCT t FROM Team t " +
                "LEFT JOIN FETCH t.teamMembers " +
                "ORDER BY t.teamName",
                Team.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding teams with member count: " + e.getMessage(), e);
        }
    }
    
    public Optional<Team> findByIdWithMembers(Integer teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "FROM Team t " +
                "LEFT JOIN FETCH t.teamMembers tm " +
                "LEFT JOIN FETCH tm.user " +
                "WHERE t.teamId = :teamId",
                Team.class
            );
            query.setParameter("teamId", teamId);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Error finding team with members: " + e.getMessage(), e);
        }
    }
    
    public List<Team> findTeamsWithoutLead() {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "FROM Team t WHERE t.teamLead IS NULL ORDER BY t.teamName",
                Team.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding teams without lead: " + e.getMessage(), e);
        }
    }
    
    public List<Team> findActiveTeams() {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery(
                "SELECT DISTINCT t FROM Team t " +
                "JOIN t.projects p " +
                "WHERE p.status IN ('PLANNING', 'ACTIVE') " +
                "ORDER BY t.teamName",
                Team.class
            );
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding active teams: " + e.getMessage(), e);
        }
    }
}