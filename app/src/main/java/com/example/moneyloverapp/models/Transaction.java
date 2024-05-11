package com.example.moneyloverapp.models;

import java.util.Date;

public class Transaction {
    int Id;
    int CategoryId;
    String Category;
    int WalletId;
    float Amount;
    Date Date;
    String Description;

    public Transaction() {
    }

    public Transaction(int id, int categoryId, int walletId, float amount, Date date, String description) {
        Id = id;
        CategoryId = categoryId;
        WalletId = walletId;
        Amount = amount;
        Date = date;
        Description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getWalletId() {
        return WalletId;
    }

    public void setWalletId(int walletId) {
        WalletId = walletId;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
