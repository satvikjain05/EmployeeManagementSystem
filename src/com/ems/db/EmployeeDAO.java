package com.ems.db;

import com.ems.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDAO.java (DAO = Data Access Object)
 * This class contains all database operations for Employee:
 *   - Create the table
 *   - Add, View, Search, Update, Delete employees
 * All SQL queries are written here using JDBC PreparedStatements (safe from SQL injection).
 */
public class EmployeeDAO {

    /**
     * Creates the Employee table if it doesn't already exist.
     * Called once when the app starts.
     */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                + "id INTEGER PRIMARY KEY, "
                + "name TEXT NOT NULL, "
                + "salary REAL NOT NULL, "
                + "department TEXT NOT NULL, "
                + "designation TEXT NOT NULL"
                + ");";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Employee table ready.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to create table.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new employee to the database.
     * @param emp The Employee object to insert
     * @return true if successful, false if ID already exists or error
     */
    public static boolean addEmployee(Employee emp) {
        // Check for duplicate ID first
        if (getEmployeeById(emp.getId()) != null) {
            return false; // ID already exists
        }
        String sql = "INSERT INTO employees (id, name, salary, department, designation) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getId());
            pstmt.setString(2, emp.getName());
            pstmt.setDouble(3, emp.getSalary());
            pstmt.setString(4, emp.getDepartment());
            pstmt.setString(5, emp.getDesignation());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all employees from the database.
     * @return List of Employee objects
     */
    public static List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("department"),
                        rs.getString("designation")
                );
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Finds a single employee by their ID.
     * @param id The employee ID to search
     * @return Employee object if found, null otherwise
     */
    public static Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("department"),
                        rs.getString("designation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found
    }

    /**
     * Updates an existing employee's details.
     * @param emp The Employee object with updated values
     * @return true if updated successfully
     */
    public static boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, salary=?, department=?, designation=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setDouble(2, emp.getSalary());
            pstmt.setString(3, emp.getDepartment());
            pstmt.setString(4, emp.getDesignation());
            pstmt.setInt(5, emp.getId());
            int rows = pstmt.executeUpdate();
            return rows > 0; // true if at least 1 row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an employee by ID.
     * @param id The employee ID to delete
     * @return true if deleted successfully
     */
    public static boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
