package com.github.vaggos.serviceapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "service.db";
    public static final String TABLE_NAME = "service_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SPARE_PART";
    public static final String COL_3 = "DATE_CHANGED";
    public static final String COL_4 = "DATE_INTERVAL";
    public static final String COL_5 = "KMS_CHANGED";
    public static final String COL_6 = "KMS_INTERVAL";


    public DatabaseHelper(Context context) {
//        super(context, name, factory, version);
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER, " +
                COL_5 + " INTEGER, " +
                COL_6 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
    }
}
