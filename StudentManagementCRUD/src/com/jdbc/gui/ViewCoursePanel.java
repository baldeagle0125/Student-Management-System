package com.jdbc.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import com.jdbc.model.Course;
import com.jdbc.service.DatabaseService;
import java.sql.SQLException;

public class ViewCoursePanel extends JPanel {
    private DefaultTableModel tableModel; // Hold reference to the table model
    private JTable courseTable; // Hold reference to the JTable
    private DatabaseService databaseService;

    public ViewCoursePanel(DatabaseService databaseService) {
        this.databaseService = databaseService; // Store the service reference
        setLayout(new BorderLayout());

        // Title section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey
        
        JLabel titleLabel = new JLabel("View Courses", SwingConstants.CENTER); // Centered title
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22)); // Font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title to the panel
        
        add(titlePanel, BorderLayout.NORTH); // Add to the north section

        // Table setup
        String[] columnNames = {"Code", "Name", "Category", "Credits", "Level", "Delivery", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);

        // Set custom font and style for the table header
        JTableHeader tableHeader = courseTable.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 14)); // Header font

        courseTable.setFont(new Font("Helvetica", Font.PLAIN, 13)); // Content font
        courseTable.setRowHeight(25); // Set row height

        // Custom cell renderer for alternating row colors
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) { // Change the color if the row is not selected
                    if (row % 2 == 0) { // Even rows with light blue
                        c.setBackground(new Color(245, 250, 255));
                    } else { // Odd rows with white
                        c.setBackground(Color.WHITE);
                    }
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // 10px left padding
                return c;
            }
        };

        courseTable.setDefaultRenderer(Object.class, customRenderer); // Apply custom renderer
        
        JScrollPane scrollPane = new JScrollPane(courseTable); // Enable scrolling
        add(scrollPane, BorderLayout.CENTER); // Add to the center

        // Refresh button setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0)); // 25px bottom padding
        JButton refreshButton = new JButton("Refresh Page"); // Create a Refresh button
        refreshButton.addActionListener(e -> refreshTable()); // Refresh the table on click
        buttonPanel.add(refreshButton); // Add the Refresh button to the panel
        
        add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the south section

        // Fetch data and populate the table
        refreshTable(); // Load course data on initialization
    }

    // Method to refresh the course table data
    private void refreshTable() {
        try {
            tableModel.setRowCount(0); // Clear existing rows
            List<Course> courses = databaseService.getAllCourse(); // Fetch all courses
            for (Course course : courses) {
                Object[] row = {
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCourseCategory(),
                    course.getCourseCredits(),
                    course.getCourseLevel(),
                    course.getCourseDelivery(),
                    course.getCourseDuration()
                };
                tableModel.addRow(row); // Add the row to the table model
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
