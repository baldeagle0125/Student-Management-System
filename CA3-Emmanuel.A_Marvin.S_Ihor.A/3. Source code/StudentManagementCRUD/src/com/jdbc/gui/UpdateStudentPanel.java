package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Student;
import java.util.List;

public class UpdateStudentPanel extends JPanel {
    private JComboBox<String> studentIdCombo;
    private JTextField nameField, dobField, addressField, emailField, phoneField, balanceField;

    public UpdateStudentPanel(DatabaseService databaseService) {
        setLayout(new BorderLayout()); // For flexible arrangement

        // Title Panel with Consistent Padding and Background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey
        
        JLabel titleLabel = new JLabel("Update Student Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // Font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH); // Add the title panel to the north
        
        // Form Panel with GridBagLayout and Consistent Layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Consistent padding
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        
        // Consistent Label Font and Text Field Size
        Font labelFont = new Font("Helvetica", Font.BOLD, 16); 
        Dimension textFieldSize = new Dimension(330, 30); // Standard size for text fields
        
        // Student ID ComboBox Setup
        JLabel idLabel = new JLabel("Select Student ID:");
        idLabel.setFont(labelFont); // Consistent font
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        studentIdCombo = new JComboBox<>();
        studentIdCombo.setPreferredSize(new Dimension(100, 30)); // Standard combo box size
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
        formPanel.add(studentIdCombo, gbc); // Add combo box

        // Text Fields with Consistent Dimensions
        nameField = new JTextField();
        nameField.setPreferredSize(textFieldSize); 
        dobField = new JTextField();
        dobField.setPreferredSize(textFieldSize); 
        addressField = new JTextField();
        addressField.setPreferredSize(textFieldSize); 
        emailField = new JTextField();
        emailField.setPreferredSize(textFieldSize); 
        phoneField = new JTextField();
        phoneField.setPreferredSize(textFieldSize); 
        balanceField = new JTextField();
        balanceField.setPreferredSize(textFieldSize); 

        // Layout with Consistent Positions and Sizes
        gbc.gridy = 1;
        gbc.gridx = 0; 
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont); 
        formPanel.add(nameLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(nameField, gbc); 

        gbc.gridy = 2;
        gbc.gridx = 0; 
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont); 
        formPanel.add(dobLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(dobField, gbc); 

        gbc.gridy = 3;
        gbc.gridx = 0; 
        JLabel addressLabel = new JLabel("Full Address:");
        addressLabel.setFont(labelFont); 
        formPanel.add(addressLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(addressField, gbc); 

        gbc.gridy = 4;
        gbc.gridx = 0; 
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont); 
        formPanel.add(emailLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(emailField, gbc); 

        gbc.gridy = 5;
        gbc.gridx = 0; 
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont); 
        formPanel.add(phoneLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(phoneField, gbc); 

        gbc.gridy = 6;
        gbc.gridx = 0; 
        JLabel balanceLabel = new JLabel("Balance Amount:");
        balanceLabel.setFont(labelFont); 
        formPanel.add(balanceLabel, gbc); 

        gbc.gridx = 1;
        formPanel.add(balanceField, gbc); 

        // Panel with "Update" and "Cancel" Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align to the right
        JButton updateButton = new JButton("Update Student");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton); 

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Spans the rest of the row
        gbc.anchor = GridBagConstraints.EAST; // Align to the right
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER); // Add the form panel to the center

        
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
                        JOptionPane.showMessageDialog(
                            UpdateStudentPanel.this,
                            "Error fetching student details: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    } catch (NumberFormatException ex) {
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

        // KeyAdapter for handling the "Enter" key
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
