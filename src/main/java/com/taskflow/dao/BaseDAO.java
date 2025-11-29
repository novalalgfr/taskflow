package com.taskflow.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T, ID extends Serializable> {
    
    private final Class<T> entityClass;
    protected SessionFactory sessionFactory;
    
    public BaseDAO(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }
    
    public T save(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error saving entity: " + e.getMessage(), e);
        }
    }
    
    public T update(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error updating entity: " + e.getMessage(), e);
        }
    }
    
    public T saveOrUpdate(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error saving/updating entity: " + e.getMessage(), e);
        }
    }
    
    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            T entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding entity by ID: " + e.getMessage(), e);
        }
    }
    
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery("FROM " + entityClass.getName(), entityClass);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all entities: " + e.getMessage(), e);
        }
    }
    
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        }
    }
    
    public void deleteById(ID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error deleting entity by ID: " + e.getMessage(), e);
        }
    }
    
    public long count() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(e) FROM " + entityClass.getName() + " e", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error counting entities: " + e.getMessage(), e);
        }
    }
    
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }
}