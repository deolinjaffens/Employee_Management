package com.skill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.exception.DatabaseException;
import com.helper.HibernateConnection;
import com.helper.Manager;
import com.model.Skill;
import com.model.Employee;

/**
 *Stores and retrives details related to skills from the database
 *Implements the SkillDao interface
 *
 *@Deolin Jaffens
 */

public class SkillDaoImpl implements SkillDao {

    /**
     *Adds new skill associated to employee in the database
     *
     *@param skill - contains the new skill that has to be added
     *@param employee - contains employee that has to be linked with the skill
     *@return int - id of the skill that is generated
     */

    public int addSkill(Skill skill, Employee employee) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
		int id = 0;
        Transaction transaction = null;
        try {
			Skill existingSkill = isPresent(skill,session);
			transaction = session.beginTransaction();
			if (existingSkill == null) {
		        id = (Integer) session.save(skill);
				existingSkill = skill;
			} else {
				id = existingSkill.getId();
			}
			    employee.getSkills().add(existingSkill);
			    existingSkill.getEmployees().add(employee);
			    session.save(employee);
                transaction.commit();
			} catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Department Error" + e);
        } finally {
            session.close();
        }
        return id;
    }
	
	/**
	 *<p>
	 *Checks wheather the skill to be added already exists
     *</p>
     *@param skill - skill that has to be checked
     *@param session - connects dao to the database
     *@return Skill - skill that already exists in the database
     *@throws HibernateException
     */
    public Skill isPresent(Skill skill,Session session) throws HibernateException {
		Transaction transaction = null;
		List<Skill> skills = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
            skills = session.createQuery("from Skill", Skill.class).list();
            transaction.commit();
			for(Skill existingSkill : skills) {
				if(existingSkill.getName().equals(skill.getName())) {
					System.out.println(existingSkill);
					return existingSkill;
				}
			}
		} catch (HibernateException e) {
			if (transaction != null) {
                transaction.rollback();
            }
			throw new HibernateException(e);
		}
		return null;
	}
    		

    /**
     *Extracts all the skills related to the employee
     *
     *@param employee - employee whose skills has to be extracted
     *@return Set<Skill>
     */

    public Set<Skill> getAllSkills(Employee employee) throws DatabaseException {
        Set<Skill> skills = employee.getSkills();
        return skills;
    }

    /**
     *Update any specific details of the skill from the database
     *
     *@param employee - employee whose details has to be updated
     *@param id - id of skill that has to be updated
     *@param name - skills name to be updated
     *@throws DatabaseException
     */

    public void updateSkill(Employee employee, int id, String name) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Skill existingSkill = session.get(Skill.class, id);
            if (existingSkill != null) {
                existingSkill.setName(name);
                session.update(existingSkill );
                transaction.commit();
            }
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Database Error  " + e);
        } finally {
            session.close();
        }
    }

    /**
     *Removes the association of an employee with a particular skill
     *
     *@param id - id of skill to be removed
     *@param employee - employee who needs to remove a particular skill
     *@throws DatabaseException
     */

    public void removeSkill(int id, Employee employee) throws DatabaseException {
         Session session = HibernateConnection.getFactory().openSession();
         Transaction transaction = null;
         try {
             transaction = session.beginTransaction();
             Skill skill = session.get (Skill.class, id);
             if (skill != null) {
                 session.delete(skill);             
                 transaction.commit();
             }
             else {
                 throw new DatabaseException("Database Error  " + id);
             }
         } catch (HibernateException e) {
             if(transaction != null) {
                transaction.rollback();
            }
             throw new DatabaseException("Database Error  " + e);
             
         } finally {
             session.close();
        }
    }    

    /**
     *Extract the employees who has a specific skill
     *
     *@param id - id of the skill whose employees has to be extracted
     *@return Set<Employee>
     */

    public Set<Employee> getEmployees (int id) throws DatabaseException {
		Session session = HibernateConnection.getFactory().openSession();
		Set<Employee> employees = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Skill skill = session.get(Skill.class, id);
			transaction.commit();
			employees = skill.getEmployees(); 
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Database Error  " + e);
        } finally {
            session.close();
        }
        return employees;
    }

}
                
