package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Student;
import java.sql.SQLException;
import java.util.List;

public class UpdateStudentPanel extends JPanel {
    private JComboBox<String> studentIdCombo;
    private JTextField nameField, dobField, addressField, emailField, phoneField, balanceField;

    public UpdateStudentPanel(DatabaseService databaseService) {
        setLayout(new BorderLayout()); // For flexible arrangement

        // Title panel with consistent padding and light grey background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding for space at the top
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey background
        
        JLabel titleLabel = new JLabel("Update Student Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH); // Add the title panel to the north
        
        // Form panel with consistent layout and components
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        
        Font labelFont = new Font("Helvetica", Font.BOLD, 18); // Consistent font
        Dimension textFieldSize = new Dimension(330, 30); // Consistent text field size
        
        // Student ID ComboBox setup
        JLabel idLabel = new JLabel("Select Student ID:");
        idLabel.setFont(labelFont); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        studentIdCombo = new JComboBox<>();
        studentIdCombo.setPreferredSize(new Dimension(100, 30)); // Adjusted combo box size
        try {
            List<String> studentIds = databaseService.getAllStudentIds(); 
            for (String id : studentIds) {
                studentIdCombo.addItem(id); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Error fetching student IDs: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }

        gbc.gridx = 1;
        formPanel.add(studentIdCombo, gbc);

        // Labels and text fields for the rest of the information
        nameField = new JTextField(30);
        nameField.setPreferredSize(textFieldSize); 
        dobField = new JTextField(30);
        dobField.setPreferredSize(textFieldSize); 
        addressField = new JTextField(30);
        addressField.setPreferredSize(textFieldSize); 
        emailField = new JTextField(30);
        emailField.setPreferredSize(textFieldSize); 
        phoneField = new JTextField(30);
        phoneField.setPreferredSize(textFieldSize); 
        balanceField = new JTextField(30);
        balanceField.setPreferredSize(textFieldSize); 
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont); 
        formPanel.add(nameLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont); 
        formPanel.add(dobLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel addressLabel = new JLabel("Full Address:");
        addressLabel.setFont(labelFont); 
        formPanel.add(addressLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont); 
        formPanel.add(emailLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont); 
        formPanel.add(phoneLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel balanceLabel = new JLabel("Balance Amount:");
        balanceLabel.setFont(labelFont); 
        formPanel.add(balanceLabel, gbc); 
        gbc.gridx = 1;
        formPanel.add(balanceField, gbc);
        
        // Panel with "Update" and "Cancel" buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        JButton updateButton = new JButton("Update Student");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton); 

        // Add the button panel to the form panel
        gbc.gridx = 1;
        gbc.gridy = 7; // Adjusted row for the button panel
        gbc.gridwidth = 2; // Spanning across two columns
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER); // Add the form panel to the center

        // Action listener for the "Update Student" button
        updateButton.addActionListener(e -> {
            try {
                // Check if the selected item is not null
                Object selectedItem = studentIdCombo.getSelectedItem();
                if (selectedItem == null) {
                    throw new NumberFormatException("No student ID selected.");
                }

                int studentId = Integer.parseInt(selectedItem.toString()); // Safely convert to integer

                // Ensure balanceField contains a valid number
                double balance = 0;
                String balanceText = balanceField.getText();
                if (balanceText != null && !balanceText.isEmpty()) {
                    balance = Double.parseDouble(balanceText);
                }

                // Ask for confirmation before updating the student
                int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to update this student?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    // If the user confirms, update the student
                    Student student = new Student(
                        studentId,
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        dobField.getText(),
                        addressField.getText(),
                        balance // Safe parsing
                    );

                    databaseService.updateStudent(student);

                    JOptionPane.showMessageDialog(
                        this,
                        "Student updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    // If the user cancels, do nothing
                    JOptionPane.showMessageDialog(
                        this,
                        "Update operation canceled.",
                        "Canceled",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (NumberFormatException nfe) {
                // Error handling for invalid inputs
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please check the data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException ex) {
                // Error handling for SQL exceptions
                JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        // Action listener for the "Cancel" button
        cancelButton.addActionListener(e -> {
            nameField.setText("");
            dobField.setText("");
            addressField.setText("");
            emailField.setText("");
            phoneField.setText("");
            balanceField.setText("");
        });

        // Item listener for ComboBox to fetch student details
        studentIdCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        String selectedStudentId = (String) studentIdCombo.getSelectedItem(); 
                        int studentId = Integer.parseInt(selectedStudentId);

                        Student student = databaseService.getStudentById(studentId); 

                        if (student != null) { 
                            nameField.setText(student.getStudentName());
                            dobField.setText(student.getStudentDOB());
                            addressField.setText(student.getStudentAddress());
                            emailField.setText(student.getStudentEmail());
                            phoneField.setText(student.getStudentPhone());
                            balanceField.setText(Double.toString(student.getStudentBalance()));
                        } else { 
                            nameField.setText("");
                            dobField.setText("");
                            addressField.setText("");
                            emailField.setText("");
                            phoneField.setText("");
                            balanceField.setText("");
                        }
                    } catch (SQLException ex) {
                        // Handle SQL exceptions
                        JOptionPane.showMessageDialog(
                            UpdateStudentPanel.this,
                            "Error fetching student details: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    } catch (NumberFormatException ex) {
                        // Handle invalid ID formats
                        JOptionPane.showMessageDialog(
                            UpdateStudentPanel.this,
                            "Invalid Student ID. Please select a valid one.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // KeyAdapter for handling "Enter" key actions
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == updateButton) {
                        updateButton.doClick(); 
                    } else if (e.getSource() == cancelButton) {
                        cancelButton.doClick(); 
                    }
                }
            }
        };

        updateButton.addKeyListener(enterKeyListener); 
        cancelButton.addKeyListener(enterKeyListener); 
    }
}
