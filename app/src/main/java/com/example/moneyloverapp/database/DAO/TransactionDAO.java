package com.example.moneyloverapp.database.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneyloverapp.database.DatabaseHandler;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private SQLiteDatabase db;
    private CategoryDAO categoryDAO;

    public TransactionDAO (Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler (context);
        categoryDAO = new CategoryDAO(context);
        db = dbHandler.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Transaction> GetData(String sql, String...selectionArgs) {
        List<Transaction> list = new ArrayList<Transaction>();
        Cursor c = db.rawQuery (sql, selectionArgs);
        while (c.moveToNext()) {
            Transaction transaction = new Transaction();

            transaction.setId(c.getInt(c.getColumnIndex("Id")));
            transaction.setCategoryId(c.getInt(c.getColumnIndex("CategoryId")));
            transaction.setWalletId(c.getInt(c.getColumnIndex("WalletId")));
            transaction.setAmount(c.getFloat(c.getColumnIndex("Amount")));
            transaction.setDate(DateTimeUltilities.StringToDate("dd/MM/yyyy", c.getString(c.getColumnIndex("Date"))));
            transaction.setDescription(c.getString(c.getColumnIndex("Description")));

            transaction.setCategory(categoryDAO.GetById(c.getInt(c.getColumnIndex("CategoryId"))).getName());

            list.add(transaction);
        }

        return list;
    }

    public void Add(Transaction transaction){
        ContentValues values = new ContentValues();

        values.put("CategoryId", transaction.getCategoryId());
        values.put("WalletId", transaction.getWalletId());
        values.put("Amount", transaction.getAmount());
        values.put("Date", DateTimeUltilities.FormatDate("dd/MM/yyyy", transaction.getDate()));
        values.put("Description", transaction.getDescription());

        db.insert(  "Transactions", null, values);
    }

    public void Update(Transaction transaction){
        ContentValues values = new ContentValues();

        values.put("CategoryId", transaction.getCategoryId());
        values.put("WalletId", transaction.getWalletId());
        values.put("Amount", transaction.getAmount());
        values.put("Date", DateTimeUltilities.FormatDate("dd/MM/yyyy", transaction.getDate()));
        values.put("Description", transaction.getDescription());

        db.update("Transactions", values,"Id = ?", new String[] { String.valueOf(transaction.getId())});
        db.close();
    }

    public Transaction GetById(int Id){
        Cursor c = db.query("Transactions", null,"Id = ?", new String[] { String.valueOf(Id) },null, null, null);
        if(c != null)
            c.moveToFirst();
        Transaction transaction = new Transaction(
                c.getInt(0),
                c.getInt(1),
                c.getInt(2),
                c.getFloat(3),
                DateTimeUltilities.StringToDate("dd/MM/yyyy",c.getString(4)),
                c.getString(5)
        );
        transaction.setCategory(categoryDAO.GetById(c.getInt(0)).getName());

        return transaction;
    }

    public List<Transaction> GetAll(){
        String sql = "SELECT * FROM Transactions";
        return GetData(sql);
    }

    public List<Transaction> GetTransactionsbyWalletId(int walletId){
        String sql = "SELECT * FROM Transactions WHERE WalletId = ?";
        return GetData(sql, new String[]{walletId+""});
    }
}
