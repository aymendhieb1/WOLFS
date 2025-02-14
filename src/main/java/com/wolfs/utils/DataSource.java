package com.wolfs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static DataSource instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/wolfs";  // Make sure 'wolfs' is your database name
    private final String USERNAME = "root";  // Correct username
    private final String PASSWORD = "";  // Correct password

    private DataSource() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to DATABASE");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;  // This will return null if the connection wasn't initialized
    }
}
