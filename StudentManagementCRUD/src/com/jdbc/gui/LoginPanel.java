package com.jdbc.gui;

import javax.swing.*;
import java.awt.*;
import com.jdbc.service.DatabaseService;

// Create an interface for login listener
interface LoginListener {
    void onLogin(boolean success);
}

public class LoginPanel extends JPanel {

    private JTextField emailField;
    private JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton cancelButton;
    private LoginListener loginListener;

    public LoginPanel(DatabaseService databaseService) {
        setLayout(new BorderLayout()); // Use BorderLayout to accommodate the logo at the top

        // Create a panel for the logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); // Padding for the logo panel

        // Load the logo image
        ImageIcon originalIcon = new ImageIcon("images//SETU-Logo.png"); // Your logo image path
        Image originalImage = originalIcon.getImage(); // Get the original image

        // Resize the logo to a suitable dimension
        int logoWidth = 250; // Adjust the desired width
        int logoHeight = 150; // Adjust the desired height
        Image resizedImage = originalImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH); // Smooth resizing
        ImageIcon resizedIcon = new ImageIcon(resizedImage); // Create the resized icon

        JLabel logoLabel = new JLabel(resizedIcon, SwingConstants.CENTER); // Center the logo
        logoPanel.add(logoLabel, BorderLayout.CENTER); // Add the logo to the panel

        add(logoPanel, BorderLayout.NORTH); // Place the logo panel at the top
        
        // Panel for the login form
        JPanel formPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for the form
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Reset anchor to west
        gbc.anchor = GridBagConstraints.WEST;
        
        // Email label and text field
        gbc.gridx = 0; 
        gbc.gridy = 1; 
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; 
        emailField = new JTextField(20); 
        formPanel.add(emailField, gbc);

        // Password label and password field
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; 
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // Adding extra vertical space (20 pixels) above the buttons
        gbc.gridy = 3; // New row for spacing
        gbc.gridx = 0; // Reset to the start
        gbc.gridwidth = 2; // Span across both columns
        formPanel.add(new JLabel(" "), gbc); // Adding a blank label as a spacer

        // Login and cancel buttons with added space
        gbc.gridy = 4; // Adjusted row index for the buttons
        gbc.gridx = 0; 
        gbc.gridwidth = 2; // Span across two columns to align to the right

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Aligns components to the right
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(cancelButton); // Add the cancel button
        buttonPanel.add(loginButton); // Add the login button

        formPanel.add(buttonPanel, gbc); // Add the button panel to the form panel

        // Existing action listeners for login and cancel buttons
        loginButton.addActionListener(e -> {
            if (loginListener != null) {
                loginListener.onLogin(true);
            }
        });

        cancelButton.addActionListener(e -> {
            emailField.setText(""); // Clear fields on cancel
            passwordField.setText(""); // Clear fields on cancel
        });

        // Add form panel to the BorderLayout center
        add(formPanel, BorderLayout.CENTER);

    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }
}
