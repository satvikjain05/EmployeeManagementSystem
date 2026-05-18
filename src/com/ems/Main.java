package com.ems;

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

        // Step 1: Initialize the local employee storage and create file
        System.out.println(" Starting Employee Management System...");
        EmployeeDAO.createTable();      // Create storage file if it doesn't exist

        // Step 2: Launch the Login GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
