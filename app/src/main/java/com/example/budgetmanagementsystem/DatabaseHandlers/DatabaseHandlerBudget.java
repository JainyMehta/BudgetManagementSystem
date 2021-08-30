package com.example.budgetmanagementsystem.DatabaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.budgetmanagementsystem.Models.Budget;
import com.example.budgetmanagementsystem.Models.Category;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerBudget extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budgetManagerBudget";
    private static final String TABLE_BUDGET = "budgets";
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORYID = "categoryId";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_BUDGET = "budget";
    private static final String KEY_REACH = "reach";
    private static final String KEY_USERID = "userid";
    private static int USERID = 0;

    public DatabaseHandlerBudget(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        SharedPreferences sh = context.getSharedPreferences("BudgetManagementSystemPref", Context.MODE_PRIVATE);

        USERID = sh.getInt("id",0);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_BUDGET + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_CATEGORYID + " INTEGER,"
                + KEY_MONTH + " INTEGER," + KEY_YEAR + " INTEGER," + KEY_BUDGET + " REAL," + KEY_REACH + " REAL," + KEY_USERID + " INTEGER " + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);

        // Create tables again
        onCreate(db);
    }

    // code to add the new budget
    public Boolean addBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORYID, budget.getCategoryId()); // Budget Name
        values.put(KEY_MONTH, budget.getMonth());
        values.put(KEY_YEAR, budget.getYear());// Budget Phone
        values.put(KEY_BUDGET, budget.getBudget());
        values.put(KEY_REACH, budget.getReach());
        values.put(KEY_USERID,USERID);

        // Inserting Row
        int i= (int) db.insert(TABLE_BUDGET, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        if(i== -1){
            return false;
        }else {
            return true;
        }
    }

    // code to get the single budget
    public Budget getBudget(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BUDGET, new String[]{KEY_ID,
                        KEY_CATEGORYID, KEY_MONTH, KEY_YEAR, KEY_BUDGET, KEY_REACH, KEY_USERID}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Budget budget = new Budget(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)), Double.parseDouble(cursor.getString(4)),Double.parseDouble(cursor.getString(5)),cursor.getInt(6));
        // return budget
        return budget;
    }

    // code to get all budgets in a list view
    public List<Budget> getAllBudgets() {
        List<Budget> budgetList = new ArrayList<Budget>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BUDGET+" where userid='"+USERID+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();
                budget.setId(Integer.parseInt(cursor.getString(0)));
                budget.setCategoryId(Integer.parseInt(cursor.getString(1)));
                budget.setMonth(Integer.parseInt(cursor.getString(2)));
                budget.setYear(Integer.parseInt(cursor.getString(3)));
                budget.setBudget(cursor.getDouble(4));
                budget.setReach(cursor.getDouble(5));
                // Adding budget to list
                budgetList.add(budget);
            } while (cursor.moveToNext());
        }

        // return budget list
        return budgetList;
    }

    // code to update the single budget
    public int updateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORYID, budget.getCategoryId());
        values.put(KEY_MONTH, budget.getMonth());
        values.put(KEY_YEAR, budget.getYear());
        values.put(KEY_BUDGET, budget.getBudget());
        values.put(KEY_REACH,budget.getReach());

        // updating row
        return db.update(TABLE_BUDGET, values, KEY_ID + " = ?",
                new String[]{String.valueOf(budget.getId())});
    }

    // Deleting single budget
    public void deleteBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUDGET, KEY_ID + " = ?",
                new String[]{String.valueOf(budget.getId())});
        db.close();
    }

    // Getting budgets Count
    public int getBudgetsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BUDGET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // code to get all budgets in a list view
    public List<Integer> getAllCategoriesofBudgets(int month, int year) {
        List<Integer> budgetList = new ArrayList<Integer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BUDGET+" where userid='"+USERID+"' and month="+month+" and year="+year;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                budgetList.add(Integer.parseInt(cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        // return budget list
        return budgetList;
    }

    public List<Budget> getAllCategoriesMonthlyofBudgets(int month, int year) {
        List<Budget> budgetList = new ArrayList<Budget>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BUDGET+" where userid='"+USERID+"' and month="+month+" and year="+year;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();
                budget.setId(Integer.parseInt(cursor.getString(0)));
                budget.setCategoryId(Integer.parseInt(cursor.getString(1)));
                budget.setMonth(Integer.parseInt(cursor.getString(2)));
                budget.setYear(Integer.parseInt(cursor.getString(3)));
                budget.setBudget(cursor.getDouble(4));
                budget.setReach(cursor.getDouble(5));
                // Adding budget to list
                budgetList.add(budget);
            } while (cursor.moveToNext());
        }

        // return budget list
        return budgetList;
    }
}
