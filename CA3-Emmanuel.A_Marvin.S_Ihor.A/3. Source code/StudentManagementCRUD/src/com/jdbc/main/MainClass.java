package com.jdbc.main;

import com.jdbc.model.Student;
import com.jdbc.service.DatabaseService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Command-line interface for managing student data
 */
public class MainClass {

    public static void main(String[] args) throws SQLException {
        
        DatabaseService databaseService = new DatabaseService(); // Interface for database operations
        Scanner scanner = new Scanner(System.in); // To read input from the console

        boolean isRunning = true; // To control the while loop
        
        while (isRunning) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Find Student by ID");
            System.out.println("4. Delete Student by ID");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");

            int choice = Integer.parseInt(scanner.nextLine()); // User choice

            switch (choice) {
                case 1:
                    System.out.println("Enter student details (Name, Email, Phone, Date of Birth (yyyy-MM-dd), Address, Balance):");
                    String name = scanner.nextLine();
                    String email = scanner.nextLine();
                    String phone = scanner.nextLine();
                    String dob = scanner.nextLine();
                    String address = scanner.nextLine();
                    double balance = Double.parseDouble(scanner.nextLine());
                    
                    Student newStudent = new Student(name, email, phone, dob, address, balance);
                    databaseService.insertStudent(newStudent);
                    System.out.println("Student added successfully.");
                    break;
                    
                case 2:
                    System.out.println("Displaying all students:");
                    databaseService.getAllStudent().forEach(student -> {
                        System.out.println(student.toString()); 
                    });
                    break;
                    
                case 3:
                    System.out.println("Enter student ID:");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    if (!databaseService.getStudentById(studentId)) {
                        System.out.println("Student not found with ID: " + studentId);
                    }
                    break;

                case 4:
                    System.out.println("Enter student ID to delete:");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    databaseService.deleteStudentById(deleteId);
                    System.out.println("Student deleted successfully.");
                    break;

                case 5:
                    System.out.println("Enter student ID to update:");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    if (databaseService.getStudentById(updateId)) {
                        System.out.println("Enter new details (Name, Email, Phone, DOB, Address, Balance):");
                        String newName = scanner.nextLine();
                        String newEmail = scanner.nextLine();
                        String newPhone = scanner.nextLine();
                        String newDOB = scanner.nextLine();
                        String newAddress = scanner.nextLine();
                        double newBalance = Double.parseDouble(scanner.nextLine());
                        
                        Student updatedStudent = new Student(updateId, newName, newEmail, newPhone, newDOB, newAddress, newBalance);
                        databaseService.updateStudent(updatedStudent);
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Student not found with ID: " + updateId);
                    }
                    break;

                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    isRunning = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            } // End switch
        } // End while
    } // End main
} // End MainClass
