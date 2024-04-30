package com.jdbc.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import com.jdbc.model.Course;
import com.jdbc.service.DatabaseService;
import java.sql.SQLException;

public class ViewCoursePanel extends JPanel {
    public ViewCoursePanel(DatabaseService databaseService) {
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
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable courseTable = new JTable(tableModel);

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
        
        // Set the preferred, minimum, and maximum width for the first column
        TableColumn codeColumn = courseTable.getColumnModel().getColumn(0);
        codeColumn.setPreferredWidth(50); // Preferred width for "Code"
        codeColumn.setMinWidth(30); // Minimum width
        codeColumn.setMaxWidth(70); // Maximum width

        // Adjust the "Name" column
        TableColumn nameColumn = courseTable.getColumnModel().getColumn(1);
        nameColumn.setPreferredWidth(240); // Preferred width for "Name"
        nameColumn.setMinWidth(200); // Minimum width
        nameColumn.setMaxWidth(270); // Maximum width
        
        JScrollPane scrollPane = new JScrollPane(courseTable); // Enable scrolling
        add(scrollPane, BorderLayout.CENTER); // Add to the center
        
        // Fetch data and populate the table
        try {
            List<Course> courses = databaseService.getAllCourse();
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
