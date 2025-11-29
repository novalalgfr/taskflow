package com.taskflow.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Configuration Manager
 * Singleton pattern untuk SessionFactory
 */
public class HibernateConfig {
    private static SessionFactory sessionFactory;
    
    // Private constructor untuk prevent instantiation
    private HibernateConfig() {}
    
    /**
     * Get Hibernate SessionFactory (singleton)
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create SessionFactory dari hibernate.cfg.xml
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                
                sessionFactory = configuration.buildSessionFactory();
                
                System.out.println("✅ Hibernate SessionFactory created successfully!");
            } catch (Exception e) {
                System.err.println("❌ Error creating SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
    /**
     * Close SessionFactory (dipanggil saat aplikasi shutdown)
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("✅ Hibernate SessionFactory closed.");
        }
    }
}