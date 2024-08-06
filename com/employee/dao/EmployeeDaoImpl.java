package com.employee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.util.exception.DatabaseException;
import com.config.hibernate.HibernateConfig;
import com.config.drivermanager.DriverManagerConfig;
import com.model.Department;
import com.model.Employee;

/**
 *Stores and retrives information related to employee from the
 *database
 *Implements the EmployeeDao interface
 *
 *@author Deolin Jaffens
 */

public class EmployeeDaoImpl implements EmployeeDao  {

    /**
     *Adds new employee details to the database
     *Links employee with department in the database
     *
     *@param employee - contains details of employee who is going to 
     *be added
     *@param department - contains details of department that the 
     *employee is going get linked
     *@throws DatabaseException
     */

    public void addEmployee(Employee employee) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        }
    }

    /**
     *Checks the avalability of specific employee details 
     *using id number and extracts it
     *
     *@param id - id of employee whose details are to be extracted
     *@return Employee
     *@throws DatabaseException
     */

    public Employee getEmployee(int id) throws DatabaseException {
		Session session = HibernateConfig.getFactory().openSession();
		try {
			return session.get(Employee.class, id);
		} catch (HibernateException e) {
           throw new DatabaseException("Error with database" + e);
        } finally {
            session.close();
        }
    }   

    /**
     *updates any Specific details of any employee
     *
     *@param employee - contains employee whose details has to be deleted
     *@throws DatabaseException
     */

    public void updateEmployee(Employee employee) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Employee existingEmployee = session.get(Employee.class, employee.getId());
            if (existingEmployee != null) {
                existingEmployee.setName(employee.getName());
                session.update(existingEmployee );
                transaction.commit();
            }
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
    *Restricts anyone from viewing details of a specific employee
    *
    *@param id - id of employee 
    *@throws DatabaseException
    */

   public void removeEmployee(int id) throws DatabaseException {
       Session session = HibernateConfig.getFactory().openSession();
         Transaction transaction = null;
         try {
             transaction = session.beginTransaction();
             Employee employee = session.get (Employee.class, id);
             if (employee != null) {
                 employee.setDelete(false);
                 session.update(employee);				 
                 transaction.commit();
             }
             else {
                 throw new DatabaseException("Database Error " + id);
             }
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
     *Extracts all the employees from the database
     *
     *@return Set<Employee>
     *@throws DatabaseException
     */
        
    public List<Employee> getEmployees() throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
        Transaction transaction = null;
        List<Employee> employees = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            employees = session.createQuery("from Employee where is_deleted = true", Employee.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Database Error " + e);
        } finally {
            session.close();
        }
        return employees;
    }   
}