package com.skill.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.util.exception.DatabaseException;
import com.model.Employee;
import com.model.Skill;
import com.skill.service.SkillService;
import com.skill.service.SkillServiceImpl;

/**
 *<p>
 *
 *</p>
 *@author Deolin Jaffens
 */ 

public class SkillController {
	
	private static Logger logger = LogManager.getLogger(SkillController.class);
    SkillService skillService = new SkillServiceImpl();
    Scanner scanner = new Scanner(System.in);

    /**
     *Initialization and extraction of details related to Skills are done 
     *for its creation,visualisation,edition and removal
     *
     *@param employee - employee who has to navigate with the skill operations
     */
    public void menu() {
        boolean exit = false;
        try {
            System.out.print("Enter Employee Id : ");
            int id = scanner.nextInt();
            Employee employee = skillService.getEmployee(id);
			if(employee != null) {
                while(!exit) {
                    System.out.println("(1) Add Skill");
                    System.out.println("(2) View All Skills Known to the Employee");
                    System.out.println("(3) Update Skill");
                    System.out.println("(4) Remove Skill");
                    System.out.println("(5) View All Employee who knows a specific Skill");
                    System.out.println("(6) Exit");
                    System.out.println(" ");
                    System.out.print("Enter an option : ");
                    int option = scanner.nextInt();
                    switch(option) {

                        case 1: 
                            addSkill(employee);
                            break;

                        case 2:
                            viewAllSkills(employee);
                            break;

                        case 3:
                            updateSkill(employee);
                            break;

                        case 4:
                            removeSkill(employee);
                            break;

                        case 5:
                            viewAllEmployees();
                            break;
                 
                        case 6:
                            exit = true;
                            break;

                        default:
                            System.out.println("Invalid!!");
                    }
                }
                System.out.println("========Back to Main Menu=========");
            } else {
				System.out.println("Employee Not Found");
			}
		} catch(DatabaseException e) {
			logger.error(e.getMessage());
		} catch(InputMismatchException e) {
			logger.warn(e.getMessage());
		}
	}
   
    /**
     *Details of the skill that has to be added to the database or associated with the 
     *employee is received
     *
     *@param employee - employee who gets associated with the skill
     */

    public void addSkill(Employee employee) {
        try {
            System.out.print("Add a Skill : ");
            String name = scanner.next();
            int id = skillService.addSkill(name, employee);      
            System.out.println(id);      
            System.out.println("=============Skill Added=============");
        } catch(DatabaseException e) {
            logger.error("failed to add employee");
        }
    }

    /**
     *Extracts all the skills of a specific employee from the database
     *
     *@param employee - Employee whose skills are to be known
     */

    public void viewAllSkills(Employee employee) {
        try {
            System.out.println("=============Skills=============");
            for(Skill skill : skillService.getAllSkills(employee)) {
                System.out.println(skill.getName());
            } 
        } catch(DatabaseException e) {
            logger.error("Failed to fetch skills");
        }
    }

    /**
     *Specific details of the skill that has to be updated is initialised
     *
     *@param employee - employee who has to update the skill details
     */

    public void updateSkill(Employee employee) {
        try {
            System.out.print("Enter the id of skill to be updated : ");
            int id = scanner.nextInt();
            System.out.println("Enter the skill name to replace: ");
            String name = scanner.next();
            skillService.updateSkill(employee, id, name);
        } catch(DatabaseException e) {
            logger.error("Failed to update employee details");
        }
    }

    /**
     *Removes the specific skill the employee wants to remove
     *
     *@param employee - employee who wants to remove the skill
     */

    public void removeSkill(Employee employee) {
        try {
            System.out.println("Enter the Skill id to be removed");
            int id = scanner.nextInt();
            skillService.removeSkill(id, employee);
            System.out.println("=============Skill Removed=============");
        } catch(DatabaseException e) {
            logger.error("Failed to remove skill");
        }
    }
    
    /**
     *All the employees who are proficient in a specific skill is extraced from
     *the database and viewed
     */

    public void viewAllEmployees() {
        try {
            System.out.print("Enter the Skill Id : ");
            int id = scanner.nextInt();
            System.out.println("=============Employees who are proficient in the Skill=============");
            for(Employee employees : skillService.getEmployees(id)){
                System.out.println(employees.getName());
            }
        } catch(DatabaseException e) {
            logger.error("Failed to fetch all employees");
        }
    }
}