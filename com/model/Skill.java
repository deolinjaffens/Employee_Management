package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.model.Employee;

/**
 *<p>
 *All the details of a particular skill is initialised
 *</p>
 *@Deolin Jaffens
 */

@Entity
@Table(name = "skill")
public class Skill implements Serializable {
    
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
    private String name;
    
	@ManyToMany(mappedBy = "skills") 
    private Set<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Extract employees who are able conversent in a specific programming language

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Skill(String name) {
        this.name = name;
        this.employees = new HashSet<>();
    }
	
	public Skill() {
		
	}
}