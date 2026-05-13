package com.ems;

import com.ems.db.DBConnection;
import com.ems.db.EmployeeDAO;
import com.ems.gui.LoginFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Using default look-and-feel.");
        }

        // Step 1: Initialize the database and create table
        System.out.println(" Starting Employee Management System...");
        DBConnection.getConnection();   // Connect to SQLite
        EmployeeDAO.createTable();      // Create table if it doesn't exist

        // Step 2: Launch the Login GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);

            // Ensure DB closes when app exits
            loginFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    DBConnection.closeConnection();
                    System.out.println("Database connection closed.");
                }
            });
        });
    }
}
