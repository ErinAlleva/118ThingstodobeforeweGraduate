package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ErinA on 6/1/2017.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ACTIVITY = "activity";
        public static final String COLUMN_NAME_CHECKED = "checked";
    }


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Checklist.db";
    //public static final String TABLE_CHECKED_ENTRIES = "checked_entries";
    //public static final String CHECKED_ACTIVITY = "checked_activty";
    public static final String IS_CHECKED = "is_checked";
    //public static final String TABLE_UNCHECKED_ENTRIES = "unchecked_entries";
    //public static final String UNCHECKED_ACTIVITY = "unchecked_activty";
    public static final String IS_UNCHECKED = "is_unchecked";

    private static final String SQL_CREATE_ENTRIES = " CREATE TABLE " +
            DbHelper.FeedEntry.TABLE_NAME + " (" + DbHelper.FeedEntry._ID + " INTEGER PRIMARY KEY,"
            + DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY + " TEXT, " +
            DbHelper.FeedEntry.COLUMN_NAME_CHECKED + " TEXT)";

    //private static final String SQL_CREATE_CHECKED_ENTRIES = " CREATE TABLE " +
      //      TABLE_CHECKED_ENTRIES+ " (" + DbHelper.FeedEntry._ID + " INTEGER PRIMARY KEY,"
        //    + DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY + " TEXT, " +
         //   DbHelper.FeedEntry.COLUMN_NAME_CHECKED + " TEXT)";

    //private static final String SQL_CREATE_UNCHECKED_ENTRIES = " CREATE TABLE " +
//            TABLE_UNCHECKED_ENTRIES + " (" + DbHelper.FeedEntry._ID + " INTEGER PRIMARY KEY,"
 //           + DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY + " TEXT, " +
  //          DbHelper.FeedEntry.COLUMN_NAME_CHECKED + " TEXT)";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        //db.execSQL(SQL_CREATE_CHECKED_ENTRIES);
        //db.execSQL(SQL_CREATE_UNCHECKED_ENTRIES);
    }

    public void onDelete(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKED_ENTRIES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNCHECKED_ENTRIES );
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME );
    }

    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKED_ENTRIES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNCHECKED_ENTRIES );
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME );
        onCreate(db);
    }







}

