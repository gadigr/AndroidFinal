package com.finalproject.kg.summary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalproject.kg.summary.StudApplication;

import java.util.LinkedList;
import java.util.List;

public class ModelSql {
    private static final int VERSION = 1;

    MyDBHelper dbHelper;

    public ModelSql(final Context context) {
        dbHelper = new MyDBHelper(context);
    }

    public void add(Summary su) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SummarySql.add(db, su);
    }

    public List<Summary> getAllSummaries(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return SummarySql.getAllSummaries(db);
    }

    class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create the DB schema
            SummarySql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            SummarySql.drop(db);
            onCreate(db);
        }
    }
}
