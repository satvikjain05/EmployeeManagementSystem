-- ============================================================
-- Employee Management System - Database Schema
-- File: sql/schema.sql
-- Database: SQLite (file: ems.db)
-- ============================================================

-- Drop the table if it already exists (useful for fresh start)
DROP TABLE IF EXISTS employees;

-- Create the employees table
CREATE TABLE employees (
    id          INTEGER PRIMARY KEY,       -- Unique Employee ID (entered by user)
    name        TEXT    NOT NULL,          -- Full name of the employee
    salary      REAL    NOT NULL,          -- Salary (decimal allowed, e.g. 45000.50)
    department  TEXT    NOT NULL,          -- Department name (e.g. IT, HR, Finance)
    designation TEXT    NOT NULL           -- Job title (e.g. Manager, Developer)
);

-- ============================================================
-- Sample Data (optional — for testing)
-- ============================================================
INSERT INTO employees (id, name, salary, department, designation) VALUES
(101, 'Rahul Sharma',   55000.00, 'Information Technology', 'Software Developer'),
(102, 'Priya Verma',    62000.00, 'Human Resources',        'HR Manager'),
(103, 'Amit Singh',     48000.00, 'Finance',                'Accountant'),
(104, 'Sneha Patel',    71000.00, 'Information Technology', 'Team Lead'),
(105, 'Vikram Joshi',   39000.50, 'Marketing',              'Marketing Executive');

-- ============================================================
-- Useful SELECT queries (for reference / testing)
-- ============================================================

-- View all employees
SELECT * FROM employees;

-- Search by ID
SELECT * FROM employees WHERE id = 101;

-- Update salary
UPDATE employees SET salary = 60000 WHERE id = 103;

-- Delete an employee
DELETE FROM employees WHERE id = 105;
