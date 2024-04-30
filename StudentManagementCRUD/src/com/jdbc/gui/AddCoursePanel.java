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
        Dimension textFieldSize = new Dimension(330, 30); // Wide text fields
        
        // Define the text fields
        courseNameField = new JTextField();
        courseNameField.setPreferredSize(textFieldSize);
        courseCategoryField = new JTextField();
        courseCategoryField.setPreferredSize(textFieldSize);
        
        // Define a common preferred size for wider combo boxes
        Dimension comboBoxSize = new Dimension(100, 30); // Width, Height in pixels
       
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

        courseDurationCombo = new JComboBox<>(new String[]{"3 Years", "4 Years"}); // Fixed by adding 'new'
        courseDurationCombo.setPreferredSize(comboBoxSize);

        gbc.gridx = 1;
        formPanel.add(courseDurationCombo, gbc);

        // Add and Cancel Buttons
        // Correct syntax for creating JButton instances
        JButton addButton = new JButton("Add Course"); // Instantiate with "new"
        JButton cancelButton = new JButton("Cancel"); // Same here

        // Panel for buttons with appropriate spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Correct JPanel instantiation

        // Add buttons to the button panel
        buttonPanel.add(cancelButton); // Add the "Cancel" button to the panel
        buttonPanel.add(addButton); // Add the "Add Course" button to the panel

        // GridBagConstraints to position the button panel
        gbc.gridx = 1; // Correct grid position
        gbc.gridy = 6; // Correct grid position
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.EAST; // Align to the right

        // Add the button panel to the form panel
        formPanel.add(buttonPanel, gbc); // Place it in the form panel

        // Add the form panel to the layout
        add(formPanel, BorderLayout.CENTER); // Correct placement in the BorderLayout

        // Add action listener for the "Add Course" button
        addButton.addActionListener(e -> {
            try {
                // Prompt for confirmation before adding the course
                int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to add this course?",
                    "Confirm Addition",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (confirmation == JOptionPane.YES_OPTION) { // Proceed if the user confirms
                    Course course = new Course(
                        courseNameField.getText(),
                        courseCategoryField.getText(),
                        (int) courseCreditsSpinner.getValue(),
                        (String) courseLevelCombo.getSelectedItem(),
                        (String) courseDeliveryCombo.getSelectedItem(),
                        (String) courseDurationCombo.getSelectedItem()
                    );

                    databaseService.insertCourse(course); // Insert into the database

                    // Feedback on success
                    JOptionPane.showMessageDialog(
                        this,
                        "Course added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    // Clear fields after adding
                    clearFields(courseNameField, courseCategoryField); 
                    courseCreditsSpinner.setValue(30); 
                    courseLevelCombo.setSelectedIndex(0); 
                    courseDeliveryCombo.setSelectedIndex(0); 
                    courseDurationCombo.setSelectedIndex(0); 

                } else {
                    // Optionally provide feedback if the user cancels
                    JOptionPane.showMessageDialog(
                        this,
                        "Course addition canceled.",
                        "Canceled",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (SQLException ex) {
                // Handle database-related errors
                JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );

            } catch (NumberFormatException nfe) {
                // Handle number format errors (e.g., invalid course credits)
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please ensure all fields are correct.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception exc) {
                // Handle other unexpected errors
                JOptionPane.showMessageDialog(
                    this,
                    "Unexpected error: " + exc.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Add "Cancel" listener to clear all fields
        cancelButton.addActionListener(e -> {
            clearFields(courseNameField, courseCategoryField); 
            courseCreditsSpinner.setValue(30); 
            courseLevelCombo.setSelectedIndex(0); 
            courseDeliveryCombo.setSelectedIndex(0); 
            courseDurationCombo.setSelectedIndex(0); 
        });
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}
