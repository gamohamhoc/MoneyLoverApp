package com.example.moneyloverapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MoneyLoverDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_WALLET_TABLE =
            "CREATE TABLE Wallets(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Balance REAL NOT NULL," +
                "IsIncluded INTEGER NOT NULL);";
    private static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE Categories(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Type INTEGER NOT NULL);";
    private static final String CREATE_TRANSACTION_TABLE =
            "CREATE TABLE Transactions(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CategoryId INTEGER REFERENCES Categories (Id)," +
                "WalletId INTEGER REFERENCES Wallets (Id)," +
                "Amount INTEGER NOT NULL," +
                "Date TEXT NOT NULL," +
                "Description TEXT);";
    private static final String CREATE_BUDGET_TABLE =
            "CREATE TABLE Budgets(" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "CategoryId INTEGER REFERENCES Categories (Id)," +
                    "WalletId INTEGER REFERENCES Wallets (Id)," +
                    "Amount INTEGER NOT NULL," +
                    "StartDate TEXT NOT NULL," +
                    "EndDate TEXT NOT NULL);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(CREATE_WALLET_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_BUDGET_TABLE);
        //insert data
        DataGenerator.InsertData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Wallets");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        db.execSQL("DROP TABLE IF EXISTS Budgets");

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }
}
