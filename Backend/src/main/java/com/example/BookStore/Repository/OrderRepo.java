package com.example.BookStore.Repository;

import com.example.BookStore.Models.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepo {
    private DBConnection connection;

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    public boolean confirm_order(int id) throws SQLException {
        String query = "UPDATE Orders SET confirmed = 'true' WHERE id = " + id;
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }
    public boolean deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE Id = " + id;
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean updateOrder(int quantity, int id) {
        String query = "UPDATE orders SET quantity = " + quantity + " WHERE Id = " + id;
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        //Id, Manage_Name, Quantity, TotalPrice, createdAt, updatedAt, Publisher, BookId, confirmed
        String query = "SELECT * FROM orders";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Order order = new Order();
                order.setBookId(resultSet.getString("Id"));
                order.setQuantity(String.valueOf(resultSet.getInt("Quantity")));
                order.setCreatedAt(resultSet.getDate("createdAt"));
                order.setUpdatedAt(resultSet.getDate("updatedAt"));
                order.setPublisher(resultSet.getString("Publisher"));
                order.setConfirmed(String.valueOf(resultSet.getBoolean("confirmed")));
                orders.add(order);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return orders;
    }
}
