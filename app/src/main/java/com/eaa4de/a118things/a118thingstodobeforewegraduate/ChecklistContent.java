package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by ErinA on 6/2/2017.
 */

public class ChecklistContent {
    public static  ArrayList<ChecklistModel> masterList = new ArrayList<>();
    public static ArrayList<ChecklistModel> showList = new ArrayList<>();
    public  ArrayList<ChecklistModel> allList = new ArrayList<>();


    private static Context context;
    public ChecklistContent(Context c) {
        context = c;
    }


    public ArrayList<ChecklistModel> readDB(SQLiteDatabase db, Cursor cursor, int checked, int checked2) {
        ArrayList<ChecklistModel> myList = new ArrayList<>();
        if(cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            if (done == checked || done == checked2) {
                myList.add(myChecklistModel);
            }
            while(cursor.moveToNext()){
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity, done);
                if (done == checked || done == checked2) {
                    myList.add(myChecklistModel);
                }
            }

        }
        return myList;

    }


    //creates checklistModelArray and hundredThings arraylist, then populates array with items from hundredThings
    public void createArray(SQLiteDatabase db, Cursor cursor) {
        if(cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            if (!masterList.contains(myChecklistModel)){
                masterList.add(myChecklistModel);
            }
            while (cursor.moveToNext()) {
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity, done);
                if (!masterList.contains(myChecklistModel)){
                    masterList.add(myChecklistModel);
                }
            }
        }
    }

    //updates Arraylist with items from the database and returns that ArrayList of ChecklistModels
    public ArrayList<ChecklistModel> updateArray(SQLiteDatabase db, Cursor cursor){
        if (cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            if (masterList.contains(myChecklistModel)){
                masterList.remove(myChecklistModel);
                masterList.add(myChecklistModel);
            }
            while (cursor.moveToNext()){
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity,done);
                if (masterList.contains(myChecklistModel)){
                    masterList.remove(myChecklistModel);
                    masterList.add(myChecklistModel);
                }
            }
         }
        return masterList;
    }



    public void updateDB(SQLiteDatabase db, DbHelper myDbHelper){
        myDbHelper.onDelete(db);
        myDbHelper.onCreate(db);
        for (int i = 0; i < masterList.size(); i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, masterList.get(i).getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, masterList.get(i).getValue());
            Toast.makeText(context,i + " " + masterList.get(i).getName() + ", " + masterList.get(i).getValue(),
                    Toast.LENGTH_SHORT).show();
            //insert new row
            db.insert(DbHelper.FeedEntry.TABLE_NAME, null, checklistValues);
        }
    }


    //puts things into the database from the checklistModelItems array
    public void insertThings(SQLiteDatabase db) {
        for (int i = 0; i < masterList.size(); i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, masterList.get(i).getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, masterList.get(i).getValue());
            //insert new row
            db.insert(DbHelper.FeedEntry.TABLE_NAME, null, checklistValues);
        }

    }

    //fills checklistModelItems with the list of things to do
    public void populateArray() {
        ChecklistModel thing1 = new ChecklistModel("Sing the Good Ole Song", 1);
        ChecklistModel thing2 = new ChecklistModel("Attend Rotunda Sing", 1);
        ChecklistModel thing3 = new ChecklistModel("High-five Dean groves", 1);
        ChecklistModel thing4 = new ChecklistModel("Make a Class Gift", 0);
        ChecklistModel thing5 = new ChecklistModel("Paint Beta Bridge", 0);
        ChecklistModel thing6 = new ChecklistModel("Chow down on late night food on the Corner", 0);
        ChecklistModel thing7 = new ChecklistModel("Tailgate a football game", 0);
        ChecklistModel thing8 = new ChecklistModel("Eat at Pancakes for Parkinson's", 0);
        ChecklistModel thing9 = new ChecklistModel("Check out a book from a library", 0);
        ChecklistModel thing10 = new ChecklistModel("Nab the #1 ticket at Bodo's", 0);
        masterList.add(thing1); masterList.add(thing2); masterList.add(thing3); masterList.add(thing4);
        masterList.add(thing5); masterList.add(thing6); masterList.add(thing7); masterList.add(thing8);
        masterList.add(thing9); masterList.add(thing10);

    }


}

