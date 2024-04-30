package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Course;
import java.util.List;

public class DeleteCoursePanel extends JPanel {
    private JComboBox<String> courseIdCombo;
    private JTextField courseNameField, courseCategoryField, courseLevelField;

    public DeleteCoursePanel(DatabaseService databaseService) {
        setLayout(new BorderLayout());

        // Title setup
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        titlePanel.setBackground(new Color(211, 211, 211)); // Light gray
        JLabel titleLabel = new JLabel("Remove a Course", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH); // Add title panel to the north section

        // Form panel setup with consistent styling
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Consistent padding
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        Font labelFont = new Font("Helvetica", Font.BOLD, 16); // Consistent font
        Dimension textFieldSize = new Dimension(330, 30); // Standard text field size
        
        // Course ID ComboBox setup
        JLabel idLabel = new JLabel("Select Course ID:");
        idLabel.setFont(labelFont); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        courseIdCombo = new JComboBox<>();
        courseIdCombo.setPreferredSize(new Dimension(100, 30)); // Standard combo box size
        try {
            List<String> courseIds = databaseService.getAllCourseIds(); // Get all student IDs
            for (String id : courseIds) {
                courseIdCombo.addItem(id); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Error fetching student IDs: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }

        gbc.gridx = 1;
        formPanel.add(courseIdCombo, gbc); // Add combo box to the panel

        // Course Name field
        courseNameField = new JTextField();
        courseNameField.setPreferredSize(textFieldSize); 
        courseNameField.setEditable(false); // Read-only
        
        // Course Category field
        courseCategoryField = new JTextField();
        courseCategoryField.setPreferredSize(textFieldSize); 
        courseCategoryField.setEditable(false); 
        
        // Course Level field
        courseLevelField = new JTextField();
        courseLevelField.setPreferredSize(textFieldSize); 
        courseLevelField.setEditable(false); 

        // Add course fields to the form panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(labelFont);
        formPanel.add(courseNameLabel, gbc); // Label
        
        gbc.gridx = 1; 
        formPanel.add(courseNameField, gbc); // Field

        gbc.gridy = 2;
        gbc.gridx = 0; 
        JLabel courseCategoryLabel = new JLabel("Course Category:");
        courseCategoryLabel.setFont(labelFont); 
        formPanel.add(courseCategoryLabel, gbc); // Label

        gbc.gridx = 1;
        formPanel.add(courseCategoryField, gbc); // Field

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel courseLevelLabel = new JLabel("Course Level:");
        courseLevelLabel.setFont(labelFont); 
        formPanel.add(courseLevelLabel, gbc); // Label
        
        gbc.gridx = 1; 
        formPanel.add(courseLevelField, gbc); // Field

        // Panel with "Delete" and "Cancel" buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Delete Course");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        gbc.anchor = GridBagConstraints.EAST; 
        formPanel.add(buttonPanel, gbc); // Add the button panel to the form panel
        
        add(formPanel, BorderLayout.CENTER); // Place form panel in BorderLayout

        // Action listener for "Delete Course"
        deleteButton.addActionListener(e -> {
            try {
                String selectedCourseId = (String) courseIdCombo.getSelectedItem(); 
                int courseId = Integer.parseInt(selectedCourseId);

                databaseService.deleteCourseById(courseId); // Corrected method call

                JOptionPane.showMessageDialog(
                    this, "Course deleted successfully!", "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(
                    this, "Invalid Course ID. Please select a valid one.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    this, "Error deleting course: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // Action listener for "Cancel"
        cancelButton.addActionListener(e -> {
            courseIdCombo.setSelectedIndex(0); // Reset ComboBox
            courseNameField.setText(""); 
            courseCategoryField.setText(""); 
            courseLevelField.setText(""); 
        });

        // Item listener for ComboBox to fetch course details
        courseIdCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        String selectedCourseId = (String) courseIdCombo.getSelectedItem(); 
                        int courseId = Integer.parseInt(selectedCourseId); 

                        Course course = databaseService.getCourseById(courseId); // Fetch course details

                        // Populate text fields with course details if found
                        if (course != null) {
                            courseNameField.setText(course.getCourseName());
                            courseCategoryField.setText(course.getCourseCategory());
                            courseLevelField.setText(course.getCourseLevel());
                        } else { 
                            // Clear fields if course isn't found
                            courseNameField.setText("");
                            courseCategoryField.setText("");
                            courseLevelField.setText("");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(
                            DeleteCoursePanel.this,
                            "Error fetching course details: " + ex.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                            DeleteCoursePanel.this,
                            "Invalid Course ID. Please select a valid one.", 
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
