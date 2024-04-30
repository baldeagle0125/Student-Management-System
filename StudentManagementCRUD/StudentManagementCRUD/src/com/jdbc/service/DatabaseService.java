package com.jdbc.service;

// Import relevant classes for JDBC database operations
import com.jdbc.model.Course;
import com.jdbc.model.Student; // The Student class to represent student data
import com.jdbc.util.DatabaseUtil; // Utility class for database connections
import com.jdbc.util.QueryUtil; // Utility class for SQL queries
import java.security.MessageDigest; // Used for password hashing with SHA-256

import java.util.List; // List interface
import java.util.ArrayList; // Implementation of the List
import java.sql.Connection; // JDBC Connection class
import java.sql.SQLException; // Exception for database errors
import java.sql.PreparedStatement; // Used to execute SQL statements with parameters
import java.sql.Statement; // Used for executing SQL statements
import java.sql.ResultSet; // Stores the result of a SQL query

/**
 * This class handles database operations related to students, including inserting, updating, 
 * deleting, and retrieving student records. It also has a method to validate staff login.
 */
public class DatabaseService {
    
    // Create a DatabaseUtil instance for database connection management
    DatabaseUtil databaseUtil = new DatabaseUtil();
    
    // New method to insert a course into the database
    public void insertCourse(Course course) throws SQLException {
        // Using try-with-resources for connection management
        try (Connection connection = databaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.insertCourse())) {

            // Set parameters in the insert query based on the Course object
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseCategory());
            preparedStatement.setInt(3, course.getCourseCredits());
            preparedStatement.setString(4, course.getCourseLevel());
            preparedStatement.setString(5, course.getCourseDelivery());
            preparedStatement.setString(6, course.getCourseDuration());

            // Execute the insert query and check if successful
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Course added successfully."); // Success message
            } else {
                System.out.println("Course insert failed."); // Failure message
            }

        } // End of try-with-resources block
    } // End insertCourse
    
    
    
    // Method to get/display all courses from the database
    public List<Course> getAllCourse() throws SQLException {
        List<Course> courses = new ArrayList<>(); // Create a list to store courses
        
        // Use a try-with-resources block to ensure resources are properly managed
        try (Connection connection = databaseUtil.getConnection(); 
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.selectAllCourseQuery())) {

            // Loop through the result set and add courses to the list
            while (resultSet.next()) {
                // Create a Student object from the current result set row
                Course course = new Course(
                        resultSet.getInt("course_id"), // Course ID
                        resultSet.getString("course_name"), // Course's name
                        resultSet.getString("course_category"), // Course's category
                        resultSet.getInt("course_credits"), // Course's credits 
                        resultSet.getString("course_level"), // Course's level
                        resultSet.getString("course_delivery"), // Course's delivery
                        resultSet.getString("course_duration") // Course's duration
                );
                courses.add(course); // Add to the list
            }
        }
        
        return courses; // Return the list of courses
    } // End getAllStudent
    
    
      
    public List<String> getAllStudentIds() throws SQLException {
        List<String> studentIds = new ArrayList<>(); // Create a list to store student IDs

        // Use a try-with-resources block to ensure resources are properly managed
        try (Connection connection = databaseUtil.getConnection(); 
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.selectAllStudentIDsQuery())) { // Query to fetch all student IDs

            // Loop through the result set and add each student ID to the list
            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id"); // Fetch the student ID from the result set
                studentIds.add(studentId); // Add it to the list
            }
        }

        return studentIds; // Return the list of student IDs
    }
    
    public List<String> getAllCourseIds() throws SQLException {
    List<String> courseIds = new ArrayList<>(); // Create a list to store course IDs

    // Use a try-with-resources block to ensure resources are properly managed
    try (Connection connection = databaseUtil.getConnection(); 
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(QueryUtil.selectAllCourseIDsQuery())) { // Query to fetch all course IDs

        // Loop through the result set and add each course ID to the list
        while (resultSet.next()) {
            String courseId = resultSet.getString("course_id"); // Fetch the course ID from the result set
            courseIds.add(courseId); // Add it to the list
        }
    }

    return courseIds; // Return the list of course IDs
}


    
    
    // Method to update a course record in the database
    public void updateCourse(Course course) throws SQLException {
        try (Connection connection = databaseUtil.getConnection(); PreparedStatement preparedStatement = connection.
                prepareStatement(QueryUtil.updateCourseQuery(course.getCourseId()))) {

            // Set the updated values from the Course object
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseCategory());
            preparedStatement.setInt(3, course.getCourseCredits());
            preparedStatement.setString(4, course.getCourseLevel());
            preparedStatement.setString(5, course.getCourseDelivery());
            preparedStatement.setString(6, course.getCourseDuration());

            int rows = preparedStatement.executeUpdate(); // Execute the update

            // Check if the update was successful
            if (rows > 0) {
                System.out.println("Course Updated Successfully."); // Success message
            } else {
                System.out.println("Course Update Failed."); // Failure message
            }
        }
    } // End updateCourse 
    
    
    
    // Method to insert a new student into the database
    public void insertStudent(Student student) throws SQLException {
        // Use a try-with-resources block to ensure the connection is closed after use
        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.insertStudentQuery())) {
            
            // Set the values for the insert query using the Student object
            preparedStatement.setString(1, student.getStudentName()); // Student's name
            preparedStatement.setString(2, student.getStudentEmail()); // Student's email
            preparedStatement.setString(3, student.getStudentPhone()); // Student's phone
            preparedStatement.setString(4, student.getStudentDOB()); // Student's date of birthday
            preparedStatement.setString(5, student.getStudentAddress()); // Student's address
            preparedStatement.setDouble(6, student.getStudentBalance()); // Student's balance
            
            // Execute the insert query and get the number of affected rows
            int rows = preparedStatement.executeUpdate();
            
            // Check if the insert was successful
            if (rows > 0) {
                System.out.println("Student created successfully."); // Success message
            } else {
                System.out.println("Student Insert FAILED..."); // Failure message
            }
            
        } // End of try-with-resources block
    } // End insertStudent
    
    // Method to get all students from the database
    public List<Student> getAllStudent() throws SQLException {
        List<Student> students = new ArrayList<>(); // Create a list to store students
        
        // Use a try-with-resources block to ensure resources are properly managed
        try (Connection connection = databaseUtil.getConnection(); 
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.selectAllStudentQuery())) {

            // Loop through the result set and add students to the list
            while (resultSet.next()) {
                // Create a Student object from the current result set row
                Student student = new Student(
                        resultSet.getInt("student_Id"), // Student ID
                        resultSet.getString("student_name"), // Student's name
                        resultSet.getString("student_email"), // Student's email
                        resultSet.getString("student_phone"), // Student's phone
                        resultSet.getString("student_dob"), // Student's date of birthday
                        resultSet.getString("student_address"), // Student's address
                        resultSet.getDouble("student_balance") // Student's balance
                );
                students.add(student); // Add to the list
            }
        }
        
        return students; // Return the list of students
    } // End getAllStudent
    
    // Method to get a course by its ID
    public Course getCourseById(int courseId) throws SQLException {
        Course course = null; // Initialize the Course object

        // Define the query with a parameter placeholder
        String query = QueryUtil.selectCourseById(); 

        try (Connection connection = databaseUtil.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, courseId); // Set the parameter index for course ID

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // If a record is found
                    course = new Course(
                        resultSet.getInt("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("course_category"),
                        resultSet.getInt("course_credits"),
                        resultSet.getString("course_level"),
                        resultSet.getString("course_delivery"),
                        resultSet.getString("course_duration")
                    );
                }
            }
        }

        return course; // Return the Course object or null if not found
    }

    
    // Method to get a student by their ID
    public Student getStudentById(int studentId) throws SQLException {
        Student student = null; // Initialize the Student object

        String query = QueryUtil.selectStudentById(); // Get the SQL query with a placeholder

        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, studentId); // Set the parameter index for student ID

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // If a record is found
                    student = new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getString("student_email"),
                        resultSet.getString("student_phone"),
                        resultSet.getString("student_dob"),
                        resultSet.getString("student_address"),
                        resultSet.getDouble("student_balance")
                    );
                }
            }
        }

        return student; // Return the Student object or null if not found
    }
    
    // Method to delete a student by their ID
    public void deleteStudentById(int studentId) throws SQLException {
        try (Connection connection = databaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Execute the delete query and get the number of affected rows
            int rows = statement.executeUpdate(QueryUtil.deleteStudentById(studentId));
            
            // Check if the delete was successful
            if (rows > 0) {
                System.out.println("Record deleted successfully."); // Success message
            } else {
                System.out.println("Something went wrong..."); // Failure message
            }
        }
    } // End deleteStudentById
    
    // Method to delete a Course by their ID
   public void deleteCourseById(int courseId) throws SQLException {
        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUtil.deleteCourseById())) {

            // Set the course ID in the delete query
            statement.setInt(1, courseId);

            // Execute the delete query and get the number of affected rows
            int rows = statement.executeUpdate();

            // Check if the delete was successful
            if (rows > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("Something went wrong...");
            }
        }
    }

    
    // Method to update a student record in the database
    public void updateStudent(Student student) throws SQLException {
        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.
                        prepareStatement(QueryUtil.updateStudentQuery(student.getStudentId()))) {
            
            // Set the updated values from the Student object
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentAddress());
            preparedStatement.setDouble(3, student.getStudentBalance());
            
            int rows = preparedStatement.executeUpdate(); // Execute the update
            
            // Check if the update was successful
            if (rows > 0) {
                System.out.println("Record updated successfully."); // Success message
            } else {
                System.out.println("Update record Failed."); // Failure message
            }
        }
    } // End updateStudent
    
    // Secure hashing function for passwords (SHA-256)
    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Get the SHA-256 hashing instance
        byte[] hash = digest.digest(password.getBytes("UTF-8")); // Hash the password
        StringBuilder hexString = new StringBuilder(); // Create a string to store the hash in hex
        
        // Convert the byte array to a hex string
        for (byte b : hash) {
            hexString.append(String.format("%02x", b)); // Format as two-digit hex
        }
        
        return hexString.toString(); // Return the hashed password as a hex string
    }
    
    
    // NOTE: For testing purposes, using plain text passwords. DO NOT USE IN PRODUCTION.
    // Method to validate staff credentials in the database
    public boolean validateStaffCredentials(String email, String password) throws SQLException, Exception {
        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM staff WHERE staff_email = ? AND staff_password = ?")) {
            
            // Set the email and hashed password in the prepared statement
            stmt.setString(1, email);
            // Comment out the hashing line to use plain text passwords for testing
            // stmt.setString(2, hashPassword(password)); 
            stmt.setString(2, password); // Use plain text password for testing
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Return true if there's at least one result (meaning valid credentials)
                return rs.next();
            }
        }
    } // End validateStaffCredentials

    

    

    
}
