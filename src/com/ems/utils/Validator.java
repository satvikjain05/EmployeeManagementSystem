package com.ems.utils;

/**
 * Validator.java
 * This utility class contains static methods to validate input fields.
 * Keeping validation logic here keeps the GUI code clean and organized.
 */
public class Validator {

    /**
     * Checks if a String is empty or null.
     * @param text The string to check
     * @return true if the string is null or blank
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Checks if a string is a valid positive integer.
     * Used to validate Employee ID.
     * @param text The string to check
     * @return true if valid integer > 0
     */
    public static boolean isValidId(String text) {
        try {
            int val = Integer.parseInt(text.trim());
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string is a valid positive salary (decimal allowed).
     * @param text The string to check
     * @return true if valid positive number
     */
    public static boolean isValidSalary(String text) {
        try {
            double val = Double.parseDouble(text.trim());
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a name contains only letters and spaces.
     * @param name The name to validate
     * @return true if valid name format
     */
    public static boolean isValidName(String name) {
        return name != null && name.trim().matches("[a-zA-Z ]+");
    }
}
