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
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static android.R.attr.id;
import static android.R.attr.uncertainGestureColor;
//import static com.eaa4de.a118things.a118thingstodobeforewegraduate.ChecklistContent.masterList;
//import static com.eaa4de.a118things.a118thingstodobeforewegraduate.ChecklistContent.allList;


public class MainActivity extends Activity {
    ListView lv;
    private static final String savedThingsKey = "savedThings";
    ChecklistContent checklistFiller = new ChecklistContent(this);
    boolean uncheckedButtonClicked = false;
    boolean allButtonClicked = false;
    boolean checkedButtonClicked = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Context mContext = this;
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button showCheckedButton = (Button) findViewById(R.id.button1);
        final Button showAllButton = (Button) findViewById(R.id.button2);
        final Button showUncheckedButton = (Button) findViewById(R.id.button3);

        //initialize helper and db
        final DbHelper myDbHelper = new DbHelper(this);
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);


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

        myDbHelper.onDelete(db);
        myDbHelper.onCreate(db);
        checklistFiller.populateArray();
        checklistFiller.insertThings(db);
        checklistFiller.createArray(db, cursor);


        checklistFiller.createArray(db,cursor);
        if (savedInstanceState==null && cursor.moveToFirst()) {
            checklistFiller.createArray(db, cursor);
            checklistFiller.updateDB(db, myDbHelper);
        } else if (cursor.moveToFirst()) {
            checklistFiller.updateDB(db, myDbHelper);
            Toast.makeText(MainActivity.this,
                    "HELLO from the else if cursor.moveToFirst", Toast.LENGTH_LONG).show();
        } else {
            myDbHelper.onDelete(db);
            myDbHelper.onCreate(db);
            checklistFiller.populateArray();
            checklistFiller.insertThings(db);
            checklistFiller.createArray(db, cursor);
        }

        lv = (ListView) findViewById(R.id.listView1);


        showCheckedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
                showCheckedButton.setTextColor(Color.YELLOW);
                showAllButton.setTextColor(Color.BLACK);
                showUncheckedButton.setTextColor(Color.BLACK);
                checkedButtonClicked = true;
                allButtonClicked = false;
                uncheckedButtonClicked = false;
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.readDB(db, cursor, 1, 1));
                lv.setAdapter(adapter);
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
                showAllButton.setTextColor(Color.YELLOW);
                showUncheckedButton.setTextColor(Color.BLACK);
                showCheckedButton.setTextColor(Color.BLACK);
                checkedButtonClicked = false;
                allButtonClicked = true;
                uncheckedButtonClicked = false;
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.readDB(db, cursor, 0, 1));
                lv.setAdapter(adapter);
            }
        });

        showUncheckedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
                showAllButton.setTextColor(Color.BLACK);
                showCheckedButton.setTextColor(Color.BLACK);
                showUncheckedButton.setTextColor(Color.YELLOW);
                checkedButtonClicked = false;
                allButtonClicked = false;
                uncheckedButtonClicked = true;
                CustomAdapter adapter = new CustomAdapter(mContext, checklistFiller.readDB(db, cursor, 0,0));
                lv.setAdapter(adapter);
            }
            //checklistFiller.create
        });


        //CustomAdapter adapter = new CustomAdapter(this, checklistFiller.createListOfChecked());
        CustomAdapter adapter = new CustomAdapter(this, checklistFiller.masterList);
        lv.setAdapter(adapter);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        DbHelper myDbHelper = new DbHelper(this);
        super.onSaveInstanceState(outState);
        ArrayList<ChecklistModel> hundredThings = new ArrayList<>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);

        if (uncheckedButtonClicked) {
            //cursor = db.rawQuery("select * from " + DbHelper.TABLE_UNCHECKED_ENTRIES,null);
        } else if (checkedButtonClicked){
            //cursor = db.rawQuery("select * from " + DbHelper.TABLE_CHECKED_ENTRIES,null);
        } else {
            //cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
        }

       //checks for database existence
        //creates new object from info in datbase and adds it to the hundred things arraylist
     if (cursor.moveToFirst()) {
         String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
         int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
         ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
         hundredThings.add(myChecklistModel);
        }
        while (cursor.moveToNext()){
            String activity = cursor.getString(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_ACTIVITY));
            int done = cursor.getInt(cursor.getColumnIndex(DbHelper.FeedEntry.COLUMN_NAME_CHECKED));
            ChecklistModel myChecklistModel = new ChecklistModel(activity, done);
            hundredThings.add(myChecklistModel);
        }
        outState.putSerializable("savedThings", hundredThings);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
    }


}
