package com.example.budgetmanagementsystem.DatabaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.budgetmanagementsystem.Models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerTransaction extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BudgetManagerTransaction";
    private static final String TABLE_TRANSACTION = "transactions";
    private static final String KEY_ID = "id";
    private static final String KEY_ISINCOME = "isIncome";
    private static final String KEY_DATE = "date";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORYID = "categoryid";
    private static final String KEY_USERID = "userid";
    private static int USERID = 0;

    public DatabaseHandlerTransaction(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        SharedPreferences sh = context.getSharedPreferences("BudgetManagementSystemPref", Context.MODE_PRIVATE);

        USERID = sh.getInt("id",0);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"  + KEY_NAME + " TEXT," + KEY_ISINCOME + " INTEGER,"+ KEY_DATE + " INTEGER,"
                + KEY_MONTH + " INTEGER," + KEY_YEAR + " INTEGER," + KEY_PRICE + " REAL," + KEY_DESCRIPTION + " TEXT,"+ KEY_CATEGORYID + " INTEGER, "  + KEY_USERID + " INTEGER " +  ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

        // Create tables again
        onCreate(db);
    }

    // code to add the new transaction
    public Boolean addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, transaction.getName());
        values.put(KEY_ISINCOME, transaction.getIsIncome());
        values.put(KEY_DATE, transaction.getDate());// Transaction Name
        values.put(KEY_MONTH, transaction.getMonth());
        values.put(KEY_YEAR, transaction.getYear());// Transaction Phone
        values.put(KEY_PRICE, transaction.getPrice());
        values.put(KEY_DESCRIPTION,transaction.getDescription());
        values.put(KEY_CATEGORYID,transaction.getCategoryId());
        values.put(KEY_USERID,USERID);

        // Inserting Row
        int i= (int) db.insert(TABLE_TRANSACTION, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        if(i== -1){
            return false;
        }else {
            return true;
        }
    }

    // code to get the single transaction
    public Transaction getTransaction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_TRANSACTION+" where userid='"+USERID+"' and id="+id,null);
        if (cursor != null)
            cursor.moveToFirst();

        Transaction transaction = new Transaction(cursor.getInt(0), cursor.getString(1),cursor.getInt(2),
                cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getDouble(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9));
        // return transaction
        return transaction;
    }

    // code to get all transactions in a list view
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION +" where userid='"+USERID+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.parseInt(cursor.getString(0)));
                transaction.setName(cursor.getString(1));
                transaction.setIsIncome(cursor.getInt(2));
                transaction.setDate(cursor.getInt(3));
                transaction.setMonth(cursor.getInt(4));
                transaction.setYear(cursor.getInt(5));
                transaction.setPrice(cursor.getDouble(6));
                transaction.setDescription(cursor.getString(7));
                transaction.setCategoryId(cursor.getInt(8));
                // Adding transaction to list
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }

        // return transaction list
        return transactionList;
    }

    // code to update the single transaction
    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, transaction.getName());
        values.put(KEY_ISINCOME, transaction.getIsIncome());
        values.put(KEY_DATE, transaction.getDate());// Transaction Name
        values.put(KEY_MONTH, transaction.getMonth());
        values.put(KEY_YEAR, transaction.getYear());// Transaction Phone
        values.put(KEY_PRICE, transaction.getPrice());
        values.put(KEY_DESCRIPTION,transaction.getDescription());

        // updating row
        return db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
                new String[]{String.valueOf(transaction.getId())});
    }

    // Deleting single transaction
    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTION, KEY_ID + " = ?",
                new String[]{String.valueOf(transaction.getId())});
        db.close();
    }

    // Getting transactions Count
    public int getTransactionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRANSACTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public List<Transaction> getAllMonthTransactions(int month, int year) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION +" where userid='"+USERID+"' and month="+month+" and year="+year;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.parseInt(cursor.getString(0)));
                transaction.setName(cursor.getString(1));
                transaction.setIsIncome(cursor.getInt(2));
                transaction.setDate(cursor.getInt(3));
                transaction.setMonth(cursor.getInt(4));
                transaction.setYear(cursor.getInt(5));
                transaction.setPrice(cursor.getDouble(6));
                transaction.setDescription(cursor.getString(7));
                transaction.setCategoryId(cursor.getInt(8));
                // Adding transaction to list
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }

        // return transaction list
        return transactionList;
    }

    public int getSumMonthTransactions(int month, int year, int categoryId) {
        int sum=0;
        // Select All Query
        String selectQuery = "SELECT  sum("+KEY_PRICE+") FROM " + TABLE_TRANSACTION +" where userid='"+USERID+"' and month="+month+" and year="+year;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sum=cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        // return transaction list
        return sum;
    }
}
