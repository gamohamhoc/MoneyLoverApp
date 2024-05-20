package com.example.moneyloverapp.database.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneyloverapp.database.DatabaseHandler;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Wallet;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase db;

    public CategoryDAO (Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler (context);
        db = dbHandler.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Category> GetData(String sql, String...selectionArgs) {
        List<Category> list = new ArrayList<Category>();
        Cursor c = db.rawQuery (sql, selectionArgs);
        while (c.moveToNext()) {
            Category category = new Category();

            category.setId(c.getInt(c.getColumnIndex("Id")));
            category.setName(c.getString(c.getColumnIndex("Name")));
            category.setType(c.getInt(c.getColumnIndex("Type")));

            list.add(category);
        }

        return list;
    }

    public void Add(Category category){
        ContentValues values = new ContentValues();

        values.put("Name", category.getName());
        values.put("Type", category.getType());

        db.insert(  "Categories", null, values);
    }

    public void Update(Category category){
        ContentValues values = new ContentValues();
        values.put("Name", category.getName());
        values.put("Type", category.getType());

        db.update("Categories", values,"Id = ?", new String[] { String.valueOf(category.getId())});
    }

    public Category GetById(int Id){
        Cursor c = db.query("Categories", null,"Id = ?", new String[] { String.valueOf(Id) },null, null, null);
        if(c != null)
            c.moveToFirst();
        Category category = new Category(
                c.getInt(0),
                c.getString(1),
                c.getInt(2)
        );
        return category;
    }

    public List<Category> GetAll(){
        String sql = "SELECT * FROM Categories";
        return GetData(sql);
    }

    public List<Category> GetByType(int type){
        String sql = "SELECT * FROM Categories WHERE Type = ?";
        return GetData(sql, new String[]{type+""});
    }
}
