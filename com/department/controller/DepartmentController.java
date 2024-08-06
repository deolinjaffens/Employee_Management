package com.department.controller;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.model.Department;
import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.employee.controller.EmployeeController;
import com.util.exception.DatabaseException;
import com.model.Employee;

/**
 *<p>
 *Creation,Updation,removal and viewing of  department is done.
 *Initialisation and extraction of details related to department are given and 
 *obtained
 *</p>
 *@author Deolin Jaffens
 */  

public class DepartmentController {
	
	private static Logger logger = LogManager.getLogger(DepartmentController.class);
    DepartmentService departmentService = new DepartmentServiceImpl(); 
    EmployeeController employeeController = new EmployeeController();
    Scanner scanner = new Scanner(System.in);
    
    /**
	 *<p>
     *Initialises and extracts department details for adding, viewing, 
     *editing and removal of the department
     *</p>
     */

    public void menu() {
        boolean exit = false;
        while(!exit) {
            System.out.println("(1) Add Department");
            System.out.println("(2) View AllDepartment");
            System.out.println("(3) Update Department");
            System.out.println("(4) Remove Department");
            System.out.println("(5) View all employees for the department");
            System.out.println("(6) Exit");
            System.out.println(" ");
            System.out.print("Enter an option : ");
            int option = scanner.nextInt();
            switch(option) {
                case 1:
                    addDepartment();
                    break;

                case 2:
                    viewAllDepartments();
                    break;

                case 3:
                    updateDepartment();
                    break;

  		        case 4: 
                    removeDepartment();
		            break;

                case 5:
                    viewAllEmployees();
                    break;

                case 6:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid Option!!");
            } 
        }
        System.out.println("=================Back to Main Menu======================");
    }

    /**
	 *<p>
     *Gets all the required details of the department that has to be added to the 
	 *database
	 *</p>
     */

    public void addDepartment() {
        try {
            System.out.print("Enter Department Name : ");
            String name = scanner.next();
            departmentService.addDepartment(name);
            System.out.println("=============Department Added=============");
        } catch (DatabaseException e) {
            logger.error("Failed to add department");
        } catch (InputMismatchException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
	 *<p>
     *All departments which are extraced from the database are displayed
	 *</p>
     */

    public void viewAllDepartments() {
        try {
            System.out.printf("|%-5s|%-10s|\n","Department Id","Department Name");
            for (Department department : departmentService.getAllDepartments()) {
                System.out.printf("|%-13s|%-15s|\n",department.getId(),department.getName());
            }
        } catch (DatabaseException e) {
            logger.error("Failed to fetch all departments");
        }
    }
    
    /**
	 *<p>
     *Gets all the details that has to be updated for a specific department
	 *</p>
     */

    public void updateDepartment() {
        try {
			System.out.println("Enter the id of department that has to be updated");
            int id = scanner.nextInt();
            System.out.print("Enter the name to be updated");
            String name = scanner.next();    
            departmentService.updateDepartment(id, name); 
            System.out.println("=============Department Updated=============");
        } catch (DatabaseException e) {
            logger.error("Failed to update department details");
        } catch (InputMismatchException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     *<p>
	 *Gets the id of a specific department which has to be removed from the database
	 *</p>
     */

    public void removeDepartment() {
        try {
            System.out.print("Enter Department Id : ");
            int id = scanner.nextInt();
            departmentService.removeDepartment(id);
      	    System.out.println("=============Department Deleted=============");
        } catch (DatabaseException e) {
            logger.error("Failed to add Department");
        } catch (InputMismatchException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
	 *<p>
     *All employees who belongs to a specific department is displayed
	 *</p>
     */
     
    public void viewAllEmployees() {
        try {
            System.out.print("Enter Department Id : ");
            int id = scanner.nextInt();
            for(Employee employee : departmentService.getDepartment(id).getEmployees()) {
                    System.out.println(employee.getId() + " - " + employee.getName());
            }
        } catch(DatabaseException e) {
            logger.error("Failed to fetch all employees");
        }
    }
}