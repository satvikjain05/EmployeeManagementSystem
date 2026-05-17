package com.ems.model;

/**
 * Employee.java
 * This class represents an Employee object with all necessary fields.
 * It uses OOP concepts: encapsulation with private fields and public getters/setters.
 */
public class Employee {

    // Private fields (encapsulation)
    private int id;
    private String name;
    private double salary;
    private String department;
    private String designation;

    // Constructor: used to create a new Employee object
    public Employee(int id, String name, double salary, String department, String designation) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.designation = designation;
    }

    // Default constructor (needed in some cases)
    public Employee() {}

    // Getters and Setters for each field

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    // toString: useful for debugging
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary
                + ", department='" + department + "', designation='" + designation + "'}";
    }
}
