package com.skill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import com.config.drivermanager.DriverManagerConfig;
import com.util.exception.DatabaseException;
import com.model.Skill;
import com.model.Employee;

/**
 *Stores and retrives details related to skills from the database
 *Implements the SkillDao interface
 *
 *@Deolin Jaffens
 */

public interface SkillDao {

    /**
	 *<p>
     *Adds new skill associated to employee in the database
     *</p>
     *@param skill - contains the new skill that has to be added
     *@param employee - contains employee that has to be linked with the skill
     *@return int
     */

    public int addSkill(Skill skill, Employee employee) throws DatabaseException;
	
	/**
	 *<p>
	 *Checks wheather the skill to be added already exists
     *</p>
     *@param skill - skill that has to be checked
     *@param session - connects dao to the database
     *@return Skill - skill that already exists in the database
     *@throws HibernateException
     */
    public Skill isPresent(Skill skill,Session session) throws DatabaseException;
    /**
	 *<p>
     *Extracts all the skills related to the employee
     *</p>
     *@param id - id of the employee whose skills has to be extracted
     *@return Set<Skill>
     */

    public Set<Skill> getAllSkills(Employee employee) throws DatabaseException;

    /**
	 *<p>
     *Update any specific details of the skill from the database
     *</p>
     *@param employee - employee whose details has to be updated
     *@param id - id of skill that has to be updated
     *@param name - skills name to be updated
     *@throws DatabaseException
     */

    public void updateSkill(Employee employee, int id, String name) throws DatabaseException;

    /**
	 *<p>
     *Removes the association of an employee with a particular skill
     *</p>
     *@param id - id of skill to be removed
     *@param employee - employee who needs to remove a particular skill
     *@throws DatabaseException
     */

    public void removeSkill(int id, Employee employee) throws DatabaseException;

    /**
	 *<p>
     *Extract the employees who has a specific skill
     *</p>
     *@param id - id of the skill whose employees has to be extracted
     *@return Set<Employee>
     */

    public Set<Employee> getEmployees (int id) throws DatabaseException;

}