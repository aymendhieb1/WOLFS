package com.wolfs.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static DataSource instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/wolfs";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private DataSource() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to DATABASE");
        } catch (SQLException var2) {
            SQLException e = var2;
            System.out.println(e.getMessage());
        }

    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
