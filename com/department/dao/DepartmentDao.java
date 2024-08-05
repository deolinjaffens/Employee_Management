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
import com.helper.Manager;
import com.model.Employee;

/**
 *Stores and retrives informations from the database
 *
 *@author Deolin Jaffens
 */

public interface DepartmentDao {

    /**
     *Stores new department details in the database
     *
     *@param department - contain the department details to be added
     *@throws DatabaseException
     */

    public void addDepartment(Department department) throws DatabaseException;


    /**
     *Extracts department from the Set departmentData
     *
     *@param departments - Extracts every department details and stores it
     *@return Set<Department>
     *@throws DatabaseException
     */    

    public List<Department> getAllDepartments() throws DatabaseException;
	
	/**
	 *<p>
	 *Extracts a specific department from the database
	 *</p>
	 *@param id - id of the department
	 *@return Department
	 *@throws DatabaseException
	 */
	 
	public Department getDepartment(int id) throws DatabaseException;

    /**
     *Updates specific details of department in the database
     *
     *@param id - id of the department
     *@param name - name that has to be updated
     *@throws DatabaseException
     */

    public void updateDepartment(int id, String name) throws DatabaseException;

    /**
     *A specific Department will be converted in a way that it 
     *will not be displayed in the controller
     *
     *@param id - id of the department which should be hid
     *@throws DatabaseException
     */

    public void removeDepartment(int id)  throws DatabaseException;

    /**
     *Check wheather the Set is empty
     *
     *@return boolean
     *@throws DatabaseException
     */

    public boolean checkForDepartment() throws DatabaseException;

} 