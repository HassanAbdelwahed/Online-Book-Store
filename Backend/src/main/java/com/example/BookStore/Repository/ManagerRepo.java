package com.example.BookStore.Repository;

import com.example.BookStore.Models.Book;
import com.example.BookStore.Models.Customer;
import com.example.BookStore.Models.Manager;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ManagerRepo {
    private DBConnection connection;

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    public Manager getManager(String username) {
        String query = "SELECT * FROM manager WHERE username = '" + username + "'";
        try {
            ResultSet resultSet = connection.getStatement().executeQuery(query);
            if (resultSet.next()) {
                Manager manager = new Manager();
                manager.setUserName(resultSet.getString("username"));
                manager.setFname(resultSet.getString("fname"));
                manager.setLname(resultSet.getString("lname"));
                manager.setEmail(resultSet.getString("email"));
                manager.setAddress(resultSet.getString("address"));
                manager.setPhone(resultSet.getString("phone"));
                manager.setPassword(resultSet.getString("password"));
                return manager;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

    //Promote registered customer to manager
    //customer > userName, Phone, FName, LName, email, Password, promoted, Address, PromoteMN
    //manager >userName, Address, FName, LName, Phone, email, Password
    public boolean promoteCustomer(String username, String managerName) {
        String query = "UPDATE customer SET promoted = 1 , PromoteMN = '" + managerName + "' WHERE username = '" + username + "'";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    // get all customers
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setUserName(resultSet.getString(1));
                customer.setFname(resultSet.getString(2));
                customer.setEmail(resultSet.getString(3));
                customer.setAddress(resultSet.getString(4));
                customer.setPhone(resultSet.getString(5));
                customer.setPassword(resultSet.getString(6));
                customer.setPromoted(resultSet.getInt(7));
                customer.setPromoteMN(resultSet.getString(8));
                customers.add(customer);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return customers;
    }


    //* c. The top 10 selling books for the last three month
    public ArrayList<Book> get_top_10_books() {
        ArrayList<Book> books = new ArrayList<>();
        //book > Book_ISBN, title, Publication_Year, Category, price, amount, Publisher
        // shopping_cart> id, userName, amountRequired, total_price, state, Date
        //items > BookId, cart_id, amountRequired, Price
        String query = "SELECT b.Book_ISBN, b.Title, b.Publisher, SUM(t.amountRequired) AS TotalQuantity FROM shopping_cart s," + " book b,items t WHERE s.Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 3 MONTH) " + "AND s.id = t.cart_id  GROUP BY t.BookId ORDER BY SUM(t.amountRequired) DESC LIMIT 10";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book();
                book.setBook_ISBN(resultSet.getString("Book_ISBN"));
                book.setTitle(resultSet.getString("Title"));
                book.setPublisher(resultSet.getString("Publisher"));
                book.setAmount(Integer.parseInt(resultSet.getString("TotalQuantity")));//amount is used to store the total quantity of the book
                books.add(book);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return books;
    }

    //* d. The top 5 customers who spent the most money for the last three month
    public ArrayList<Customer> get_top_5_customers() {
        ArrayList<Customer> customers = new ArrayList<>();
        //customer > userName, Phone, FName, LName, email, Password, promoted, Address, PromoteMN
        // shopping_cart> id, userName, amountRequired, total_price, state, Date
        String query = "SELECT c.username, c.email, c.phone, c.address, SUM(s.total_price) AS TotalPrice FROM customer c" + ", shopping_cart s " + "WHERE s.Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 3 MONTH) AND s.userName = c.username " + "GROUP BY s.userName ORDER BY SUM(s.total_price) DESC LIMIT 5";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setUserName(resultSet.getString("username"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setPromoted(resultSet.getInt("promoted"));//promoted is used to store the total price
                customers.add(customer);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return customers;
    }

    // The total sales for books in the previous month
    public ArrayList<Book> get_total_sales() {
        //shopping_cart> id, userName, amountRequired, total_price, state, Date
        //items > BookId, cart_id, amountRequired, Price
        //book > Book_ISBN, title, Publication_Year, Category, price, amount, Publisher
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT b.Book_ISBN, b.Title, b.Publisher, SUM(t.amountRequired) AS TotalQuantity, " + "SUM(t.Price) AS TotalPrice FROM shopping_cart s," + " book b,items t WHERE s.Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 1 MONTH) " + "AND s.id = t.cart_id AND t.BookId = b.Book_ISBN GROUP BY t.BookId ORDER BY SUM(t.amountRequired) DESC";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book();
                book.setBook_ISBN(resultSet.getString("Book_ISBN"));
                book.setTitle(resultSet.getString("Title"));
                book.setPublisher(resultSet.getString("Publisher"));
                book.setAmount(Integer.parseInt(resultSet.getString("TotalQuantity")));//amount is used to store the total quantity of the book
                book.setPrice(Integer.parseInt(resultSet.getString("TotalPrice")));//price is used to store the total price of the book
                books.add(book);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return books;
    }
}