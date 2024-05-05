package com.example.moneyloverapp.models;

public class Wallet {
    private  int Id;
    private String Name;
    private float Balance;
    private int Image;

    public Wallet() {
    }

    public Wallet(String name, float balance, int image) {
        Name = name;
        Balance = balance;
        Image = image;
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

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
