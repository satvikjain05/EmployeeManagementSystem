package com.ems.gui;

import com.ems.db.EmployeeDAO;
import com.ems.model.Employee;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;

    public MainFrame() {

        // Fix faded text issue
        UIManager.put("Button.disabledText", Color.BLACK);

        setTitle("Employee Management System - Dashboard");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadEmployeeData();
    }

    private void initComponents() {

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 60, 114));

        JLabel title = new JLabel("Employee Management Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(title, BorderLayout.WEST);

        // ===== SEARCH =====
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30, 60, 114));

        txtSearch = new JTextField(10);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> searchEmployee());

        JButton btnReset = new JButton("Show All");
        btnReset.addActionListener(e -> loadEmployeeData());

        JLabel lbl = new JLabel("ID:");
        lbl.setForeground(Color.WHITE);

        searchPanel.add(lbl);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        // ===== TABLE =====
        String[] cols = {"ID", "Name", "Salary", "Department", "Designation"};

        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);

        //  Table styling
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 14));
        employeeTable.setForeground(Color.BLACK);
        employeeTable.setBackground(Color.WHITE);
        employeeTable.setRowHeight(25);

        //  HEADER FIX (IMPORTANT)
        JTableHeader header = employeeTable.getTableHeader();

        header.setOpaque(true);
        header.setBackground(new Color(30, 60, 114));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(JLabel.CENTER);
                setBackground(new Color(30, 60, 114));
                setForeground(Color.WHITE);
                setFont(new Font("Arial", Font.BOLD, 15));
            }
        });

        JScrollPane scroll = new JScrollPane(employeeTable);

        // ===== BUTTONS =====
        JPanel bottom = new JPanel();

        JButton add = createButton("Add", new Color(40, 167, 69));
        JButton update = createButton("Update", new Color(0, 123, 255));
        JButton delete = createButton("Delete", new Color(220, 53, 69));
        JButton refresh = createButton("Refresh", new Color(108, 117, 125));

        add.addActionListener(e -> openAddDialog());
        update.addActionListener(e -> openUpdateDialog());
        delete.addActionListener(e -> deleteSelectedEmployee());
        refresh.addActionListener(e -> loadEmployeeData());

        bottom.add(add);
        bottom.add(update);
        bottom.add(delete);
        bottom.add(refresh);

        // ===== ADD TO FRAME =====
        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // BUTTON FIX
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));

        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        return btn;
    }

    // ===== LOAD DATA =====
    public void loadEmployeeData() {
        tableModel.setRowCount(0);

        List<Employee> list = EmployeeDAO.getAllEmployees();

        for (Employee e : list) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getName(),
                    e.getSalary(),
                    e.getDepartment(),
                    e.getDesignation()
            });
        }
    }

    // ===== ADD =====
    private void openAddDialog() {
        new EmployeeFormDialog(this, "Add Employee", null).setVisible(true);
        loadEmployeeData();
    }

    // ===== UPDATE =====
    private void openUpdateDialog() {
        int row = employeeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select employee first");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Employee emp = EmployeeDAO.getEmployeeById(id);

        new EmployeeFormDialog(this, "Update Employee", emp).setVisible(true);
        loadEmployeeData();
    }

    // ===== DELETE =====
    private void deleteSelectedEmployee() {
        int row = employeeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select employee first");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (EmployeeDAO.deleteEmployee(id)) {
                JOptionPane.showMessageDialog(this, "Deleted successfully");
                loadEmployeeData();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed");
            }
        }
    }

    // ===== SEARCH =====
    private void searchEmployee() {
        try {
            int id = Integer.parseInt(txtSearch.getText());

            Employee emp = EmployeeDAO.getEmployeeById(id);

            tableModel.setRowCount(0);

            if (emp != null) {
                tableModel.addRow(new Object[]{
                        emp.getId(),
                        emp.getName(),
                        emp.getSalary(),
                        emp.getDepartment(),
                        emp.getDesignation()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Not found");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }
}