package com.example.budgetmanagementsystem.Models;

public class BudgetProgress {
    String Name;
    int budget;
    int progress;

    public BudgetProgress() {
    }

    public BudgetProgress(String name, int budget, int progress) {
        Name = name;
        this.budget = budget;
        this.progress = progress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
