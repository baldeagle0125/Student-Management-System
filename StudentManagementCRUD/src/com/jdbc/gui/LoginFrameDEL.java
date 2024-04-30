package com.jdbc.gui;

import javax.swing.*; // Swing components
import java.awt.*; // Layouts and components
import com.jdbc.service.DatabaseService; // Service to interact with the database
import java.sql.SQLException; // For handling SQL exceptions

public class LoginFrameDEL extends JFrame {
    private DatabaseService databaseService;

    public LoginFrameDEL() {
        super("Student Management System - Login"); // Set frame title
        this.databaseService = new DatabaseService(); // Instantiate database service

        setLayout(new BorderLayout()); // Use BorderLayout
        setSize(800, 600); // Set the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application on exit
        
        // Create and add the LoginPanel
        LoginPanel loginPanel = new LoginPanel(databaseService);
        add(loginPanel, BorderLayout.CENTER);

        setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        new LoginFrameDEL(); // Launch the login frame
    }
}
