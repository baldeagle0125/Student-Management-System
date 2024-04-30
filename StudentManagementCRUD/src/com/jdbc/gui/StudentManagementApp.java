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
        
        // Top Header
        JPanel topTitlePanel = new JPanel(new BorderLayout());
        topTitlePanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 0)); 
        //topTitlePanel.setBackground(new Color (73, 89, 105));
        topTitlePanel.setBackground(new Color(92,132,161)); // Snowy mountain Color
        
        // Include the logged-in user's name in the label
        String topTitleText = "Staff Logged in " + loggedInUserName; 
        JLabel topTitleLabel = new JLabel(topTitleText, SwingConstants.LEFT);
        topTitleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        topTitlePanel.add(topTitleLabel, BorderLayout.CENTER); // Add the label to the top panel

        add(topTitlePanel, BorderLayout.NORTH); // Add topTitlePanel to the top
                
        // Blue color for selected tabs 
        UIManager.put("TabbedPane.selected", new Color(173, 216, 230)); 

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT); // Position the tabs on the left

        // Set the desired font for tab labels
        Font tabFont = new Font("Helvetica", Font.BOLD, 14); // Bold font for tab titles

        // GROUP 1: Student-related tabs
        addCenteredTab(tabbedPane, "STUDENTS", new JPanel(), tabFont); // Centered, light gray
        tabbedPane.setEnabledAt(0, false); // Disable the Student Menu tab to prevent interaction
        
        // Left-aligned
        addTabWithCustomMargin(tabbedPane, "Display", new ViewStudentPanel(databaseService), tabFont);
        addTabWithCustomMargin(tabbedPane, "Add New", new AddStudentPanel(databaseService), tabFont); 
        addTabWithCustomMargin(tabbedPane, "Update", new UpdateStudentPanel(databaseService), tabFont);
        addTabWithCustomMargin(tabbedPane, "Remove", new DeleteStudentPanel(databaseService), tabFont);
        
        // Create and Disable Tab just to use as separator
        JPanel emptyPanel = new JPanel(); // An empty panel for a placeholder tab
        addTabWithCustomMargin(tabbedPane, "", emptyPanel, tabFont); // Creates a tab with empty content
        tabbedPane.setEnabledAt(5, false); // Disable the Course Menu tab
        
        // GROUP 2: Course-related tabs
        addCenteredTab(tabbedPane, "COURSES", new JPanel(), tabFont); // Centered, light gray
        tabbedPane.setEnabledAt(6, false); // Disable the Course Menu tab
        
        addTabWithCustomMargin(tabbedPane, "Display", new ViewCoursePanel(databaseService), tabFont);
        addTabWithCustomMargin(tabbedPane, "Add New", new AddCoursePanel(databaseService), tabFont);
        addTabWithCustomMargin(tabbedPane, "Update", new UpdateCoursePanel(databaseService), tabFont);
        addTabWithCustomMargin(tabbedPane, "Remove", new DeleteCoursePanel(databaseService), tabFont);
        
        // Create and Disable Tab just to use as separator
        JPanel emptyPanel2 = new JPanel(); // An empty panel for a placeholder tab
        addTabWithCustomMargin(tabbedPane, "", emptyPanel2, tabFont); // Creates a tab with empty content
        tabbedPane.setEnabledAt(11, false); // Disable the Course Menu tab
        
        // GROUP 3: Enrolled-related tabs
        addCenteredTab(tabbedPane, "ENROLLMENTS", new JPanel(), tabFont); // Centered, light gray
        tabbedPane.setEnabledAt(12, false); // Disable the Course Menu tab
        addTabWithCustomMargin(tabbedPane, "By Courses", new ViewCoursePanel(databaseService), tabFont);
        // Create and Disable Tab just to use as separator
        JPanel emptyPanel3 = new JPanel(); // An empty panel for a placeholder tab
        addTabWithCustomMargin(tabbedPane, "", emptyPanel3, tabFont); // Creates a tab with empty content
        tabbedPane.setEnabledAt(14, false); // Disable the Course Menu tab
        
        // GROUP 4: ACCOUNT-related tabs
        addCenteredTab(tabbedPane, "ACCOUNT", new JPanel(), tabFont); // Centered, light gray
        tabbedPane.setEnabledAt(15, false); // Disable the Course Menu tab
        
        // Tab for "Logout"
        JPanel logoutPanel = new JPanel(); // Empty panel for "Logout"
        addTabWithCustomMargin(tabbedPane, "Logout", logoutPanel, new Font("Arial", Font.BOLD, 14));
        // Create and Disable Tab just to use as separator
        JPanel emptyPanel4 = new JPanel(); // An empty panel for a placeholder tab
        addTabWithCustomMargin(tabbedPane, "", emptyPanel4, tabFont); // Creates a tab with empty content
        tabbedPane.setEnabledAt(17, false); // Disable the Course Menu tab
        
        // Set the default selected tab to "View Students"
        tabbedPane.setSelectedIndex(1); // Default index
        
        // Attach a change listener to detect when the "Logout" tab is selected
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == logoutPanel) {
                    // Ask for confirmation before logging out
                    int confirmation = JOptionPane.showConfirmDialog(
                        StudentManagementApp.this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Logic to handle logout
                        //dispose(); // Close the frame (ends the session)
                        new StudentManagementApp(); // Redirect to login page
                    } else {
                        // If user clicks "No", switch back to another tab
                        tabbedPane.setSelectedIndex(1); // Back to index "View Students"
                    }
                }
            }
        });

        setVisible(true); // Make the frame visible
    
        // Create a main panel to hold the tabbedPane
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder()); // Ensure no border
        mainPanel.add(tabbedPane, BorderLayout.CENTER); // Add tabbedPane to the center to take full space
         mainPanel.setBackground(new Color(222, 222, 222)); // Light gray
        return mainPanel;
        
        
    }

    // Method to add a tab with custom margin and left alignment
    private void addTabWithCustomMargin(JTabbedPane tabbedPane, String title, JPanel content, Font tabFont) {
        int tabWidth = 220; // Custom tab width
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Left-aligned
        tabPanel.setPreferredSize(new Dimension(tabWidth, 30)); 
        tabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px margin
        tabPanel.setBackground(new Color(255, 255, 255)); // Tab white background

        JLabel tabLabel = new JLabel(title); 
        tabLabel.setFont(tabFont); 
        tabLabel.setHorizontalAlignment(SwingConstants.LEFT); 
        
        tabPanel.add(tabLabel); 
        tabbedPane.addTab("", content); 
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabPanel); 
    }

    // Method to add a centered tab with light gray background
    private void addCenteredTab(JTabbedPane tabbedPane, String title, JPanel content, Font tabFont) {
        int tabWidth = 220; // Custom tab width
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Centered
        tabPanel.setPreferredSize(new Dimension(tabWidth, 30)); 
        tabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        tabPanel.setBackground(new Color(239, 239, 239)); // Light gray

        JLabel tabLabel = new JLabel(title); 
        tabLabel.setFont(tabFont); 
        tabLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        
        tabPanel.add(tabLabel); 
        tabbedPane.addTab("", content); 
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabPanel); 
    }

    public static void main(String[] args) {
        new StudentManagementApp(); // Launch the application
    }
}
