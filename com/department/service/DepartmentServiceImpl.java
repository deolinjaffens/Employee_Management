package com.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.department.dao.DepartmentDao;
import com.department.dao.DepartmentDaoImpl;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import com.util.exception.DatabaseException;
import com.model.Department;
import com.model.Employee;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDao departmentDao = new DepartmentDaoImpl();

    public void addDepartment(String name) throws DatabaseException {
        Department department = new Department(name);
        departmentDao.addDepartment(department);
    }

    public List<Department> getAllDepartments() throws DatabaseException {
        return departmentDao.getAllDepartments();
    }

    public void updateDepartment(int id, String name) throws DatabaseException {
        try {
            departmentDao.updateDepartment(id, name);
        } catch(NullPointerException e) {
            throw new DatabaseException("Department does not exist");
        }	
    }
    
    public void removeDepartment(int id) throws DatabaseException {
        departmentDao.removeDepartment(id);
    }

    public boolean isEmpty() throws DatabaseException {
        return departmentDao.checkForDepartment();
    }

    public Department getDepartment(int id) throws DatabaseException {
       return departmentDao.getDepartment(id);
	}
}