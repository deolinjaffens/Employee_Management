package com.employee.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import com.util.exception.DatabaseException;
import com.model.Department;
import com.model.Employee;
import com.model.Skill;
import com.skill.controller.SkillController;
import com.util.validator.Validator;

/**
 *<p>
 *Creation,Updation,removal and viewing of employee data is done
 *Initialisation and extraction of datas related to employee are given and obtained
 *</p>
 *@Deolin Jaffens
 */

public class EmployeeController {
    
	private static Logger logger = LogManager.getLogger(EmployeeController.class);
    EmployeeService employeeService = new EmployeeServiceImpl();
    DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    Scanner scanner = new Scanner(System.in);
    
    /**
	 *<p>
     *Creation,edition,visualisation and removal of details related 
     *to employee is done
     *</p>
     *@param department - department to which employee is associated
     */

    public void menu(){
	    boolean flag = false;
		try {
	        if ((employeeService.isEmpty()) == true) {
                System.out.printf("|%-5s|%-10s|\n","Department Id","Department Name");
                for(Department department : employeeService.getAllDepartments()) {
                    System.out.printf("|%-13s|%-15s|\n",department.getId(), department.getName());
                }
                System.out.print("Enter the department Id : ");
                int id = scanner.nextInt();
                Department department = employeeService.getDepartment(id);
                
	            while(!flag) {
	                System.out.println(" ");
	                System.out.println("(1) Add an employee");
	                System.out.println("(2) View an employee");
	                System.out.println("(3) Update an employee");
	                System.out.println("(4) Delete an employee");
	                System.out.println("(5) Exit");
	                System.out.println(" ");
	                System.out.print("Enter an option : ");
	                int option = scanner.nextInt();
                    System.out.println(" ");

	                switch(option) {

		                case 1:
                            addEmployee(department);
		                    break;

		                case 2:
                            viewEmployee();
                            break;

		                case 3:
                            updateEmployee();
		                    break;

		                case 4: 
                            removeEmployee();
		                    break;	
    		            case 5:
	    	                flag = true;
		                    break;

		                default:
		                    System.out.println("Invalid option!!");    
					}
                }				
	        } else {
                System.out.println("No department Found");
            }
            System.out.println("=============Back to Main Menu=============");
		} catch(DatabaseException e) {
			logger.error(e.getMessage());
		} catch(InputMismatchException e) {
		    logger.warn(e.getMessage());
		}
    }

    /**
	 *<p>
     *To add the details of a specific employee
     *</p>
     *@param department - department to which the employee has to be added
     */

    public void addEmployee(Department department) {
        try {
            System.out.print("Enter name: ");
	        String name = scanner.next();
            if(!Validator.isValidName(name)) {
                System.out.println("Invalid name");
                return;
            }
	        System.out.print("Enter date of birth(dd-mm-yyyy): ");
            String getDob = scanner.next();
	        //LocalDate addDob = null;
            LocalDate addDob = LocalDate.parse(getDob,datetimeformatter);
	        System.out.print("Enter Male(M) or Female(F): ");
	        char addGender = scanner.next().charAt(0);
            if(!Validator.isValidGender(addGender)) {
                System.out.println("Enter valid gender");
                return;
            }
	        System.out.print("Enter mobile number: ");
	        long addPhNum = scanner.nextLong();
            if(!Validator.isValidNumber(addPhNum)) {
                System.out.println("Invalid Phone Number");
                return;
            }
	        System.out.print("Enter salary: ");
	        double addSalary = scanner.nextDouble();
            employeeService.addEmployee(name, addDob, department, addGender, addPhNum, addSalary);
            System.out.println("=============Employee Added=============");
        } catch(IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return;
        } catch(InputMismatchException e) {
            logger.warn(e.getMessage());
            return;
        } catch(DatabaseException e) {
            logger.error("Failed to add employee");
        } catch(DateTimeParseException e){
            logger.warn(e.getMessage());
            return;
        } 
    } 

    /**
	 *<p>
     *Displays all the details related to a specific employee
	 *<p>
     */

    public void viewEmployee() {
        try {
            System.out.print("Enter Id Number : ");
            int id = scanner.nextInt();
            Employee employee = employeeService.getEmployee(id);
            System.out.println(employee.getName());
            System.out.println("Name : "+employee.getName());
	        System.out.println("Department : "+employee.getDepartment().getName());    
      	    System.out.println("Dob : "+employee.getDob());
            System.out.println("Age : "+employee.getAge());
	        System.out.println("Gender : "+employee.getGender());
	        System.out.println("Mob. Number : "+employee.getPhoneNumber());
	        System.out.println("Salary : "+employee.getSalary());
        } catch(IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch(DatabaseException e) {
            logger.error("Failed to fetch employee");
        } catch(InputMismatchException e) {
            logger.warn(e.getMessage());
            return;
        }
    }

     /**
	  *<p>
      *Updates any specific details related to a specific employee
	  *<p>
      */

    public void updateEmployee() {
        try {
            System.out.print("Enter Employee Id : ");
	    int id = scanner.nextInt();
            Employee employee = employeeService.getEmployee(id);
            System.out.print("Name (");
            System.out.print(employee.getName());
            System.out.print(") :");
            String name = scanner.next();
            if(!Validator.isValidName(name)) {
                System.out.println("Invalid name");
                return;
            }
            employee.setName(name);
            System.out.print("Date of birth (");
            System.out.print(employee.getDob());
            System.out.print(") :");
            String stringDob = scanner.next();
            LocalDate dob = LocalDate.parse(stringDob,datetimeformatter);
            employee.setDob(dob);
            System.out.print("Gender (");
            System.out.print(employee.getGender());
            System.out.print(") :");
            char gender = scanner.next().charAt(0);
            if(!Validator.isValidGender(gender)) {
                System.out.println("Enter valid gender");
            }
            employee.setGender(gender);
            System.out.print("Ph.No. (");
            System.out.print(employee.getPhoneNumber());
            System.out.print(") :");
            Long updatePhNum = scanner.nextLong();
            if(!Validator.isValidNumber(updatePhNum)) {
                System.out.println("Invalid Phone Number");
            }
            employee.setPhoneNumber(updatePhNum);
            System.out.print("Salary (");
            System.out.print(employee.getSalary());
            System.out.print(") :");
            double salary = scanner.nextDouble();
            employee.setSalary(salary);
            employeeService.updateEmployee(employee);
            System.out.println("=============Employee Updated=============");
        } catch(IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch(DatabaseException e) {
            logger.error("Failed to update employee details");
        } catch(InputMismatchException e) {
            logger.warn(e.getMessage());
            return;
        } catch(DateTimeParseException e) {
            logger.warn(e.getMessage());
            return;
        }
    }

    /**
	 *<p>
     *Get the id of the employee whose details has to be removed
	 *</p>
     */

    public void removeEmployee() {
        try {
            System.out.print("Enter Employee Id : ");
            int id = scanner.nextInt();
            employeeService.removeEmployee(id);
        } catch(IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch(DatabaseException e) {
            logger.error("Failed to remove employee from the database");
        } catch(InputMismatchException e) {
            logger.warn(e.getMessage());
            return;
        }
        System.out.println("=============Employee Deleted=============");
    }
   
}