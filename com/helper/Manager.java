package com.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *Connects various functions to the driver
 *
 *@author Deolin Jaffens
 */

public class Manager {
    private static final String url = "jdbc:mysql://localhost:3306/employee_management_system";
    private static final String user = "root";
    private static final String password = "";

    /**
     *Connects various functions to the driver class
     */

    public static Connection getConnection() {
        Connection connection = null;
        try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection(url, user, password);
             
        } catch(SQLException e) {
            System.out.println("SQL Error");
        } catch(ClassNotFoundException e) {
            System.out.println("Class not Found");
        }
        return connection;
    }
} 