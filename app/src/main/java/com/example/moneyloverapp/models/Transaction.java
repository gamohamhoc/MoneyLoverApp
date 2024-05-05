package com.example.moneyloverapp.models;

import java.util.Date;

public class Transaction {
    int Id;
    String Description;
    String Category;
    float Amount;
    int WalletId;
    Date CreatedDate;

    public Transaction(String description, String category, float amount, int walletId, Date createdDate) {
        Description = description;
        Category = category;
        Amount = amount;
        WalletId = walletId;
        CreatedDate = createdDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getWalletId() {
        return WalletId;
    }

    public void setWalletId(int walletId) {
        WalletId = walletId;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }
}
