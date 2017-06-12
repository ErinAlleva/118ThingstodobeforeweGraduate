package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static android.R.attr.id;


public class MainActivity extends Activity {
    ListView lv;
    private static final String savedThingsKey = "savedThings";
    ChecklistContent checklistFiller = new ChecklistContent();
    final ArrayList<ChecklistModel> hundredThings = new ArrayList<>();
/*
    final DbHelper myDbHelper = new DbHelper(this);
    final SQLiteDatabase db = myDbHelper.getWritableDatabase();
    final Cursor checkedCursor = db.rawQuery("select * from " + DbHelper.TABLE_CHECKED_ENTRIES, null);
    final Cursor unCheckedCursor = db.rawQuery("select * from " + DbHelper.TABLE_UNCHECKED_ENTRIES, null);

*/
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Context mContext = this;
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button showCheckedButton = (Button) findViewById(R.id.button1);
        final Button showAllButton = (Button) findViewById(R.id.button2);
        final Button showUncheckedButton = (Button) findViewById(R.id.button3);
        final Button saveButton = (Button) findViewById(R.id.button4);
        //initialize helper and db
        final DbHelper myDbHelper = new DbHelper(this);
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        final Cursor checkedCursor = db.rawQuery("select * from " + DbHelper.TABLE_CHECKED_ENTRIES, null);
        final Cursor unCheckedCursor = db.rawQuery("select * from " + DbHelper.TABLE_UNCHECKED_ENTRIES, null);


        //database visualizer on chrome://inspect
        Stetho.newInitializerBuilder(this).enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initializeWithDefaults(this);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        //if just starting app and the database exists, add db contents to hundredThings arrayList
        //final ArrayList<ChecklistModel> hundredThings = new ArrayList<>();
        final Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
        if (savedInstanceState==null && cursor.moveToFirst()) {
            checklistFiller.createArray(db, cursor);
            checklistFiller.updateDB(db, myDbHelper);
        } else if (cursor.moveToFirst()) {
            checklistFiller.updateDB(db, myDbHelper);
        } else {
            myDbHelper.onDelete(db);
            myDbHelper.onCreate(db);
            checklistFiller.populateArray();
            checklistFiller.insertThings(db);
        }


        lv = (ListView) findViewById(R.id.listView1);

        showCheckedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCheckedButton.setTextColor(Color.YELLOW);
                //checklistFiller.updateArray(db,checkedCursor,hundredThings);
                checklistFiller.createArray(db, checkedCursor);
                //checklistFiller.updateDB(db, myDbHelper);
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.checklistModelItems);
                lv.setAdapter(adapter);
                checklistFiller.updateDB(db, myDbHelper);
                //checklistFiller.updateArray(db, cursor, hundredThings);
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //checklistFiller.updateArray(db,cursor, hundredThings);
                checklistFiller.createArray(db, cursor);
                checklistFiller.updateDB(db, myDbHelper);
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.checklistModelItems);
                lv.setAdapter(adapter);
                //checklistFiller.updateArray(db, cursor, hundredThings);
            }
        });

        showUncheckedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //checklistFiller.updateDB(db,myDbHelper);
                //checklistFiller.updateArray(db,unCheckedCursor,hundredThings);
                checklistFiller.createArray(db,unCheckedCursor);
                //checklistFiller.updateDB(db, myDbHelper);
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.checklistModelItems);
                lv.setAdapter(adapter);
                //checklistFiller.updateArray(db, cursor, hundredThings);
            }
            //checklistFiller.create
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checklistFiller.createArray(db, cursor);
                //checklistFiller.updateDB(db, myDbHelper);
                showCheckedButton.setTextColor(Color.BLACK);
                for (int i=0; i < checklistFiller.checklistModelItems.length; i++){
                    if (checkedCursor.moveToFirst()){
                        if (checklistFiller.checklistModelItems[i].getName()==cursor.getString(checkedCursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY))) {
                            int done = checklistFiller.checklistModelItems[i].getValue();
                            ContentValues cv = new ContentValues();
                            cv.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, checkedCursor.getString(checkedCursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY)));
                            cv.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED, done);
                            db.update(myDbHelper.TABLE_CHECKED_ENTRIES, cv, "_id=" + id, null);
                        }
                        while(checkedCursor.moveToNext()){
                                int done = checklistFiller.checklistModelItems[i].getValue();
                                ContentValues cv = new ContentValues();
                                cv.put(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY, checkedCursor.getString(checkedCursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY)));
                                cv.put(DbHelper.FeedEntry.COLUMN_NAME_CHECKED,done);
                                db.update(myDbHelper.TABLE_CHECKED_ENTRIES, cv, "_id="+ id, null);
                        }

                    }
                }

                }
             });

        //CustomAdapter adapter = new CustomAdapter(this, checklistFiller.createListOfChecked());
        CustomAdapter adapter = new CustomAdapter(this, checklistFiller.checklistModelItems);
        lv.setAdapter(adapter);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        DbHelper myDbHelper = new DbHelper(this);
        super.onSaveInstanceState(outState);
        ArrayList<ChecklistModel> hundredThings = new ArrayList<>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        myDbHelper.onDelete(db);
        myDbHelper.onCreate(db);
        Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
       //checklistFiller.populateArray();
        //checklistFiller.createArray(db, cursor);
        checklistFiller.insertThings(db);
        //Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);

       //checks for database existence
        //creates new object from info in datbase and adds it to the hundred things arraylist
     if (cursor.moveToFirst()) {
           hundredThings = checklistFiller.updateArray(db, cursor, hundredThings);
        }
        outState.putSerializable("savedThings", hundredThings);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        /* super.onRestoreInstanceState(savedInstanceState);
        final ArrayList<ChecklistModel> hundredThings = (ArrayList<ChecklistModel>) savedInstanceState.getSerializable("savedThings");
        for (int i = 0; i < checklistFiller.checklistModelItems.length; i++) {
            checklistFiller.checklistModelItems[i] = hundredThings.get(i);
        }
        */
    }


}
