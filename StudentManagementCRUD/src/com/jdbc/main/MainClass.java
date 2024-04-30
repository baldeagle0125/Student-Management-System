package com.jdbc.main;

import com.jdbc.model.Student;
import com.jdbc.service.DatabaseService;
import java.util.Scanner;

/**
 * @author mazds
 */
public class MainClass {

    public static void main(String[] args) {
        
        DatabaseService databaseService = new DatabaseService();
        try(Scanner scanner = new Scanner(System.in);){
            
            boolean isRunning = true;
            
            while(isRunning){
                System.out.println("Enter choice");
                System.out.println("1. Insert a student");
                System.out.println("2. Select all");
                System.out.println("3. Select Student by Id");
                System.out.println("4. Delete Student");
                System.out.println("5. Update Student");
                System.out.println("6. Exit");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice){
                    case 1:
                        System.out.println("Enter Name, Address, and Balance.");
                        databaseService.insertStudent(new Student(scanner.nextLine(), scanner.nextLine(), Double.parseDouble(scanner.nextLine())));
                        break;
                    case 2:
                        databaseService.getAllStudent();
                        break;
                    case 3:
                        System.out.println("Enter Student Id:");
                        databaseService.getStudentById(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 4:
                        System.out.println("Enter Student Id:");
                        databaseService.deleteStudentById(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 5:
                        System.out.println("Enter Student Id:");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        boolean isFound = databaseService.getStudentById(updateId);
                        
                        if (isFound){
                            System.out.println("Enter new name, address, balance");
                            Student student = new Student(updateId, scanner.nextLine(), 
                                    scanner.nextLine(), Double.parseDouble(scanner.nextLine()));
                            databaseService.updateStudent(student);
                        }
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        isRunning = false;
                        break;
                    default:
                        break;
                }// End switch
            }// End while
        } catch (Exception e) {
            throw new RuntimeException("Something went WRONG: " +e);
        }
    }// End main
}// End main
