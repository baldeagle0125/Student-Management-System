package com.jdbc.service;

import com.jdbc.model.Course;
import com.jdbc.model.Student;
import com.jdbc.util.DatabaseUtil;
import static com.jdbc.util.DatabaseUtil.getConnection;
import com.jdbc.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    public void insertCourse(Course course) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.INSERT_COURSE_QUERY)) {

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseCategory());
            preparedStatement.setInt(3, course.getCourseCredits());
            preparedStatement.setString(4, course.getCourseLevel());
            preparedStatement.setString(5, course.getCourseDelivery());
            preparedStatement.setString(6, course.getCourseDuration());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Course insert failed.");
            }

        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.SELECT_ALL_COURSES_QUERY)) {

            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("course_category"),
                        resultSet.getInt("course_credits"),
                        resultSet.getString("course_level"),
                        resultSet.getString("course_delivery"),
                        resultSet.getString("course_duration")
                );
                courses.add(course);
            }
        }

        return courses;
    }

    public List<String> getAllStudentIds() throws SQLException {
        List<String> studentIds = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.SELECT_ALL_STUDENT_IDS_QUERY())) {

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                studentIds.add(studentId);
            }
        }

        return studentIds;
    }

    public void updateCourse(Course course) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.UPDATE_COURSE_QUERY(course.getCourseId()))) {

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseCategory());
            preparedStatement.setInt(3, course.getCourseCredits());
            preparedStatement.setString(4, course.getCourseLevel());
            preparedStatement.setString(5, course.getCourseDelivery());
            preparedStatement.setString(6, course.getCourseDuration());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Course Updated Successfully.");
            } else {
                System.out.println("Course Update Failed.");
            }
        }
    }

    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.INSERT_STUDENT_QUERY())) {

            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentEmail());
            preparedStatement.setString(3, student.getStudentPhone());
            preparedStatement.setString(4, student.getStudentDOB());
            preparedStatement.setString(5, student.getStudentAddress());
            preparedStatement.setDouble(6, student.getStudentBalance());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Student created successfully.");
            } else {
                System.out.println("Student Insert FAILED...");
            }

        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryUtil.SELECT_ALL_STUDENTS_QUERY())) {

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("student_Id"),
                        resultSet.getString("student_name"),
                        resultSet.getString("student_email"),
                        resultSet.getString("student_phone"),
                        resultSet.getString("student_dob"),
                        resultSet.getString("student_address"),
                        resultSet.getDouble("student_balance")
                );
                students.add(student);
            }
        }

        return students;
    }

    public Student getStudentById(int studentId) throws SQLException {
        Student student = null;

        String query = QueryUtil.SELECT_STUDENT_BY_ID_QUERY();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
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

        return student;
    }

    public void deleteStudentById(int studentId) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {

            int rows = statement.executeUpdate(QueryUtil.DELETE_STUDENT_BY_ID(studentId));

            if (rows > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Something went wrong...");
            }
        }
    }

    public void updateStudent(Student student) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.UPDATE_STUDENT_QUERY(student.getStudentId()))) {

            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentAddress());
            preparedStatement.setDouble(3, student.getStudentBalance());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Update record Failed.");
            }
        }
    }

    public boolean validateStaffCredentials(String email, String password) throws SQLException, Exception {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM staff WHERE staff_email = ? AND staff_password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Method to retrieve all students from the database
    public List<Student> getAllStudent() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // Implement getConnection() method to establish a database connection
            
            // SQL query to select all students
            String query = "SELECT * FROM students";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Iterate through the result set and create Student objects
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("studentId"),
                    rs.getString("studentName"),
                    rs.getString("studentEmail"),
                    rs.getString("studentPhone"),
                    rs.getString("studentDOB"),
                    rs.getString("studentAddress"),
                    rs.getDouble("studentBalance")
                );
                students.add(student);
            }
        } finally {
            // Close the database resources
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return students;
    }

    // Method to retrieve all courses from the database
    public List<Course> getAllCourse() throws SQLException {
        List<Course> courses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // Implement getConnection() method to establish a database connection
            
            // SQL query to select all courses
            String query = "SELECT * FROM courses";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Iterate through the result set and create Course objects
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("courseId"),
                    rs.getString("courseName"),
                    rs.getString("courseCategory"),
                    rs.getInt("courseCredits"),
                    rs.getString("courseLevel"),
                    rs.getString("courseDelivery"),
                    rs.getString("courseDuration")
                );
                courses.add(course);
            }
        } finally {
            // Close the database resources
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return courses;
    }
}
