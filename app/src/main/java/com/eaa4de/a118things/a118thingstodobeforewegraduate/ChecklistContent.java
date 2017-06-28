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
    public  ArrayList<ChecklistModel> allList = new ArrayList<>();
    public  ArrayList<ChecklistModel> checkedList = new ArrayList<>();
    public  ArrayList<ChecklistModel> unCheckedList = new ArrayList<>();

    private static Context context;
    public ChecklistContent(Context c) {
        context = c;
    }


    public ArrayList<ChecklistModel> readDB (SQLiteDatabase db, Cursor cursor, ArrayList<ChecklistModel> list) {
        ArrayList<ChecklistModel> myList = new ArrayList<>();
        if(cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
           // if (!list.contains(myChecklistModel)) {
                myList.add(myChecklistModel);
            Toast.makeText(context,
                          activity + cursor, Toast.LENGTH_LONG).show();
           // }

            while(cursor.moveToNext()){
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity, done);
                //if (!list.contains(myChecklistModel)) {
                    myList.add(myChecklistModel);
                Toast.makeText(context,
                               activity + cursor, Toast.LENGTH_LONG).show();
                //}
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
            if (!allList.contains(myChecklistModel)){ allList.add(myChecklistModel);}
            if(done == 1){
                if (!checkedList.contains(myChecklistModel))checkedList.add(myChecklistModel);
            } else {
                if (!unCheckedList.contains(myChecklistModel))unCheckedList.add(myChecklistModel);
            }
            while (cursor.moveToNext()) {
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity, done);
                if (!allList.contains(myChecklistModel)){ allList.add(myChecklistModel);}
                if(done == 1){
                    if (!checkedList.contains(myChecklistModel))checkedList.add(myChecklistModel);
                } else {
                    if (!unCheckedList.contains(myChecklistModel))unCheckedList.add(myChecklistModel);
                }
            }
        }
        for (int i = 0; i < unCheckedList.size(); i++) {
            if (unCheckedList.get(i).getValue() == 1) {
                unCheckedList.remove(unCheckedList.get(i));
            }
        }
        for (int i = 0; i < checkedList.size(); i++) {
            if (checkedList.get(i).getValue() == 0) {
                checkedList.remove(checkedList.get(i));
            }
        }
    }

    //updates Arraylist with items from the database and returns that ArrayList of ChecklistModels
    public ArrayList<ChecklistModel> updateArray(SQLiteDatabase db, Cursor cursor){
        //Toast.makeText(context,
         //       "HELLO from updateArray  " + cursor, Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()) {
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            if (!allList.contains(myChecklistModel)){
                allList.remove(myChecklistModel);
                allList.add(myChecklistModel);
            }
            if (!checkedList.contains((myChecklistModel)) && (done == 1) ){
                checkedList.remove(myChecklistModel);
                checkedList.add(myChecklistModel);
            }
            if (checkedList.contains(myChecklistModel) && (done != 1)){
                checkedList.remove(myChecklistModel);
            }
            if (!unCheckedList.contains((myChecklistModel)) && (done != 1)){
                unCheckedList.remove(myChecklistModel);
                unCheckedList.add(myChecklistModel);
            }
            if (unCheckedList.contains(myChecklistModel) && done == 1) {
                unCheckedList.remove(myChecklistModel);
            }
            while (cursor.moveToNext()){
                activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
                done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
                myChecklistModel = new ChecklistModel(activity,done);
                if (!allList.contains(myChecklistModel)){
                    allList.remove(myChecklistModel);
                    allList.add(myChecklistModel);
                }
                if (!checkedList.contains((myChecklistModel)) && (done == 1) ){
                    checkedList.remove(myChecklistModel);
                    checkedList.add(myChecklistModel);
                }
                if (checkedList.contains(myChecklistModel) && (done != 1)){
                    checkedList.remove(myChecklistModel);
                }
                if (!unCheckedList.contains((myChecklistModel)) && (done != 1)){
                    unCheckedList.remove(myChecklistModel);
                    unCheckedList.add(myChecklistModel);
                }
                if (unCheckedList.contains(myChecklistModel) && done == 1) {
                    unCheckedList.remove(myChecklistModel);
                }
            }
         }
        return masterList;
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

    public void updateDB(SQLiteDatabase db, DbHelper myDbHelper){
        Toast.makeText(context,
               "HELLO from updateDB  " , Toast.LENGTH_LONG).show();
        myDbHelper.onDelete(db);
        myDbHelper.onCreate(db);
        for (int i = 0; i < allList.size(); i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, allList.get(i).getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, allList.get(i).getValue());
            //insert new row
            db.insert(DbHelper.FeedEntry.TABLE_NAME, null, checklistValues);
            if (allList.get(i).getValue() == 1){
                db.insert(DbHelper.TABLE_CHECKED_ENTRIES, null, checklistValues);
            }
            if (allList.get(i).getValue() == 0) {
                db.insert(DbHelper.TABLE_UNCHECKED_ENTRIES, null, checklistValues);
            }
        }
        /*for (int i = 0; i < checkedList.size(); i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, checkedList.get(i).getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, checkedList.get(i).getValue());
            //insert new row
            if (checkedList.get(i).getValue() == 1){
                db.insert(DbHelper.TABLE_CHECKED_ENTRIES, null, checklistValues);
            }
        }
        for (int i = 0; i < unCheckedList.size(); i++) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, unCheckedList.get(i).getName());
            checklistValues.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, unCheckedList.get(i).getValue());
            //insert new row
            if (unCheckedList.get(i).getValue() == 0) {
                db.insert(DbHelper.TABLE_UNCHECKED_ENTRIES, null, checklistValues);
            }
        }
        */
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


}

