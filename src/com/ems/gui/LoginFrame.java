package com.ems.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnClear;

    public LoginFrame() {
        setTitle("Employee Management System - Login");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 60, 114));

        // --- Title ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 60, 114));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // --- Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtUsername = new JTextField(15);
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        formPanel.add(txtPassword, gbc);

        // Hint
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel hintLabel = new JLabel("Hint: admin / admin123");
        hintLabel.setForeground(Color.BLACK); // darker for visibility
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        formPanel.add(hintLabel, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);

        // Login button (outlined style)
        btnLogin = new JButton("Login");
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(true);
        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        btnLogin.setForeground(new Color(0, 123, 255)); // blue text
        btnLogin.setBackground(Color.WHITE);           // white background
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setPreferredSize(new Dimension(100, 35));

        // Clear button (outlined style)
        btnClear = new JButton("Clear");
        btnClear.setContentAreaFilled(false);
        btnClear.setOpaque(true);
        btnClear.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 2));
        btnClear.setForeground(new Color(220, 53, 69)); // red text
        btnClear.setBackground(Color.WHITE);            // white background
        btnClear.setFont(new Font("Arial", Font.BOLD, 13));
        btnClear.setPreferredSize(new Dimension(100, 35));

        btnPanel.add(btnLogin);
        btnPanel.add(btnClear);
        formPanel.add(btnPanel, gbc);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Actions
        btnLogin.addActionListener(e -> handleLogin());
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        });
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "✅ Login Successful! Welcome, " + username + "!",
                    "Login", JOptionPane.INFORMATION_MESSAGE);
            new MainFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid username or password!\nTry: admin / admin123",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}
