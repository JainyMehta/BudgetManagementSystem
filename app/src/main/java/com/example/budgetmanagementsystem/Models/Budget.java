package com.example.budgetmanagementsystem.Models;

public class Budget {
    int id;
    int categoryId;
    int month;
    int year;
    double budget;
    double reach;
    int userId;//how much is spent of earned

    public Budget() {
    }

    public Budget(int id, int categoryId, int month, int year, double budget, double reach,int userId) {
        this.id = id;
        this.categoryId = categoryId;
        this.month = month;
        this.year = year;
        this.budget = budget;
        this.reach = reach;
        this.userId=userId;
    }

    public Budget(int categoryId, int month, int year, double budget, double reach) {
        this.categoryId = categoryId;
        this.month = month;
        this.year = year;
        this.budget = budget;
        this.reach=reach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}
