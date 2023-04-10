package com.example.BookStore.Models;

public class Items {
    private String bookId;
    private int cart_id;
    private int amountRequired;
    private int price;

    public Items(String bookId, int cart_id, int amountRequired, int price) {
        this.bookId = bookId;
        this.cart_id = cart_id;
        this.amountRequired = amountRequired;
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public void setAmountRequired(int amountRequired) {
        this.amountRequired = amountRequired;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
