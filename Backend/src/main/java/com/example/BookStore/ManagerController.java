package com.example.BookStore;

import com.example.BookStore.Models.*;
import com.example.BookStore.Repository.BookRepo;
import com.example.BookStore.Repository.ManagerRepo;
import com.example.BookStore.Repository.OrderRepo;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/manager")
public class ManagerController {
// get manager by id
    ManagerRepo managerRepo = new ManagerRepo();
    @GetMapping("/get/{username}")
    public Manager getManager(@PathVariable String username) {
         managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.getManager(username);
    }

   BookRepo bookRepo = new BookRepo();
    //* 1. Add new books
    @PostMapping("/addBook")
    public boolean addBook(@RequestBody Book book) throws SQLException {
        bookRepo.setConnection(BookStoreApplication.dbConnection);
        return bookRepo.add_new_book(book);
    }
    //* 2. Modify existing books
    @PutMapping("/modifyBook")
    public boolean modifyBook(@RequestBody Book book) throws SQLException {
        bookRepo.setConnection(BookStoreApplication.dbConnection);
        return bookRepo.update_book(book);
    }
    //* 7. View all books
    @GetMapping("/viewBooks")
    public ArrayList<Book> getBooks() {
        bookRepo.setConnection(BookStoreApplication.dbConnection);
        return bookRepo.getAllBooks();

    }
    OrderRepo orderRepo = new OrderRepo();
    //* 3. Place orders for books
    @PostMapping("/placeOrder")
    public boolean placeOrder(@RequestBody Order order) {
        return true;
    }
    //* 4. Confirm orders
    @PutMapping("/confirmOrder")
    public boolean confirmOrder(@PathVariable int orderID) throws SQLException {
        orderRepo.setConnection(BookStoreApplication.dbConnection);
         return orderRepo.confirm_order(orderID);
    }
    //* 5. remove  orders
    @PutMapping("/removeOrder")
        public boolean removeOrder(@PathVariable int orderID) throws SQLException {
        orderRepo.setConnection(BookStoreApplication.dbConnection);
        return orderRepo.deleteOrder(orderID);
    }
    //* 6. View all orders
    @GetMapping("/viewOrders")
    public ArrayList<Order> getOrders() {
        orderRepo.setConnection(BookStoreApplication.dbConnection);
        return orderRepo.getOrders();
    }

    //* 8. View all customers
    @GetMapping("/viewCustomers")
    public ArrayList<Customer> getCustomers() {
        managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.getAllCustomers();
    }

    //* 5. Promote registered customers to have managers credentials
    @PutMapping("/promoteCustomer")
    public boolean promoteCustomer(@RequestBody Customer customer) {
        managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.promoteCustomer(customer.getUserName(), customer.getPromoteMN());
    }
    //* 6. View the following reports on sales
    //*  The total sales for books in the previous month
    @GetMapping("/totalSales")
    public ArrayList<Book> totalSales() {
        managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.get_total_sales();
    }
    //* b. The top 5 customers who purchase the most purchase amount in descending order for the last three months
    @GetMapping("/top5Customers")
    public ArrayList<Customer> top5Customers() {
        managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.get_top_5_customers();
    }
    //* c. The top 10 selling books for the last three month
    @GetMapping("/top10Books")
    public ArrayList<Book> top10Books() {
        managerRepo.setConnection(BookStoreApplication.dbConnection);
        return managerRepo.get_top_10_books();
    }
}
