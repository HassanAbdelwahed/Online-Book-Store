package com.example.BookStore.Repository;

import com.example.BookStore.Models.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepo {
    private DBConnection connection;

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    //Book_ISBN, title, Publication_Year, Category, price, amount, Publisher
    public boolean add_new_book(Book book) throws SQLException {
        String query = "SELECT * FROM Book WHERE Book_ISBN = '" + book.getBook_ISBN() + "'";
        if (connection.getStatement().executeQuery(query).next()) return false;
        query = "INSERT INTO Book VALUES ('" + book.getBook_ISBN() + "', '" + book.getTitle() + "', '" + book.getPublication_Year() + "', '" + book.getCategory() + "', " + book.getPrice() + ", " + book.getAmount() + ", '" + book.getPublisher() + "')";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean update_book_price(String ISBN, int value) throws SQLException {
        String query = "UPDATE Book SET price = " + value + "WHERE Book_ISBN = '" + ISBN + "'";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean delete_book(String ISBN) throws SQLException {
        String query = "DELETE FROM Book WHERE Book_ISBN = '" + ISBN + "'";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    public boolean update_book(Book book) throws SQLException {
        String query = "UPDATE Book SET title = '" + book.getTitle() +
                "', Publication_Year = '" + book.getPublication_Year() +
                "', Category = '" + book.getCategory() + "', price = " + book.getPrice() +
                ", amount = " + book.getAmount() + ", Publisher = '" + book.getPublisher() +
                "' WHERE Book_ISBN = '" + book.getBook_ISBN() + "'";
        try {
            connection.getStatement().executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return false;
    }

    //Book_ISBN, title, Publication_Year, Category, price, amount, Publisher
    public ArrayList<Book> search(String field, String value) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE " + field + " = '" + value + "'";
        var resultSet = connection.getStatement().executeQuery(query);
        while (resultSet.next()) {
            Book book = new Book();
            book.setBook_ISBN(resultSet.getString(1));
            book.setTitle(resultSet.getString(2));
            book.setPublication_Year(resultSet.getString(3));
            book.setCategory(resultSet.getString(4));
            book.setPrice(resultSet.getInt(5));
            book.setAmount(resultSet.getInt(6));
            book.setPublisher(resultSet.getString(7));
            books.add(book);
        }
        return books;
    }

    public ArrayList<Book> searchByAuthor(String author) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE Book_ISBN IN" +
                " (SELECT Book_ISBN FROM Author WHERE Author_Name = '"
                + author + "')";
        var resultSet = connection.getStatement().executeQuery(query);
        while (resultSet.next()) {
            Book book = new Book();
            book.setBook_ISBN(resultSet.getString(1));
            book.setTitle(resultSet.getString(2));
            book.setPublication_Year(resultSet.getString(3));
            book.setCategory(resultSet.getString(4));
            book.setPrice(resultSet.getInt(5));
            book.setAmount(resultSet.getInt(6));
            book.setPublisher(resultSet.getString(7));
            books.add(book);
        }
        return books;
    }

    // get all books
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        //book > Book_ISBN, title, Publication_Year, Category, price, amount, Publisher
        String query = "SELECT * FROM book WHERE amount > 0 LIMIT 20 ";

        //String query = "SELECT * FROM book";
        try {
            var resultSet = connection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book();
                book.setBook_ISBN(resultSet.getString(1));
                book.setTitle(resultSet.getString(2));
                book.setPublication_Year(resultSet.getString(3));
                book.setCategory(resultSet.getString(4));
                book.setPrice(resultSet.getInt(5));
                book.setAmount(resultSet.getInt(6));
                book.setPublisher(resultSet.getString(7));
                books.add(book);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return books;
    }
}
