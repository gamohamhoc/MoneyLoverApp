package com.example.moneyloverapp.models;

import java.util.Date;

public class TransactionsByDate {
    Transaction transaction;
    Date date;

    public TransactionsByDate(Transaction transaction, Date date) {
        this.transaction = transaction;
        this.date = date;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
