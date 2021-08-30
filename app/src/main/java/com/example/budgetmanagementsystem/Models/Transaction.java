package com.example.budgetmanagementsystem.Models;

public class Transaction {
    int id;
    String name;
    int isIncome;
    int date;
    int month;
    int year;
    double price;
    String description;
    int categoryId;
    int userId;

    public Transaction() {
    }

    public Transaction(int id, String name, int isIncome, int date, int month, int year, double price, String description, int categoryId, int userId) {
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
        this.description = description;
        this.categoryId=categoryId;
        this.userId=userId;
    }

    public Transaction(String name, int isIncome, int date, int month, int year, double price, String description, int categoryId) {
        this.name = name;
        this.isIncome = isIncome;
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
        this.description = description;
        this.categoryId=categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(int isIncome) {
        this.isIncome = isIncome;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

