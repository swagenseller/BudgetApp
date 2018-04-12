package com.example.jeff.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jeff on 11/19/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    //Database------------------------------------
    public static final String DATABASE_NAME = "budget.db";

    //Tables--------------------------------------
    public static final String CATEGORY = "category";
    public static final String CHARGES = "charges";

    //Table Columns--------------------------------

            //category columns
    public static final String CAT_COL1 = "ID";
    public static final String CAT_COL2 = "Name";
    public static final String CAT_COL3 = "Amount";

            //category data columns

    public static final String CHA_COL1 = "ID";
    public static final String CHA_COL2 = "Category_ID";
    public static final String CHA_COL3 = "Name";
    public static final String CHA_COL4 = "Amount";
    public static final String CHA_COL5 = "Date";




    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " +CATEGORY+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Amount INTEGER )");
        db.execSQL("create table " +CHARGES+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, Category_ID INTEGER, Name TEXT, Amount INTEGER, Date INTEGER )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY );
        db.execSQL("DROP TABLE IF EXISTS " + CHARGES );
        onCreate(db);
    }

    public boolean insertCategory(String name, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_COL2,name);
        contentValues.put(CAT_COL3,amount);
        long result = db.insert(CATEGORY,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void removeItem(String tableName, String colName, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +tableName+ " WHERE " +colName+ "='" +value+ "'");

    }



}
