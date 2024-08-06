package com.department.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Department;
import com.util.exception.DatabaseException;
import com.config.hibernate.HibernateConfig;
import com.config.drivermanager.DriverManagerConfig;
import com.model.Employee;

/**
 *<p>
 *Stores and retrives informations from the database
 *Implements the DepartmentDao interface 
 *</p>
 *@author Deolin Jaffens
 */

public class DepartmentDaoImpl implements DepartmentDao {

    private static SessionFactory factory;

    public void addDepartment(Department department) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(department);
            transaction.commit();
        } catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        }
     }

    public List<Department> getAllDepartments() throws DatabaseException {
		Session session = HibernateConfig.getFactory().openSession();
        Transaction transaction = null;
        List<Department> departments = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            departments = session.createQuery("from Department",Department.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Department Error" + e);
        } finally {
            session.close();
        }
        return departments;
    }
	 
	public Department getDepartment(int id) throws DatabaseException{
        Session session = HibernateConfig.getFactory().openSession();
        try { 
            return session.get(Department.class, id);
        }catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        } 
    }		
	 
    public void updateDepartment(int id, String name) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
		Transaction transaction = null;
        try { 
		    transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
			department.setName(name);
			session.update(department);
			transaction.commit();
        }catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        }
    }

    public void removeDepartment(int id)  throws DatabaseException {
         Session session = HibernateConfig.getFactory().openSession();
         Transaction transaction = null;
         try {
             transaction = session.beginTransaction();
             Department department = session.get (Department.class, id);
             session.delete(department);             
             transaction.commit();
         } catch (HibernateException e) {
             if(transaction != null) {
                transaction.rollback();
            }
             throw new DatabaseException("Database Error  " + e);
             
         } finally {
             session.close();
        }
    }

    public boolean checkForDepartment() throws DatabaseException {
        int count = 0;
        for(Department department : getAllDepartments()) {
            count++;
        }
        return !(count == 0);
    }
}