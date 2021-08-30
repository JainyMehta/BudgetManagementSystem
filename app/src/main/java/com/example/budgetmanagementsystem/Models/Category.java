package com.example.budgetmanagementsystem.Models;

public class Category {
    int id;
    String name;
    int userId;

    public Category() {
    }



    public Category(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public Category(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId=userId;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
