package com.jdbc.gui;

import javax.swing.*; // Import Swing components
import java.awt.*; // For layout and design components
import com.jdbc.service.DatabaseService; // For interacting with the database
import com.jdbc.model.Student; // For creating Student objects
import java.sql.SQLException; // For SQL exception handling
import java.awt.event.*; // Event handling

public class AddStudentPanel extends JPanel {
    public AddStudentPanel(DatabaseService databaseService) {
        setLayout(new BorderLayout()); // Use BorderLayout for flexible component placement
        
        // Create a panel for the title with additional padding
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding for space at the top
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey background
        
        // Title label at the top (north), centered
        JLabel titleLabel = new JLabel("Add a New Student", SwingConstants.CENTER); // Centered title
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22)); // Set font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title to the center of the title panel
        
        // Add the title panel to the north section
        add(titlePanel, BorderLayout.NORTH);

        // Create a panel with GridBagLayout for the form fields and buttons
        JPanel formPanel = new JPanel(new GridBagLayout());

        // GridBagConstraints for positioning and layout settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        
        // Labels adjustments
        Font labelFont = new Font("Helvetica", Font.BOLD, 16); 

        // Input Text Field Box Adjustments
        Dimension textFieldSize = new Dimension(330, 30); // Wide, High in pixels
        
        JTextField nameField = new JTextField(); // Input for Name
        nameField.setPreferredSize(textFieldSize); 
        JTextField emailField = new JTextField(); // Input for Email
        emailField.setPreferredSize(textFieldSize);
        JTextField phoneField = new JTextField(); // Input for Phone
        phoneField.setPreferredSize(textFieldSize);
        
        JTextField dobField = new JTextField(); // Input for Date of Birth
        dobField.setPreferredSize(textFieldSize);
        JTextField addressField = new JTextField(); // Input for Address
        addressField.setPreferredSize(textFieldSize);
        JTextField balanceField = new JTextField(); // Input for Balance
        balanceField.setPreferredSize(textFieldSize);

        // Labels and text fields to the form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont); 
        formPanel.add(nameLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont); 
        formPanel.add(emailLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont); 
        formPanel.add(phoneLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont); 
        formPanel.add(dobLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel addressLabel = new JLabel("Full Address:");
        addressLabel.setFont(labelFont); 
        formPanel.add(addressLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel balanceLabel = new JLabel("Balance Amount:");
        balanceLabel.setFont(labelFont); 
        formPanel.add(balanceLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(balanceField, gbc);

        // Button panel for "Add Student" and "Cancel" buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons on the left
        JButton addButton = new JButton("Add Student"); // Button to add the student
        JButton cancelButton = new JButton("Cancel"); // Button to clear all fields
        
        // Add the buttons to the button panel
        buttonPanel.add(cancelButton); // Add the "Cancel" button first
        buttonPanel.add(addButton); // Then add the "Add Student" button
        
        // Add the button panel to the form panel
        gbc.gridx = 1;
        gbc.gridy = 6; // Adjust the row index for the buttons
        gbc.anchor = GridBagConstraints.EAST; // Align to the RIGHT
        formPanel.add(buttonPanel, gbc); // Add the button panel to the form panel
            
        add(formPanel, BorderLayout.CENTER); // Add the form panel to the center of the BorderLayout
        
        // Action Listener for "Add Student"
        addButton.addActionListener(e -> {
            addStudent(databaseService, nameField, emailField, phoneField, dobField, addressField, balanceField);
        });
        
        // Action Listener for "Cancel" to clear all fields
        cancelButton.addActionListener(e -> {
            clearFields(nameField, emailField, phoneField, dobField, addressField, balanceField);
        });
        
        // Add keyboard action for Enter key to trigger the same events
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == cancelButton) {
                        clearFields(nameField, emailField, phoneField, dobField, addressField, balanceField);
                    } else {
                        addStudent(databaseService, nameField, emailField, phoneField, dobField, addressField, balanceField);
                    }
                }
            }
        };
        // Apply the KeyAdapter to relevant buttons
        addButton.addKeyListener(enterKeyListener); // Trigger "Add Student" with Enter
        cancelButton.addKeyListener(enterKeyListener); // Trigger "Cancel" with Enter
    }
    
    // Helper Method to clear text fields when the Record is successfully added
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
        
    private void addStudent(DatabaseService databaseService, JTextField nameField, JTextField emailField, JTextField phoneField, JTextField dobField, JTextField addressField, JTextField balanceField) {
    try {
        // Confirm before adding the student
        int confirmation = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to add this student?",
            "Confirm Addition",
            JOptionPane.YES_NO_OPTION
        );

        // Only proceed if the user confirms
        if (confirmation == JOptionPane.YES_OPTION) {
            // Assign result to a variable before passing to the Student constructor
            double balance = Double.parseDouble(balanceField.getText());

            // Create the Student object with the provided fields
            Student student = new Student(
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                dobField.getText(),
                addressField.getText(),
                balance
            );

            // Insert the student into the database
            databaseService.insertStudent(student);

            // Notify the user of success
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the fields after successful addition
            clearFields(nameField, emailField, phoneField, dobField, addressField, balanceField);
        } else {
            // Optionally, handle the case when the user cancels
            JOptionPane.showMessageDialog(this, "Student addition canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException nfe) {
        // Handle number format exceptions
        JOptionPane.showMessageDialog(this, "Invalid balance input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        // Handle SQL exceptions
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}

