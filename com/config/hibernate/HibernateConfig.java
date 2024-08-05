package com.config.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.util.exception.DatabaseException;

/**
 *<p>
 *Provies configuration to session factory which manages database session and 
 *transaction
 *</p>
 *@author Deolin Jaffens
 */
 
public class HibernateConfig {
    private static SessionFactory factory = null;

    static {
		try {
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getFactory() {
		return factory;
	}
}