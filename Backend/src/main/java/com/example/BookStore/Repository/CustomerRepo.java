package com.example.BookStore.Repository;

import com.example.BookStore.Models.Book;
import com.example.BookStore.Models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepo {
    DBConnection connection;

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    public Customer getCustomer(String username) {
        String query = "SELECT * FROM customer WHERE username = '" + username + "'";
        try {
            var result = connection.getStatement().executeQuery(query);
            if (result.next()) {
                Customer customer = new Customer();
                customer.setUserName(result.getString("username"));
                customer.setFname(result.getString("fname"));
                customer.setLname(result.getString("lname"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setPassword(result.getString("password"));
                customer.setAddress(result.getString("address"));
                return customer;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

    public boolean editCustomer(Customer customer) {
        String query = "UPDATE customer SET fname = '" + customer.getFname()
                + "', lname = '" + customer.getLname() + "', phone = '" + customer.getPhone()
                + "', email = '" + customer.getEmail() + "', password = '" + customer.getPassword()
                + "', address = '" + customer.getAddress() + "' WHERE username = '" +
                customer.getUserName() + "'";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean customer_log_out(String username) throws SQLException {
        int count = 0;
        int cart_id;
        try {
            String query = "SELECT * FROM shopping_cart WHERE username = '" + username + "' AND state = false";
            ResultSet resultSet = connection.getStatement().executeQuery(query);
            if (resultSet.next()) {
                cart_id = resultSet.getInt("id");
                System.out.println(cart_id);
            } else {
                return false;
            }
            String query1 = "SELECT * FROM items WHERE cart_id = " + cart_id;
            ResultSet resultSet2 = connection.getStatement().executeQuery(query);
            //loop on items
            while (resultSet2.next()) {
                count++;
                String book_id = resultSet2.getString(1);
                int amount = resultSet2.getInt(3);
                System.out.println(amount);
                query = "UPDATE book SET amount = amount + " + amount + " WHERE Book_ISBN = '" + book_id + "'";
                connection.getStatement().executeUpdate(query);
                System.out.println("ok");
                resultSet2 = connection.getStatement().executeQuery(query1);
                for (int i = 0; i < count; i++) {
                    resultSet2.next();
                }
            }
            query = "DELETE FROM Items WHERE cart_id = " + cart_id;
            connection.getStatement().executeUpdate(query);
            query = "DELETE FROM shopping_cart WHERE id = " + cart_id;
            connection.getStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Book> viewCart(int cart_id) throws SQLException {
        int count = 0;
        ArrayList<Book> books = new ArrayList<>();
        try {
            //get items in cart
            String query1 = "SELECT * FROM Items WHERE cart_id = " + cart_id;
            ResultSet resultSet = connection.getStatement().executeQuery(query1);
            //loop on items
            while (resultSet.next()) {
                count++;
                String book_id = resultSet.getString(1);
                int amount = resultSet.getInt(3);
                //get book info

                String query = "SELECT * FROM book WHERE book_ISBN = '" + book_id + "'";
                resultSet = connection.getStatement().executeQuery(query);

                if (resultSet.next()) {
                    Book book = new Book();
                    book.setBook_ISBN(resultSet.getString(1));
                    book.setTitle(resultSet.getString(2));
                    book.setPublication_Year(resultSet.getString(3));
                    book.setCategory(resultSet.getString(4));
                    book.setPrice(resultSet.getInt(5));
                    book.setAmount(amount);
                    book.setPublisher(resultSet.getString(7));
                    books.add(book);
                }
                resultSet = connection.getStatement().executeQuery(query1);
                for (int i = 0; i < count; i++) {
                    resultSet.next();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return books;
    }
}