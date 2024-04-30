package com.jdbc.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import com.jdbc.model.Course;
import com.jdbc.service.DatabaseService;

public class ViewCoursePanel extends JPanel {
    public ViewCoursePanel(DatabaseService databaseService) {
        setLayout(new BorderLayout());

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = createTableModel();
        JTable courseTable = createCourseTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        populateTable(tableModel, databaseService);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        titlePanel.setBackground(new Color(211, 211, 211));

        JLabel titleLabel = new JLabel("View Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        return titlePanel;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Code", "Name", "Category", "Credits", "Level", "Delivery", "Duration"};
        return new DefaultTableModel(columnNames, 0);
    }

    private JTable createCourseTable(DefaultTableModel tableModel) {
        JTable courseTable = new JTable(tableModel);
        customizeTable(courseTable);
        customizeColumnWidths(courseTable);
        return courseTable;
    }

    private void customizeTable(JTable courseTable) {
        JTableHeader tableHeader = courseTable.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 14));
        courseTable.setFont(new Font("Helvetica", Font.PLAIN, 13));
        courseTable.setRowHeight(25);
        courseTable.setDefaultRenderer(Object.class, createCustomRenderer());
    }

    private DefaultTableCellRenderer createCustomRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(245, 250, 255));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                return c;
            }
        };
    }

    private void customizeColumnWidths(JTable courseTable) {
        TableColumnModel columnModel = courseTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(0).setMaxWidth(70);
        columnModel.getColumn(1).setPreferredWidth(240);
        columnModel.getColumn(1).setMinWidth(200);
        columnModel.getColumn(1).setMaxWidth(270);
    }

    private void populateTable(DefaultTableModel tableModel, DatabaseService databaseService) {
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
            tableModel.addRow(row);
        }
    }
}
