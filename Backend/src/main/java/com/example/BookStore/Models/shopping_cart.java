package com.example.BookStore.Models;

import java.sql.Date;

public class shopping_cart {
    private int id;
    private String userName;
    private int amountRequired;
    private int total_price;
    private String state;
private Date date;
    public shopping_cart() {
    }

    public String getState() {
        return state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public shopping_cart(int id, String userName, int amountRequired, int total_price, String state) {
        this.id = id;
        this.userName = userName;
        this.amountRequired = amountRequired;
        this.total_price = total_price;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public void setAmountRequired(int amountRequired) {
        this.amountRequired = amountRequired;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String isState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
