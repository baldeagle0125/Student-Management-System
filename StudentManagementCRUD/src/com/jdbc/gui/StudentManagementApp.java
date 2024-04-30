package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import com.jdbc.service.DatabaseService;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StudentManagementApp extends JFrame {
    private final DatabaseService databaseService;

    public StudentManagementApp() {
        super("Student Management System");
        this.databaseService = new DatabaseService();

        // Set full screen and remove window decorations if required
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame to full-screen
        setUndecorated(false); // Keep window borders and title bar

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the login panel
        LoginPanel loginPanel = new LoginPanel(databaseService);

        // Set the login listener to switch panels upon successful login
        loginPanel.setLoginListener(success -> {
            if (success) {
                remove(loginPanel); // Remove the login panel
                JPanel mainPanel = createStudentManagementPanel(""); // Example with a logged-in user
                add(mainPanel, BorderLayout.CENTER); // Add the main panel to the center
                validate(); // Refresh the layout
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginPanel, BorderLayout.CENTER); // Add login panel to the frame
        setVisible(true);
    }

    private JPanel createStudentManagementPanel(String loggedInUserName) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(222, 222, 222)); // Light gray

        // Top Header
        JPanel topTitlePanel = new JPanel(new BorderLayout());
        topTitlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topTitlePanel.setBackground(new Color(92, 132, 161)); // Snowy mountain Color

        // Include the logged-in user's name in the label
        String topTitleText = "Logged in: " + loggedInUserName;
        JLabel topTitleLabel = new JLabel(topTitleText, SwingConstants.LEFT);
        topTitleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        topTitlePanel.add(topTitleLabel, BorderLayout.CENTER); // Add the label to the top panel

        mainPanel.add(topTitlePanel, BorderLayout.NORTH); // Add topTitlePanel to the top

        // Create a tabbed pane for menu items
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(new Color(239, 239, 239)); // Light gray background
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Tab font

        // GROUP 1: Student-related tabs
        addTab(tabbedPane, "STUDENTS", null); // Title tab
        addTab(tabbedPane, "Display", new ViewStudentPanel(databaseService));
        addTab(tabbedPane, "Add New", new AddStudentPanel(databaseService));
        addTab(tabbedPane, "Update", new UpdateStudentPanel(databaseService));
        addTab(tabbedPane, "Remove", new DeleteStudentPanel(databaseService));

        // GROUP 2: Course-related tabs
        addTab(tabbedPane, "COURSES", null); // Title tab
        addTab(tabbedPane, "Display", new ViewCoursePanel(databaseService));
        addTab(tabbedPane, "Add New", new AddCoursePanel(databaseService));
        addTab(tabbedPane, "Update", new UpdateCoursePanel(databaseService));

        // GROUP 4: ACCOUNT-related tabs
        addTab(tabbedPane, "ACCOUNT", null); // Title tab
        addTab(tabbedPane, "Logout", null); // Logout tab

        // Attach a change listener to detect when the "Logout" tab is selected
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1) {
                    // Ask for confirmation before logging out
                    int confirmation = JOptionPane.showConfirmDialog(
                        StudentManagementApp.this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        dispose(); // Close the frame (ends the session)
                    } else {
                        // If user clicks "No", switch back to another tab
                        tabbedPane.setSelectedIndex(0); // Back to index "STUDENTS"
                    }
                }
            }
        });

        mainPanel.add(tabbedPane, BorderLayout.CENTER); // Add tabbed pane to the center
        return mainPanel;
    }

    private void addTab(JTabbedPane tabbedPane, String title, JPanel content) {
        tabbedPane.addTab(title, content);
    }

    public static void main(String[] args) {
        new StudentManagementApp(); // Launch the application
    }
}
