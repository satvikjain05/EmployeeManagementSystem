package com.ems.db;

import com.ems.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeDAO.java (DAO = Data Access Object)
 * This class contains all file-based operations for Employee:
 *   - Create storage file
 *   - Add, View, Search, Update, Delete employees
 */
public class EmployeeDAO {

    private static final String EMPLOYEE_FILE = "employees.csv";
    private static final String HEADER = "id|name|salary|department|designation";

    private static Path getFilePath() {
        return Paths.get(EMPLOYEE_FILE);
    }

    public static void createTable() {
        DBConnection.initializeStorage();
    }

    public static boolean addEmployee(Employee emp) {
        if (getEmployeeById(emp.getId()) != null) {
            return false;
        }
        try {
            String record = formatEmployee(emp);
            Files.write(getFilePath(), (record + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Employee> getAllEmployees() {
        try {
            return Files.lines(getFilePath())
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(EmployeeDAO::parseEmployee)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Employee getEmployeeById(int id) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static boolean updateEmployee(Employee emp) {
        List<Employee> employees = getAllEmployees();
        boolean found = false;

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == emp.getId()) {
                employees.set(i, emp);
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        return writeAllEmployees(employees);
    }

    public static boolean deleteEmployee(int id) {
        List<Employee> employees = getAllEmployees();
        List<Employee> filtered = employees.stream()
                .filter(emp -> emp.getId() != id)
                .collect(Collectors.toList());

        if (filtered.size() == employees.size()) {
            return false;
        }

        return writeAllEmployees(filtered);
    }

    private static boolean writeAllEmployees(List<Employee> employees) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Employee emp : employees) {
            lines.add(formatEmployee(emp));
        }
        try {
            Files.write(getFilePath(), lines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String formatEmployee(Employee emp) {
        return emp.getId() + "|" + emp.getName() + "|" + emp.getSalary() + "|"
                + emp.getDepartment() + "|" + emp.getDesignation();
    }

    private static Employee parseEmployee(String line) {
        String[] parts = line.split("\\|", -1);
        int id = Integer.parseInt(parts[0]);
        double salary = Double.parseDouble(parts[2]);
        return new Employee(id, parts[1], salary, parts[3], parts[4]);
    }
}
