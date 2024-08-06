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
import com.config.drivermanager.DriverManagerConfig;
import com.model.Employee;

/**
 *<p>
 *Stores and retrives informations from the database
 *</p>
 *@author Deolin Jaffens
 */

public interface DepartmentDao {

    /**
	 *<p>
     *Stores new department details in the database
     *</p>
     *@param department - contain the department details to be added
     *@throws DatabaseException
     */

    public void addDepartment(Department department) throws DatabaseException;


    /**
	 *<p>
     *Extracts department from the Set departmentData
     *</p>
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
	 *<p>
     *Updates specific details of department in the database
     *</p>
     *@param id - id of the department
     *@param name - name that has to be updated
     *@throws DatabaseException
     */

    public void updateDepartment(int id, String name) throws DatabaseException;

    /**
	 *<p>
     *Removes a specific department from the database
     *</p>
     *@param id - id of the department which should be hid
     *@throws DatabaseException
     */

    public void removeDepartment(int id)  throws DatabaseException;

    /**
	 *<p>
     *Check wheather the Set is empty
     *</p>
     *@return boolean
     *@throws DatabaseException
     */

    public boolean checkForDepartment() throws DatabaseException;

} 