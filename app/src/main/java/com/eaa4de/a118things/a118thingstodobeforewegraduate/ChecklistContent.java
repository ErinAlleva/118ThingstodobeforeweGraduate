package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ErinA on 6/2/2017.
 */

public class ChecklistContent {

    public static ChecklistModel[] checklistModelItems;

    public void initializeArray(){
        checklistModelItems = new ChecklistModel[10];
    }


    //creates checklistModelArray and hundredThings arraylist, then populates array with items from hundredThings
    public void createArray(SQLiteDatabase db, Cursor cursor) {
        final ArrayList<ChecklistModel> hundredThings = new ArrayList<>();
        if(cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            hundredThings.add(myChecklistModel);
            while (cursor.moveToNext()) {
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity, done);
                hundredThings.add(myChecklistModel);
            }
            //initializeArray();
            checklistModelItems = new ChecklistModel[hundredThings.size()];
            for (int i = 0; i <  hundredThings.size() ; i++) {
                checklistModelItems[i] = hundredThings.get(i);
            }
        }
            //add hundredThings arrayList items to checklistModelItems -not sure why I needed both of these, it's a mess

    }

    //updates Arraylist with items from the database and returns that ArrayList of ChecklistModels
    public ArrayList<ChecklistModel> updateArray(SQLiteDatabase db, Cursor cursor, ArrayList<ChecklistModel> hundredThings){
        if (cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            hundredThings.add(myChecklistModel);
            while (cursor.moveToNext()){
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity,done);
                hundredThings.add(myChecklistModel);
            }
            checklistModelItems = new ChecklistModel[hundredThings.size()];
            for (int i = 0; i <  hundredThings.size() ; i++) {
                checklistModelItems[i] = hundredThings.get(i);
            }
         }
        return hundredThings;
    }


    //fills checklistModelItems with the list of things to do
    public void populateArray() {
            checklistModelItems = new ChecklistModel[10];
          //  if (checklistModelItems.length < 8) {
            checklistModelItems[0] = new ChecklistModel("Nab the #1 ticket at Bodo's", 0);
            checklistModelItems[1] = new ChecklistModel("Sing the Good Ole Song", 1);
            checklistModelItems[2] = new ChecklistModel("Attend Rotunda Sing", 1);
            checklistModelItems[3] = new ChecklistModel("High-five Dean groves", 1);
            checklistModelItems[4] = new ChecklistModel("Make a Class Gift", 0);
            checklistModelItems[5] = new ChecklistModel("Paint Beta Bridge", 0);
            checklistModelItems[6] = new ChecklistModel("Chow down on late night food on the Corner", 0);
            checklistModelItems[7] = new ChecklistModel("Tailgate a football game", 0);
            checklistModelItems[8] = new ChecklistModel("Eat at Pancakes for Parkinson's", 0);
            checklistModelItems[9] = new ChecklistModel("Check out a book from a library", 0);
       // }

    }

    public void updateDB(SQLiteDatabase db, DbHelper myDbHelper){
        myDbHelper.onDelete(db);
        myDbHelper.onCreate(db);
        for (int i = 0; i < checklistModelItems.length; i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, checklistModelItems[i].getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, checklistModelItems[i].getValue());
            //insert new row
            if(checklistModelItems[i].getValue() == 1){db.insert(DbHelper.TABLE_CHECKED_ENTRIES, null, checklistValues);
            } else { db.insert(DbHelper.TABLE_UNCHECKED_ENTRIES,null, checklistValues); }
            db.insert(DbHelper.FeedEntry.TABLE_NAME, null, checklistValues);
        }
    }
    //puts things into the database from the checklistModelItems array
    public void insertThings(SQLiteDatabase db) {
        for (int i = 0; i < checklistModelItems.length; i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, checklistModelItems[i].getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, checklistModelItems[i].getValue());
            //insert new row
            if(checklistModelItems[i].getValue() == 1){db.insert(DbHelper.TABLE_CHECKED_ENTRIES, null, checklistValues);
            } else { db.insert(DbHelper.TABLE_UNCHECKED_ENTRIES,null, checklistValues); }
            db.insert(DbHelper.FeedEntry.TABLE_NAME, null, checklistValues);
        }
}


}

