package com.jdbc.util;

public class QueryUtil {
    
    // SQL query to insert a new Student record into the STUDENT_INFO table   
    public static final String INSERT_STUDENT_QUERY = "INSERT INTO student_info (student_name, student_email, student_phone, "
            + "student_dob, student_address, student_balance) VALUES (?, ?, ?, ?, ?, ?)";
    
    // SQL query to retrieve all Student records from the STUDENT_INFO table
    public static final String SELECT_ALL_STUDENTS_QUERY = "SELECT * FROM STUDENT_INFO";
    
    // SQL query to retrieve a Student record by ID from the STUDENT_INFO table
    public static final String SELECT_STUDENT_BY_ID_QUERY = "SELECT * FROM STUDENT_INFO WHERE student_id = ?";
        
    // SQL query to delete a Student record by ID from the STUDENT_INFO table
    public static String deleteStudentById(int studentId){
        return "DELETE FROM STUDENT_INFO WHERE student_id = " + studentId;
    }
    
    // SQL query to update a Student record by ID in the STUDENT_INFO table
    public static String updateStudentQuery(int studentId){
        return "UPDATE STUDENT_INFO SET student_name = ?, student_address = ?, "
                + "student_balance = ? WHERE student_id = " + studentId;
    }

    // SQL query to insert a new course record into the COURSE_INFO table
    public static final String INSERT_COURSE_QUERY = "INSERT INTO COURSE_INFO (course_name, course_category, course_credits, "
            + "course_level, course_delivery, course_duration) " +
           "VALUES (?,?,?,?,?,?)";
    
    // SQL query to retrieve all course records from the COURSE_INFO table
    public static final String SELECT_ALL_COURSES_QUERY = "SELECT * FROM COURSE_INFO";
    
    // SQL query to update a course record by ID in the COURSE_INFO table
    public static String updateCourseQuery(int courseId) {
        return "UPDATE COURSE_INFO SET course_name = ?, course_category = ?, "
                + "course_credits = ?, course_level = ?, course_delivery = ?, course_duration = ? "
                + "WHERE course_id = " + courseId;
    }
    
    // SQL query to retrieve all Student IDs
    public static final String SELECT_ALL_STUDENT_IDS_QUERY = "SELECT student_id FROM student_info";

    public static String SELECT_ALL_STUDENT_IDS_QUERY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String UPDATE_COURSE_QUERY(int courseId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String INSERT_STUDENT_QUERY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String SELECT_ALL_STUDENTS_QUERY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String SELECT_STUDENT_BY_ID_QUERY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String DELETE_STUDENT_BY_ID(int studentId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String UPDATE_STUDENT_QUERY(int studentId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
