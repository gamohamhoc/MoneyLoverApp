package com.example.moneyloverapp.models;

public class Wallet {
    private  int Id;
    private String Name;
    private float Balance;
    private int IsIncluded; // 0 = not, 1 = is included

    public Wallet() {
    }

    public Wallet( int id, String name, float balance, int isIncluded) {
        Name = name;
        Balance = balance;
        IsIncluded = isIncluded;
        Id = id;
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

    public int getIsIncluded() {
        return IsIncluded;
    }

    public void setIsIncluded(int isIncluded) {
        IsIncluded = isIncluded;
    }

    public void setId(int id) {
        Id = id;
    }
}
