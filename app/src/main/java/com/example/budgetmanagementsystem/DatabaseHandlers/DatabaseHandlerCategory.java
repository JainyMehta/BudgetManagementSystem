package com.example.budgetmanagementsystem.DatabaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.budgetmanagementsystem.Models.Category;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerCategory extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budgetManagerCategory";
    private static final String TABLE_CATEGORY = "category";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERID = "userid";
    private static int USERID = 0;

    public DatabaseHandlerCategory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SharedPreferences sh = context.getSharedPreferences("BudgetManagementSystemPref", Context.MODE_PRIVATE);

        USERID = sh.getInt("id",0);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_NAME + " TEXT ," + KEY_USERID + " INTEGER " + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // Create tables again
        onCreate(db);
    }

    // code to add the new category
    public Boolean addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, category.getName()); // Category Name
        values.put(KEY_USERID,USERID);

        // Inserting Row
        int i= (int) db.insert(TABLE_CATEGORY, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        if(i== -1){
            return false;
        }else {
            return true;
        }
    }

    // code to get the single category
    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_CATEGORY+" where userid='"+USERID+"' and id="+id,null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));
        // return category
        return category;
    }

    // code to get all categorys in a list view
    public List<Category> getAllCategorys() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY+" where userid='"+USERID+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setName(cursor.getString(1));

                // Adding category to list
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        // return category list
        return categoryList;
    }

    // code to update the single category
    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, category.getName());

        // updating row
        return db.update(TABLE_CATEGORY, values, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
    }

    // Deleting single category
    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }

    // Getting categorys Count
    public int getCategorysCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
