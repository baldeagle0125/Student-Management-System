package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.awt.event.*; // Event handling
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Course;

public class AddCoursePanel extends JPanel {
    private JTextField courseNameField;
    private JTextField courseCategoryField;
    private JSpinner courseCreditsSpinner;
    private JComboBox<String> courseLevelCombo;
    private JComboBox<String> courseDeliveryCombo;
    private JComboBox<String> courseDurationCombo;
    private final JButton addButton;
    private final JButton cancelButton;

    public AddCoursePanel(DatabaseService databaseService) {

        setLayout(new BorderLayout()); // Use BorderLayout to accommodate title at the top

        // Create a panel for the title with padding
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding for space at the top
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey background
        
        // Title label at the top (north), centered
        JLabel titleLabel = new JLabel("Add a New Course", SwingConstants.CENTER); // Centered title
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22)); // Set font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title to the center of the title panel
        
        // Add the title panel to the north section
        add(titlePanel, BorderLayout.NORTH);

        // Panel for course Fields and layout details 
        JPanel formPanel = new JPanel(new GridBagLayout());

        
        // GridBagConstraints for positioning and layout settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST; // Left align labels
        
        // Label and text field font settings
        Font labelFont = new Font("Helvetica", Font.BOLD, 18); // Larger font
        Dimension textFieldSize = new Dimension(500, 30); // Wide text fields
        
        // Define the text fields
        courseNameField = new JTextField();
        courseNameField.setPreferredSize(textFieldSize);
        courseCategoryField = new JTextField();
        courseCategoryField.setPreferredSize(textFieldSize);
        
        // Define a common preferred size for wider combo boxes
        Dimension comboBoxSize = new Dimension(250, 30); // Width, Height in pixels
        

        // Course Name
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(labelFont); 
        formPanel.add(courseNameLabel, gbc);
        gbc.gridx = 1; 
        formPanel.add(courseNameField, gbc);

        // Course Category
        gbc.gridx = 0; 
        gbc.gridy = 1; 
        JLabel courseCategoryLabel = new JLabel("Course Category:");
        courseCategoryLabel.setFont(labelFont); 
        formPanel.add(courseCategoryLabel, gbc);
        gbc.gridx = 1; 
        formPanel.add(courseCategoryField, gbc);

        // Course Credits
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        JLabel courseCreditsLabel = new JLabel("Course Credits:");
        courseCreditsLabel.setFont(labelFont); // Apply the same font
        formPanel.add(courseCreditsLabel, gbc);
        courseCreditsSpinner = new JSpinner(new SpinnerNumberModel(30, 30, 240, 30)); // Range 
        courseCreditsSpinner.setPreferredSize(textFieldSize); // Apply the same preferred size
        gbc.gridx = 1; 
        formPanel.add(courseCreditsSpinner, gbc);

        // Course Level
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        JLabel courseLevelLabel = new JLabel("Course Level:");
        courseLevelLabel.setFont(labelFont); // Apply the same font
        formPanel.add(courseLevelLabel, gbc);
        courseLevelCombo = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}); 
        courseLevelCombo.setPreferredSize(comboBoxSize);
        gbc.gridx = 1; 
        formPanel.add(courseLevelCombo, gbc);
        //courseDetailsPanel.add(courseLevelCombo, gbc);

        // Course Delivery
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel courseDeliveryLabel = new JLabel("Course Delivery:");
        courseDeliveryLabel.setFont(labelFont);
        formPanel.add(courseDeliveryLabel, gbc);
        courseDeliveryCombo = new JComboBox<>(new String[]{"Full-time", "Part-time", "Online"}); 
        courseDeliveryCombo.setPreferredSize(comboBoxSize);
        gbc.gridx = 1;
        formPanel.add(courseDeliveryCombo, gbc);
        

        // Course Duration
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel courseDurationLabel = new JLabel("Course Duration:");
        courseDurationLabel.setFont(labelFont);
        formPanel.add(courseDurationLabel, gbc);
        courseDurationCombo = new JComboBox<>(new String[]{"3 Years", "4 Years"});
        courseDurationCombo.setPreferredSize(comboBoxSize);
        gbc.gridx = 1;
        formPanel.add(courseDurationCombo, gbc);

        // Add and Cancel Buttons
        addButton = new JButton("Add Course");
        cancelButton = new JButton("Cancel");

        // Panel for buttons with spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // 20px spacing
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Add action listener for the "Add Course" button
        addButton.addActionListener(e -> addCourse(databaseService, courseNameField, courseCategoryField));

        // Add "Cancel" listener to clear all fields
        cancelButton.addActionListener(e -> clearFields(courseNameField, courseCategoryField));

        // Add keyboard action for Enter key
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == cancelButton) {
                        clearFields(courseNameField, courseCategoryField);
                    } else {
                        addCourse(databaseService, courseNameField, courseCategoryField);
                    }
                }
            }
        };
        addButton.addKeyListener(enterKeyListener); // Add listener to the "Add Course" button
        cancelButton.addKeyListener(enterKeyListener); // Add listener to the "Cancel" button
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void addCourse(DatabaseService databaseService, JTextField... fields) {
        try {
            int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to add this course?",
                "Confirm Addition",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                // Ensure you have all required field values
                Course course = new Course(
                    fields[0].getText(), // Course Name
                    fields[1].getText(), // Course Category
                    Integer.parseInt(fields[2].getText()), // Course Credits (assuming conversion to int)
                    fields[3].getText(), // Course Level
                    fields[4].getText(), // Course Delivery
                    fields[5].getText()  // Course Duration
                    // Add other necessary fields
                );

                // Insert into the database
                databaseService.insertCourse(course);

                // Success feedback
                JOptionPane.showMessageDialog(
                    this,
                    "Course added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Clear the input fields after successful addition
                clearFields(fields);

            } else {
                // Cancellation feedback
                JOptionPane.showMessageDialog(
                    this,
                    "Course addition canceled.",
                    "Canceled",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException nfe) {
            // Handle number format errors (e.g., invalid credits)
            JOptionPane.showMessageDialog(
                this,
                "Invalid input for credits. Please enter a valid number.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException ex) {
            // Handle database errors
            JOptionPane.showMessageDialog(
                this,
                "Database error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
