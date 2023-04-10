package com.example.BookStore.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    private Connection connection;
    private Statement statement;

    public DBConnection() {
    }

    public boolean connect() {
        String url = "jdbc:mysql://localhost:3306/online_book_store";
        String userName = "root";
        String password = "abdu";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            statement = connection.createStatement();
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
