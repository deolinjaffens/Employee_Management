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

import com.helper.Manager;
import com.exception.DatabaseException;
import com.model.Department;
import com.model.Employee;

/**
 *Stores and retrives information related to employee from the
 *database
 *Implements the EmployeeDao interface
 *
 *@author Deolin Jaffens
 */

public interface EmployeeDao {

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

    public void addEmployee(Employee employee) throws DatabaseException;
   
    /**
     *Checks the avalability of specific employee details 
     *using id number and extracts it
     *
     *@param id - id of employee whose details are to be extracted
     *@return Employee
     *@throws DatabaseException
     */

    public Employee getEmployee(int id) throws DatabaseException;

    /**
     *Extracts the department details from the database
     *
     *@param id - id of the department whose details has to be extracted
     *@param connection - connects the driver to the function
     *@return department
     *@throws DatabaseException
     */

    public Department getDepartment(int id, Connection connection) throws DatabaseException;

    /**
     *updates any Specific details of any employee
     *
     *@param employee - contains employee whose details has to be deleted
     *@throws DatabaseException
     */

    public void updateEmployee(Employee employee) throws DatabaseException;

    /**
     *Restricts anyone from viewing details of a specific employee
     *
     *@param id - id of employee 
     *@throws DatabaseException
     */

    public void removeEmployee(int id) throws DatabaseException;

    /**
     *Extracts all the employees from the database
     *
     *@return Set<Employee>
     *@throws DatabaseException
     */

    public List<Employee> getEmployees() throws DatabaseException;

}