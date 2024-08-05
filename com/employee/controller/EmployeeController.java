package com.employee.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import com.exception.DatabaseException;
import com.model.Department;
import com.model.Employee;
import com.model.Skill;
import com.skill.controller.SkillController;
import com.validator.Validator;

/**
 *<p>
 *Creation,Updation,removal and viewing of employee data is done
 *Initialisation and extraction of datas related to employee are given and obtained
 *</p>
 *@Deolin Jaffens
 */

public class EmployeeController {
    
    EmployeeService employeeService = new EmployeeServiceImpl();
    SkillController skillController = new SkillController();
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
                for(Department department : employeeService.getAllDepartments()){
                    if((department.getId()) == id) {
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
                    }
				    break;
                }				
	        } else {
                System.out.println("No department Found");
            }
            System.out.println("=============Back to Main Menu=============");
		} catch(DatabaseException e) {
			System.out.println("Database Error " + e);
		} catch(InputMismatchException e) {
		    System.out.println("Enter a valid option");
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
	        LocalDate addDob = null;
            try {
                addDob = LocalDate.parse(getDob,datetimeformatter);
            } catch(DateTimeParseException e){
                System.out.println("Invalid date format ");
                return;
            } 
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
            System.out.println("Invalid Input" + e);
            return;
        } catch(InputMismatchException e) {
            System.out.println("Invalid Input" + e);
            return;
        } catch(DatabaseException e) {
            System.out.println("Database Error" + e);
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
            System.out.println("Invalid Id number");
        } catch(DatabaseException e) {
            System.out.println("Database Error" + e);
        } catch(InputMismatchException e) {
            System.out.println("Invalid Input" + e);
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
            LocalDate dob = null;
            try {
                dob = LocalDate.parse(stringDob,datetimeformatter);
            } catch(DateTimeParseException e) {
                System.out.println("Invlid date Format" + e);
                return;
            }
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
            System.out.println("Invalid Input");
        } catch(DatabaseException e) {
            System.out.println("Database Error" + e);
        } catch(InputMismatchException e) {
            System.out.println("Invalid Input" + e);
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
            System.out.println("Id number does not exists");
        } catch(DatabaseException e) {
            System.out.println("Database error" + e);
        } catch(InputMismatchException e) {
            System.out.println("Invalid Input" + e);
            return;
        }
        System.out.println("=============Employee Deleted=============");
    }
   
}