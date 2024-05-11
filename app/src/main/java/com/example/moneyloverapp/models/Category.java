package com.example.moneyloverapp.models;

public class Category {
    int Id;
    String Name;
    int Type;

    public Category() {
    }

    public Category(int id, String name, int type) {
        Id = id;
        Name = name;
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
