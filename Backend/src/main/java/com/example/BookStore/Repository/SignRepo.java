package com.example.BookStore.Repository;

import com.example.BookStore.Models.Customer;
import com.example.BookStore.Models.Manager;

import java.sql.SQLException;

public class SignRepo {
    private DBConnection connection;

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    public boolean addManager(Manager manager) throws SQLException {
        String query = "SELECT * FROM customer WHERE username = '" + manager.getUserName() +
                "' OR email = '" + manager.getEmail() + "'";
        if (connection.getStatement().executeQuery(query).next())
            return false;
        query = "INSERT INTO customer VALUES ('" + manager.getUserName() + "', '" + manager.getPhone() +
                "', '" + manager.getFname() + "', '" + manager.getLname() + "', '" + manager.getEmail() + "', '" +
                manager.getPassword() + "', 0, '" + manager.getAddress() + "', null)";
        System.out.println(query);
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;

    }

    public boolean addCustomer(Customer customer) throws SQLException {
        String query = "SELECT * FROM customer WHERE username = '" + customer.getUserName() +
                "' OR email = '" + customer.getEmail() + "'";
        if (connection.getStatement().executeQuery(query).next())
            return false;
        query = "INSERT INTO customer VALUES ('" + customer.getUserName() + "', '" + customer.getPhone() +
                "', '" + customer.getFname() + "', '" + customer.getLname() + "', '" + customer.getEmail() + "', '" +
                customer.getPassword() + "', 0, '" + customer.getAddress() + "', null)";
        System.out.println(query);
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean checkManager(String username, String password) {
        String query = "SELECT * FROM manager WHERE (username = '" + username + "' OR email = '" + username + "') AND password = '" + password + "'";
        try {
            if (connection.getStatement().executeQuery(query).next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean checkCustomer(String username, String password) {
        String query = "SELECT * FROM customer WHERE (username = '" + username + "' OR email = '" + username + "') AND password = '" + password + "'";
        try {
            if (connection.getStatement().executeQuery(query).next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

}