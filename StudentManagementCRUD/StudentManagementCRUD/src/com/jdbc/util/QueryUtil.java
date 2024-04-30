package com.jdbc.util;
/**
 * @author mazds
 */
public class QueryUtil {
    
    // SQL query to insert a new Student record into the STUDENT_INFO table   
    public static String insertStudentQuery(){
        return "INSERT INTO student_info (student_name, student_email, student_phone, "
                + "student_dob, student_address, student_balance) VALUES (?, ?, ?, ?, ?, ?)";
    }
    
    // SQL query to insert a new Student record into the STUDENT_INFO table
    public static String selectAllStudentQuery(){
        return "SELECT * FROM STUDENT_INFO";
    }
    
    public static String selectAllCourseQuery() {
    return "SELECT * FROM COURSE_INFO"; // Query to select all courses
    }

    public static String selectStudentById() {
        return "SELECT * FROM STUDENT_INFO WHERE student_id = ?"; // Placeholder for parameter
    }
        
    public static String deleteStudentById(int studentId){
        return "DELETE FROM STUDENT_INFO WHERE student_id = "+studentId;
    }
    
    // SQL query to UPDATE a Student record into the STUDENT_INFO table
    public static String updateStudentQuery(int studentId){
        return "UPDATE STUDENT_INFO SET student_name = ?, student_address = ?, "
                + "student_balance = ? WHERE student_id = "+studentId;
    }

    // SQL query to insert a new course record into the COURSE_INFO table
    public static String insertCourse() {
        return "INSERT INTO COURSE_INFO (course_name, course_category, course_credits, "
                + "course_level, course_delivery, course_duration) " +
               "VALUES (?,?,?,?,?,?)"; // Prepared statement with placeholders
    }
  
    // SQL query to UPDATE a course record into the COURSE_INFO table
    public static String updateCourseQuery(int courseId) {
        return "UPDATE COURSE_INFO SET course_name = ?, course_category = ?, "
                + "course_credits = ?, course_level = ?, course_delivery = ?, course_duration = ? "
                + "WHERE course_id = " + courseId;
    }
    
    // SQL query to Display the Students IDs at the Student update page
    public static String selectAllStudentIDsQuery(){
        return "SELECT student_id FROM student_info";
    }
    
    // SQL query to display all course IDs for a given page or context
    public static String selectAllCourseIDsQuery() {
        return "SELECT course_id FROM COURSE_INFO"; // Query to fetch all course IDs
    }

    
    public static String deleteCourseById() {
        return "DELETE FROM COURSE_INFO WHERE course_id = ?"; // Placeholder for parameter
    }

    
    public static String selectCourseById() {
        return "SELECT * FROM COURSE_INFO WHERE course_id = ?"; // Query with a placeholder
    } 

}// End QueryUtil
