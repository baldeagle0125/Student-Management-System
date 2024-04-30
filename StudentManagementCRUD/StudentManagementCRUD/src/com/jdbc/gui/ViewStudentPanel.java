package com.jdbc.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import com.jdbc.model.Student;
import com.jdbc.service.DatabaseService;

public class ViewStudentPanel extends JPanel {
    private DatabaseService databaseService;
    private DefaultTableModel tableModel; // Hold reference to the table model
    private JTable studentTable; // Hold reference to the JTable

    public ViewStudentPanel(DatabaseService databaseService) {
        this.databaseService = databaseService; // Store the service reference
        setLayout(new BorderLayout());

        // Title section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Padding
        titlePanel.setBackground(new Color(211, 211, 211)); // Light grey
        
        JLabel titleLabel = new JLabel("Display All Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22)); // Font and size
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title to the panel
        
        add(titlePanel, BorderLayout.NORTH); // Add to the north

        // Table setup
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Date of Birth", "Address", "Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        
        // Set custom font and style for the table header
        JTableHeader tableHeader = studentTable.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 14)); // Font for the header

        studentTable.setFont(new Font("Helvetica", Font.PLAIN, 12)); // Content font
        studentTable.setRowHeight(25); // Set row height
        
        // Set the preferred, minimum, and maximum width for the Table
        TableColumn idColumn = studentTable.getColumnModel().getColumn(0);
        idColumn.setPreferredWidth(50); // Preferred width
        idColumn.setMinWidth(30); // Minimum width
        idColumn.setMaxWidth(60); // Maximum width
        
        TableColumn addressColumn = studentTable.getColumnModel().getColumn(5);
        addressColumn.setPreferredWidth(250); // Increase preferred width for Address
        addressColumn.setMinWidth(200); // Set minimum width for Address
        addressColumn.setMaxWidth(300); // Set maximum width for Address

        TableColumn balanceColumn = studentTable.getColumnModel().getColumn(6);
        balanceColumn.setPreferredWidth(80); // Reduce preferred width for Balance
        balanceColumn.setMinWidth(60); // Minimum width for Balance
        balanceColumn.setMaxWidth(100); // Maximum width for Balance

        // Add alternating row colors
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
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

        studentTable.setDefaultRenderer(Object.class, customRenderer); // Apply custom renderer
        
        // Add the table to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(studentTable); 
        add(scrollPane, BorderLayout.CENTER); // Add to the center

        // Refresh button setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0)); // 20px bottom padding
        JButton refreshButton = new JButton("Refresh Page"); // Create a Refresh button
        refreshButton.addActionListener(e -> refreshTable()); // Refresh the table on click
        buttonPanel.add(refreshButton); // Add the Refresh button to the panel
        
        add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the south section

        // Initially load the student data
        refreshTable(); // Load student data on initialization
    }

    // Method to refresh the student table data
    private void refreshTable() {
        try {
            tableModel.setRowCount(0); // Clear existing rows
            List<Student> students = databaseService.getAllStudent(); // Fetch all students
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getStudentEmail(),
                    student.getStudentPhone(),
                    student.getStudentDOB(),
                    student.getStudentAddress(),
                    student.getStudentBalance()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Error fetching student data: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
