package com.ems.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * This class handles the database connection using JDBC.
 * It uses SQLite so no separate server setup is needed — great for beginners!
 * The database file (ems.db) is automatically created in the project folder.
 */
public class DBConnection {

    // SQLite database file path — saved inside the project folder
    private static final String DB_URL = "jdbc:sqlite:ems.db";

    // Single shared connection (singleton pattern)
    private static Connection connection = null;

    /**
     * Returns the database connection.
     * Creates a new connection if one doesn't exist yet.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                // Connect to the SQLite database file
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found! Make sure sqlite-jdbc.jar is in /lib folder.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes the database connection when the app exits.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
