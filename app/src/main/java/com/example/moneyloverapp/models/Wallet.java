package com.example.moneyloverapp.models;

public class Wallet {
    private  int Id;
    private String Name;
    private float Balance;

    public Wallet() {
    }

    public Wallet(String name, float balance) {
        Name = name;
        Balance = balance;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getBalance() {
        return Balance;
    }

    public void setBalance(float balance) {
        Balance = balance;
    }
}
