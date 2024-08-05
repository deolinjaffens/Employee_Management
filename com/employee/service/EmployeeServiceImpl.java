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

public class EmployeeServiceImpl implements EmployeeService {
    
    EmployeeDao employeeDao = new EmployeeDaoImpl();
	DepartmentService departmentService = new DepartmentServiceImpl();

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

    public void addEmployee(String name, LocalDate dob, Department department, char gender, long phNum, double salary) throws DatabaseException{
        Employee employee = new Employee(name, dob, gender, phNum, salary, department);
        employeeDao.addEmployee(employee);
    }

    /**
     *Checks wheather the details of the specific employee  
     *should be viewed
     *
     *@param id - id of employee to be checked and extracted 
     *@return Employee
     *@throws DatabaseException
     */

    public Employee getEmployee(int id) throws DatabaseException {
        if(employeeDao.getEmployee(id).getDelete() == true) {
            return employeeDao.getEmployee(id);
        }
        return null;
    }

    /**
     *Transfers the employee details that has to be updated to the dao
     *
     *@param employee - contains details of the employee to be updated
     *@throws DatabaseException
     */

    public void updateEmployee(Employee employee) throws DatabaseException {
        employeeDao.updateEmployee(employee);
    }

    /**
     *Transfers the details of the employee to be removed
     *
     *@param id - id of the employee to be removed
     *@throws DatabaseException
     */
    public void removeEmployee(int id) throws DatabaseException {
        employeeDao.removeEmployee(id) ;
    }

    /**
     *Extracts all the employees from the database
     *
     *@return Set<Employee>
     *@throws DatabaseException
     */

    public List<Employee> getEmployees() throws DatabaseException{
        return employeeDao.getEmployees();
    }
	
	/** 
	 *<p>
	 *Check for the availability of any department
	 *</p>
	 *return boolean
	 *throws DatabaseException
	 */
	 
	 public boolean isEmpty() throws DatabaseException {
		 return departmentService.isEmpty();
	 }
	 
	 /**
	  *<p>
	  *Extracts all the department from the database with the help of the service
	  *layer in department
	  *</p>
	  *return List<Department>
	  *throws DatabaseException
	  */
	  
	  public List<Department> getAllDepartments() throws DatabaseException {
		  return departmentService.getAllDepartments();
	  }
}