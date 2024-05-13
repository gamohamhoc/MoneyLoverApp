package com.example.moneyloverapp.database.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneyloverapp.database.DatabaseHandler;
import com.example.moneyloverapp.models.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletDAO {
    private SQLiteDatabase db;

    public WalletDAO (Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler (context);
        db = dbHandler.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Wallet> GetData(String sql, String...selectionArgs) {
        List<Wallet> list = new ArrayList<Wallet>();
        Cursor c = db.rawQuery (sql, selectionArgs);
        while (c.moveToNext()) {
            Wallet wallet = new Wallet();

            wallet.setId(c.getInt(c.getColumnIndex("Id")));
            wallet.setName(c.getString(c.getColumnIndex("Name")));
            wallet.setBalance(c.getFloat(c.getColumnIndex("Balance")));
            wallet.setIsIncluded(c.getInt(c.getColumnIndex("IsIncluded")));

            list.add(wallet);
        }

        return list;
    }

    public void Add(Wallet wallet){
        ContentValues values = new ContentValues();

        values.put("Name", wallet.getName());
        values.put("Balance", wallet.getBalance());
        values.put("IsIncluded", wallet.getIsIncluded());
        db.insert(  "Wallets", null, values);
    }

    public void Update(Wallet wallet){
        ContentValues values = new ContentValues();
        values.put("Name", wallet.getName());
        values.put("Balance", wallet.getBalance());
        values.put("IsIncluded", wallet.getIsIncluded());

        db.update("Wallets", values,"Id = ?", new String[] { String.valueOf(wallet.getId())});
    }

    public Wallet GetById(int Id){
        Cursor c = db.query("Wallets", null,"Id = ?", new String[] { String.valueOf(Id) },null, null, null);
        if(c != null)
            c.moveToFirst();
        Wallet wallet = new Wallet(
            c.getInt(0),
            c.getString(1),
            c.getFloat(2),
            c.getInt(3)
        );
        return wallet;
    }

    public List<Wallet> GetAll(){
        String sql = "SELECT * FROM Wallets";
        return GetData(sql);
    }
}
