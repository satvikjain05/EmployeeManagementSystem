package com.ems.gui;

import com.ems.db.EmployeeDAO;
import com.ems.model.Employee;
import com.ems.utils.Validator;

import javax.swing.*;
import java.awt.*;

/**
 * EmployeeFormDialog.java
 * A reusable dialog box used for both Adding and Updating an employee.
 * If an existing Employee object is passed in, fields are pre-filled (Update mode).
 * If null is passed, fields are empty (Add mode).
 */
public class EmployeeFormDialog extends JDialog {

    // Form fields
    private JTextField txtId, txtName, txtSalary, txtDepartment, txtDesignation;
    private JButton btnSave, btnCancel;

    private Employee existingEmployee; // null = Add mode, non-null = Update mode
    private String mode;               // "Add Employee" or "Update Employee"

    public EmployeeFormDialog(JFrame parent, String title, Employee emp) {
        super(parent, title, true); // Modal dialog (blocks parent)
        this.existingEmployee = emp;
        this.mode = title;

        setSize(420, 370);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();

        // If updating, pre-fill the form with existing data
        if (emp != null) {
            txtId.setText(String.valueOf(emp.getId()));
            txtId.setEditable(false); // ID should not be changed on update
            txtId.setBackground(new Color(220, 220, 220));
            txtName.setText(emp.getName());
            txtSalary.setText(String.valueOf(emp.getSalary()));
            txtDepartment.setText(emp.getDepartment());
            txtDesignation.setText(emp.getDesignation());
        }
    }

    /**
     * Builds the form layout with labels and input fields.
     */
    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Title
        JLabel titleLabel = new JLabel(mode, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(30, 60, 114));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form fields using GridBagLayout for alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Employee ID
        addFormRow(formPanel, gbc, 0, "Employee ID *:", txtId = new JTextField(20));
        // Name
        addFormRow(formPanel, gbc, 1, "Full Name *:", txtName = new JTextField(20));
        // Salary
        addFormRow(formPanel, gbc, 2, "Salary (₹) *:", txtSalary = new JTextField(20));
        // Department
        addFormRow(formPanel, gbc, 3, "Department *:", txtDepartment = new JTextField(20));
        // Designation
        addFormRow(formPanel, gbc, 4, "Designation *:", txtDesignation = new JTextField(20));

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        btnSave = new JButton(mode.startsWith("Add") ? "💾 Save" : "✏ Update");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 13));
        btnSave.setPreferredSize(new Dimension(120, 35));

        btnCancel = new JButton("✖ Cancel");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancel.setPreferredSize(new Dimension(120, 35));

        btnSave.addActionListener(e -> saveEmployee());
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Helper to add a label + field row to the form.
     */
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    /**
     * Validates inputs and saves (Add or Update) the employee.
     */
    private void saveEmployee() {
        // Get values from fields
        String idText = txtId.getText().trim();
        String name = txtName.getText().trim();
        String salaryText = txtSalary.getText().trim();
        String department = txtDepartment.getText().trim();
        String designation = txtDesignation.getText().trim();

        // --- Validation ---
        if (Validator.isEmpty(idText) || Validator.isEmpty(name) ||
                Validator.isEmpty(salaryText) || Validator.isEmpty(department) ||
                Validator.isEmpty(designation)) {
            JOptionPane.showMessageDialog(this, "⚠ All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validator.isValidId(idText)) {
            JOptionPane.showMessageDialog(this, "⚠ Employee ID must be a positive number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validator.isValidName(name)) {
            JOptionPane.showMessageDialog(this, "⚠ Name should contain only letters and spaces!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validator.isValidSalary(salaryText)) {
            JOptionPane.showMessageDialog(this, "⚠ Salary must be a valid positive number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Build Employee object from input
        int id = Integer.parseInt(idText);
        double salary = Double.parseDouble(salaryText);
        Employee emp = new Employee(id, name, salary, department, designation);

        // --- Add or Update ---
        if (existingEmployee == null) {
            // ADD mode
            if (EmployeeDAO.addEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "✅ Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Employee ID " + id + " already exists! Use a unique ID.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // UPDATE mode
            if (EmployeeDAO.updateEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "✅ Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
