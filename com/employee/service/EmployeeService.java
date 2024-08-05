package com.employee.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.model.Department;
import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.employee.dao.EmployeeDao;
import com.employee.dao.EmployeeDaoImpl;
import com.exception.DatabaseException;
import com.model.Employee;

/**
 *Implements the EmployeeService interface 
 *Connects Controller to the dao
 *
 *@author Deolin Jaffens
 */

public interface EmployeeService {

    /**
     *Adds the new employee to the specified department
     *The new employee is passed to the Dao
     *
     *@param name - name of the employee to be added
     *@param dob - date of birth of the employee
     *@param departmentName - name of the department to which the employee has joined
     *@param departmentId - id of the department to which the employee has joined
     *@param id - id that has to be provided to the employee
     *@param gender - gender of the employee
     *@param PhNum - phone number of the employee
     *@param salary - salary of the employee
     *@throws DatabaseException 
     */

     public void addEmployee(String name, LocalDate dob, Department department, char gender, long phNum, double salary) throws DatabaseException; 
     
     /**
      *Checks wheather the details of the specific employee  
      *should be viewed
      *
      *@param id - id of employee to be checked and extracted 
      *@return Employee
      *@throws DatabaseException
      */
     public Employee getEmployee(int id) throws DatabaseException ;

     /**
      *Transfers the employee details that has to be updated to the dao
      *
      *@param employee - contains details of the employee to be updated
      *@throws DatabaseException
      */

     public void updateEmployee(Employee employee) throws DatabaseException;

     /**
      *Transfers the details of the employee to be removed
      *
      *@param id - id of the employee to be removed
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
	 
	 /** 
	 *<p>
	 *Check for the availability of any department in the database
	 *</p>
	 *return boolean
	 *throws DatabaseException
	 */
	 
	 public boolean isEmpty() throws DatabaseException;
	 
	 /**
	  *<p>
	  *Extracts all the department from the database with the help of the service
	  *layer in department
	  *</p>
	  *return List<Department>
	  *throws DatabaseException
	  */
	  
	  public List<Department> getAllDepartments() throws DatabaseException;
}