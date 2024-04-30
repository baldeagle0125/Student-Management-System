package com.jdbc.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author mazds
 */
public class DatabaseUtil {
    
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "DevMaster-222";
    
    
    public DatabaseUtil(){
        
        try {
            Class.forName(DRIVER_PATH);
        } catch (Exception e){
            throw new RuntimeException("Something went wrong -> " + e);
        }
    }// End constructor
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);  
    }// End getConnection()
    
}
