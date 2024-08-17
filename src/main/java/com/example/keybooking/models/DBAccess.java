package com.example.keybooking.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAccess {
    public final String USERNAME = "root";
    public final String PASSWORD = "@DLMiTech1248";
    public final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public final String DATABASE = "ktu_key_db";
    public final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;

    private Connection connection;
    public static DBAccess dbAccess;

    public DBAccess() {
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBAccess getInstance() {
        if(dbAccess == null) {
            dbAccess = new DBAccess();
        }
        return dbAccess;
    }

    public Connection getConnection() {
        return connection;
    }
}
