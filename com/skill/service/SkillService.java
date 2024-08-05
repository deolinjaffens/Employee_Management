package com.skill.service;

import java.util.Set;

import com.model.Employee;
import com.model.Skill;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeService;
import com.exception.DatabaseException;
import com.skill.dao.SkillDao;
import com.skill.dao.SkillDaoImpl;

/**
 *Implements the skill interface
 *Connects controller to the dao
 *
 *@Deolin Jaffens
 */ 

public interface SkillService {

    /**
     *Adds the new Skill to the database by transferring details from the 
     *controller to the dao 
     *
     *@param name - name of the skill to be added
     *@param employee - employee who posses the specific skill
     *@throws DatabaseException
     */    

    public int addSkill(String name, Employee employee) throws DatabaseException;

    /**
     *Extracts all the skills related to a specific employee from the Dao
     *
     *@param id - id of the employee whose skills are to be extracted
     *@return Set<Skill>
     *@throws DatabaseException
     */

    public Set<Skill> getAllSkills(Employee employee) throws DatabaseException;

    /**
     *Passes the details that has to be updated from the user to the dao
     *
     *@param employee - employee whose skill details has to be updated
     *@param id - id of the skill that has to be updated
     *@param name - name that is to be updated for a skill
     */

    public void updateSkill(Employee employee, int id, String name) throws DatabaseException;

    /**
     *Passes the skill detail that has to be removed from the association of
     *a specific employee
     *
     *@param id - id of skill that has to be removed
     *@param employee - employee whose skill has to be removed
     */

    public void removeSkill(int id, Employee employee) throws DatabaseException;

    /**
     *Extracted data of employees related to a specific programming langugae
     *from the Dao is transferred to the controller
     *
     *@param id - id of the skill for which the employees has to be
     *extracted
     *@return Set<Employee>
     */ 

    public Set<Employee> getEmployees(int id) throws DatabaseException;
	
	/**
	 *<p>
	 *A specific employee is extracted from the database
	 *</p>
	 *@param id - id of employee to be extracted
	 *@return Employee
	 *@throws DatabaseException
	 */
	 
	public Employee getEmployee(int id) throws DatabaseException;
}