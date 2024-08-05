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
import com.exception.DatabaseException;
import com.helper.HibernateConnection;
import com.helper.Manager;
import com.model.Employee;

/**
 *Stores and retrives informations from the database
 *Implements the DepartmentDao interface 
 *
 *@author Deolin Jaffens
 */

public class DepartmentDaoImpl implements DepartmentDao {

    private static SessionFactory factory;

    /**
     *Stores new department details in the database
     *
     *@param department - contain the department details to be added
     *@throws DatabaseException
     */

    public void addDepartment(Department department) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
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

     /**
     *Extracts department from the Set departmentData
     *
     *@param departments - Extracts every department details and stores it
     *@return Set<Department>
     *@throws DatabaseException
     */    

    public List<Department> getAllDepartments() throws DatabaseException {
		Session session = HibernateConnection.getFactory().openSession();
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

    /**
	 *<p>
	 *Extracts a specific department from the database
	 *</p>
	 *@param id - id of the department
	 *@return Department
	 *@throws DatabaseException
	 */
	 
	public Department getDepartment(int id) throws DatabaseException{
        Session session = HibernateConnection.getFactory().openSession();
        try { 
            return session.get(Department.class, id);
        }catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        } 
    }		
	 
     /**
     *Updates specific details of department in the database
     *
     *@param id - id of the department
     *@param name - name that has to be updated
     *@throws DatabaseException
     */

    public void updateDepartment(int id, String name) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
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


    /**
     *A specific Department will be converted in a way that it 
     *will not be displayed in the controller
     *
     *@param id - id of the department which should be hid
     *@throws DatabaseException
     */    

    public void removeDepartment(int id)  throws DatabaseException {
         Session session = HibernateConnection.getFactory().openSession();
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

    /**
     *Check wheather the database is empty
     *
     *@return boolean
     *@throws DatabaseException
     */

    public boolean checkForDepartment() throws DatabaseException {
        int count = 0;
        for(Department department : getAllDepartments()) {
            count++;
        }
        return !(count == 0);
    }
}