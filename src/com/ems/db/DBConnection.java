package com.ems.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * DBConnection.java
 * This helper initializes the employee storage file.
 * The app stores employees in a local CSV-style file instead of a database.
 */
public class DBConnection {

    private static final String EMPLOYEE_FILE = "employees.csv";
    private static final String HEADER = "id|name|salary|department|designation";

    public static void initializeStorage() {
        try {
            Path path = Paths.get(EMPLOYEE_FILE);
            if (Files.notExists(path)) {
                Files.write(path, (HEADER + System.lineSeparator()).getBytes());
                System.out.println("✅ Employee storage file created: " + EMPLOYEE_FILE);
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to create employee storage file.");
            e.printStackTrace();
        }
    }
}
