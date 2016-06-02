package com.github.vaggos.serviceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER, " +
                COL_5 + " INTEGER, " +
                COL_6 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }

    public boolean insertData(
            String spare_part,
            String date_changed,
            int date_interval,
            int kms_changed,
            int kms_interval) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, spare_part);
        contentValues.put(COL_3, date_changed);
        contentValues.put(COL_4, date_interval);
        contentValues.put(COL_5, kms_changed);
        contentValues.put(COL_6, kms_interval);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(
            String id,
            String spare_part,
            String date_changed,
            int date_interval,
            int kms_changed,
            int kms_interval) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, spare_part);
        contentValues.put(COL_3, date_changed);
        contentValues.put(COL_4, date_interval);
        contentValues.put(COL_5, kms_changed);
        contentValues.put(COL_6, kms_interval);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}
