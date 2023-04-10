package com.example.BookStore.Models;

public class Book {
    private String book_ISBN;
    private String title;
    private String publication_Year;
    private String category;
    private int price;
    private int amount;
    private String publisher;

    public Book(){}
    public Book(String book_ISBN, String title, String publication_Year, String category, int price, int amount, String publisher) {
        this.book_ISBN = book_ISBN;
        this.title = title;
        this.publication_Year = publication_Year;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.publisher = publisher;
    }

    public String getBook_ISBN() {
        return book_ISBN;
    }

    public void setBook_ISBN(String book_ISBN) {
        this.book_ISBN = book_ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublication_Year() {
        return publication_Year;
    }

    public void setPublication_Year(String publication_Year) {
        this.publication_Year = publication_Year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
