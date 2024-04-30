package com.jdbc.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import com.jdbc.model.Student;
import com.jdbc.service.DatabaseService;

public class ViewStudentPanel extends JPanel {
    private final DatabaseService databaseService;
    private final DefaultTableModel tableModel;
    private final JTable studentTable;

    public ViewStudentPanel(DatabaseService databaseService) {
        this.databaseService = databaseService;
        setLayout(new BorderLayout());

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        tableModel = createTableModel();
        studentTable = createStudentTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        titlePanel.setBackground(new Color(211, 211, 211));

        JLabel titleLabel = new JLabel("Display All Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        return titlePanel;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Date of Birth", "Address", "Balance"};
        return new DefaultTableModel(columnNames, 0);
    }

    private JTable createStudentTable(DefaultTableModel tableModel) {
        JTable studentJTable = new JTable(tableModel);
        customizeTable(studentJTable);
        return studentJTable;
    }

    private void customizeTable(JTable studentTable) {
        studentTable.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 14));
        studentTable.setFont(new Font("Helvetica", Font.PLAIN, 12));
        studentTable.setRowHeight(25);

        customizeColumnWidths(studentTable);
        addRowColors(studentTable);
    }

    private void customizeColumnWidths(JTable studentTable) {
        TableColumn idColumn = studentTable.getColumnModel().getColumn(0);
        idColumn.setPreferredWidth(50);
        idColumn.setMinWidth(30);
        idColumn.setMaxWidth(60);

        TableColumn addressColumn = studentTable.getColumnModel().getColumn(5);
        addressColumn.setPreferredWidth(250);
        addressColumn.setMinWidth(200);
        addressColumn.setMaxWidth(300);

        TableColumn balanceColumn = studentTable.getColumnModel().getColumn(6);
        balanceColumn.setPreferredWidth(80);
        balanceColumn.setMinWidth(60);
        balanceColumn.setMaxWidth(100);
    }

    private void addRowColors(JTable studentTable) {
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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

        studentTable.setDefaultRenderer(Object.class, customRenderer);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        JButton refreshButton = new JButton("Refresh Page");
        refreshButton.addActionListener(e -> refreshTable());
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Student> students = databaseService.getAllStudent();
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
    }
}
