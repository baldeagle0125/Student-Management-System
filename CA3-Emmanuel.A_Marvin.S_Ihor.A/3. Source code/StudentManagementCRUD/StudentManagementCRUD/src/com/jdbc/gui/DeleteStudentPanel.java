package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Student;
import java.util.List;

public class DeleteStudentPanel extends JPanel {
    private JComboBox<String> studentIdCombo;
    private JTextField nameField, dobField, addressField;

    public DeleteStudentPanel(DatabaseService databaseService) {
        setLayout(new BorderLayout());

        // Setup title and form panels
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        titlePanel.setBackground(new Color(211, 211, 211)); // Light gray
        JLabel titleLabel = new JLabel("Remove a Student", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH); // Add title panel to the north section

        // Form panel with consistent sizing and layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Consistent padding
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        
        // Consistent label font and text field size
        Font labelFont = new Font("Helvetica", Font.BOLD, 16); // Font for labels
        Dimension textFieldSize = new Dimension(330, 30); // Standard text field size
        
        // Student ID ComboBox setup
        JLabel idLabel = new JLabel("Select Student ID:");
        idLabel.setFont(labelFont); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        studentIdCombo = new JComboBox<>();
        studentIdCombo.setPreferredSize(new Dimension(100, 30)); // Standard combo box size
        try {
            List<String> studentIds = databaseService.getAllStudentIds(); // Get all student IDs
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
        formPanel.add(studentIdCombo, gbc); // Add combo box

        // Labels and fields for Name, DOB, Address with consistent sizes
        nameField = new JTextField();
        nameField.setPreferredSize(textFieldSize); // Standard text field size
        nameField.setEditable(false); // Read-only
        
        dobField = new JTextField();
        dobField.setPreferredSize(textFieldSize); // Standard text field size
        dobField.setEditable(false); 
        
        addressField = new JTextField();
        addressField.setPreferredSize(textFieldSize); // Standard text field size
        addressField.setEditable(false); 

        gbc.gridy = 1;
        gbc.gridx = 0; // Start of the "Name" row
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc); // Label

        gbc.gridx = 1; 
        formPanel.add(nameField, gbc); // Field

        gbc.gridy = 2;
        gbc.gridx = 0; 
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont); 
        formPanel.add(dobLabel, gbc);

        gbc.gridx = 1; 
        formPanel.add(dobField, gbc); // Field

        gbc.gridy = 3;
        gbc.gridx = 0; 
        JLabel addressLabel = new JLabel("Full Address:");
        addressLabel.setFont(labelFont); 
        formPanel.add(addressLabel, gbc); // Label

        gbc.gridx = 1; 
        formPanel.add(addressField, gbc); // Field

        // Button panel with "Delete" and "Cancel" buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Delete Student");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Spans the rest of the row
        gbc.anchor = GridBagConstraints.EAST; // Align to the right
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);

        // Action listener for "Delete Student"
        deleteButton.addActionListener(e -> {
            try {
                String selectedStudentId = (String) studentIdCombo.getSelectedItem(); 
                int studentId = Integer.parseInt(selectedStudentId);
                
                databaseService.deleteStudentById(studentId);
                
                JOptionPane.showMessageDialog(
                    this, "Student deleted successfully!", "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    this, "Error deleting student: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(
                    this, "Invalid Student ID. Please select a valid ID.", 
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Action listener for "Cancel"
        cancelButton.addActionListener(e -> {
            studentIdCombo.setSelectedIndex(0); 
            nameField.setText("");
            dobField.setText("");
            addressField.setText("");
        });

        // Item listener for ComboBox to fetch student details
        studentIdCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        String selectedStudentId = (String) studentIdCombo.getSelectedItem(); // Fetch selected item
                        int studentId = Integer.parseInt(selectedStudentId); // Convert to int

                        Student student = databaseService.getStudentById(studentId); // Fetch student details

                        // Ensure all fields are updated correctly
                        if (student != null) {
                            nameField.setText(student.getStudentName());
                            dobField.setText(student.getStudentDOB());
                            addressField.setText(student.getStudentAddress());
                        } else { // Clear fields if student is not found
                            nameField.setText("");
                            dobField.setText("");
                            addressField.setText("");
                        }
                    } catch (SQLException ex) {
                        // Handle SQL exceptions
                        JOptionPane.showMessageDialog(
                            DeleteStudentPanel.this,
                            "Error fetching student details: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    } catch (NumberFormatException ex) {
                        // Handle invalid ID formats
                        JOptionPane.showMessageDialog(
                            DeleteStudentPanel.this,
                            "Invalid Student ID. Please select a valid one.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });



        // KeyAdapter for handling the "Enter" key
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == deleteButton) {
                        deleteButton.doClick();
                    } else if (e.getSource() == cancelButton) {
                        cancelButton.doClick();
                    }
                }
            }
        };

        deleteButton.addKeyListener(enterKeyListener);
        cancelButton.addKeyListener(enterKeyListener);
    }
}
