package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import com.jdbc.service.DatabaseService;
import com.jdbc.model.Course;
import java.sql.SQLException;

public class UpdateCoursePanel extends JPanel {
    public UpdateCoursePanel(DatabaseService databaseService) {
        
        
        setLayout(new BorderLayout()); // Use BorderLayout for flexible arrangement
        

        // Create a panel for the title with additional padding and light grey background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding for space at the top
        titlePanel.setBackground(new Color(222, 222, 222)); // Light grey background
        
        JLabel titleLabel = new JLabel("Update Course Details", SwingConstants.CENTER); // Centered title
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22)); // Set font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title to the center
        
        add(titlePanel, BorderLayout.NORTH); // Add the title panel to the north section

        // Panel for course Fields details with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left
        
        // Components Inputs text are
        gbc.gridx = 0; // Reset grid position
        gbc.gridy = 0; // First row
        JLabel idLabel = new JLabel("Course ID:");
        JTextField idField = new JTextField(20); // ID field with preferred size
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 1; 
        JLabel courseNameLabel = new JLabel("Course Name:");
        JTextField courseNameField = new JTextField(20);
        formPanel.add(courseNameLabel, gbc);
        gbc.gridx = 1; // Move to the second column
        formPanel.add(courseNameField, gbc);

        JLabel courseCategoryLabel = new JLabel("Course Category:");
        JTextField courseCategoryField = new JTextField(20);
        gbc.gridx = 0; // Back to the first column
        gbc.gridy = 2; // Next row
        formPanel.add(courseCategoryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseCategoryField, gbc);

        JLabel courseCreditsLabel = new JLabel("Course Credits:");
        JSpinner courseCreditsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 180, 1));
        gbc.gridx = 0; // First column
        gbc.gridy = 3; // Next row
        formPanel.add(courseCreditsLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseCreditsSpinner, gbc);

        JLabel courseLevelLabel = new JLabel("Course Level:");
        JTextField courseLevelField = new JTextField(20);
        gbc.gridx = 0; // First column
        gbc.gridy = 4; // Next row
        formPanel.add(courseLevelLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseLevelField, gbc);

        JLabel courseDeliveryLabel = new JLabel("Course Delivery:");
        JTextField courseDeliveryField = new JTextField(20);
        gbc.gridx = 0; // First column
        gbc.gridy = 5; // Next row
        formPanel.add(courseDeliveryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseDeliveryField,gbc);
        

        JLabel courseDurationLabel = new JLabel("Course Duration:");
        JTextField courseDurationField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6; // Next row
        formPanel.add(courseDurationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseDurationField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        JButton updateButton = new JButton("Update Course");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        // Add the button panel to the form panel
        gbc.gridx = 1;
        gbc.gridy = 7; // Row for the button panel
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER); // Add the form panel to the center of the BorderLayout

        // Action Listener for "Update Course" button
        updateButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(idField.getText());

                Course course = new Course(
                    courseId,
                    courseNameField.getText(),
                    courseCategoryField.getText(),
                    (int) courseCreditsSpinner.getValue(),
                    courseLevelField.getText(),
                    courseDeliveryField.getText(),
                    courseDurationField.getText()
                );

                databaseService.updateCourse(course);

                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check the ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            idField.setText("");
            courseNameField.setText("");
            courseCategoryField.setText("");
            courseCreditsSpinner.setValue(1);
            courseLevelField.setText("");
            courseDeliveryField.setText("");
            courseDurationField.setText("");
        });
    }
}
