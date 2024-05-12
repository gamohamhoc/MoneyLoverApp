package com.example.moneyloverapp.models;

import java.util.Date;
import java.util.List;

public class TransactionsByDate {
    List<Transaction> transactions;
    Date date;
    float totalAmount;

    public TransactionsByDate() {
    }

    public TransactionsByDate(List<Transaction> transactions, Date date, float totalAmount) {
        this.transactions = transactions;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transaction) {
        this.transactions = transaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
