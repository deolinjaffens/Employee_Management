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

import com.util.exception.DatabaseException;
import com.config.hibernate.HibernateConfig;
import com.config.drivermanager.DriverManagerConfig;
import com.model.Skill;
import com.model.Employee;

/**
 *<p>
 *Stores and retrives details related to skills from the database
 *Implements the SkillDao interface
 *</p>
 *@author Deolin Jaffens
 */

public class SkillDaoImpl implements SkillDao {

    public int addSkill(Skill skill, Employee employee) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
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
	 
    public Skill isPresent(Skill skill,Session session) throws DatabaseException {
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
			throw new DatabaseException("Database Error " + e);
		}
		return null;
	}
    		
    public Set<Skill> getAllSkills(Employee employee) throws DatabaseException {
        Set<Skill> skills = employee.getSkills();
        return skills;
    }

    public void updateSkill(Employee employee, int id, String name) throws DatabaseException {
        Session session = HibernateConfig.getFactory().openSession();
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

    public void removeSkill(int id, Employee employee) throws DatabaseException {
         Session session = HibernateConfig.getFactory().openSession();
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

    public Set<Employee> getEmployees (int id) throws DatabaseException {
		Session session = HibernateConfig.getFactory().openSession();
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