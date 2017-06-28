package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by ErinA on 5/24/2017.
 */

public class CustomAdapter extends ArrayAdapter{//&lt;ChecklistModel&gt;{
        ChecklistContent checklistContent = new ChecklistContent(getContext());
        public static ArrayList<Boolean> itemChecked1 = null;
        //public static ArrayList<ChecklistModel> checkedItems= new ArrayList<>();
        //public static ArrayList<ChecklistModel> uncheckedItems= new ArrayList<>();

        final DbHelper myDbHelper = new DbHelper(getContext());
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        final Cursor checkedCursor = db.rawQuery("select * from " + DbHelper.TABLE_CHECKED_ENTRIES, null);
        final Cursor unCheckedCursor = db.rawQuery("select * from " + DbHelper.TABLE_UNCHECKED_ENTRIES, null);
        final Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
        Context context;
        public CustomAdapter(Context context, ArrayList<ChecklistModel> resource){
        super(context,R.layout.list_view_row,resource);
        this.context=context;
        checklistContent.masterList=resource;
            itemChecked1 = new ArrayList<Boolean>();
           for (int i = 0; i < checklistContent.masterList.size(); i++) {
                itemChecked1.add(i,checklistContent.masterList.get(i).getValue()==0);
            }

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_row, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.textView1);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
            name.setText(ChecklistContent.masterList.get(position).getName());
            if(ChecklistContent.masterList.get(position).getValue()==1) {
                cb.setChecked(true);
                //if checked, check if it is in checkedList, if not add to list

                if(!checklistContent.checkedList.contains(checklistContent.masterList.get(position))){
                    checklistContent.checkedList.add(checklistContent.masterList.get(position));
                }
                //also check if in unChecked, if in unChecked remove
                if(checklistContent.unCheckedList.contains(checklistContent.masterList.get(position))){
                    checklistContent.unCheckedList.remove(checklistContent.masterList.get(position));
                }
                if (!checklistContent.allList.contains(checklistContent.masterList.get(position))){
                    checklistContent.allList.add((checklistContent.masterList.get(position)));
                }

            } else {
                cb.setChecked(false);
                //check in in checkedList, if it is, then remove it because it shouldnt be in checked

                if(checklistContent.checkedList.contains(checklistContent.masterList.get(position))){
                    checklistContent.checkedList.remove(checklistContent.masterList.get(position));
                }
                //also check if not in unChecked, if not in unChecked then add it
                if(!checklistContent.unCheckedList.contains(checklistContent.masterList.get(position))){
                    checklistContent.unCheckedList.add(checklistContent.masterList.get(position));
                }
                if (!checklistContent.allList.contains(checklistContent.masterList.get(position))){
                    checklistContent.allList.add((checklistContent.masterList.get(position)));
                }

            }

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View view) {
                    //checklistContent.updateArray(db, cursor);
                    //checklistContent.updateArray(db, checkedCursor);
                    //checklistContent.updateArray(db, unCheckedCursor);
                    checklistContent.updateDB(db, myDbHelper);
                    if (cb.isChecked()) {
                        checklistContent.masterList.get(position).setValue(1);
                    } else {
                        checklistContent.masterList.get(position).setValue(0);
                    }
                    checklistContent.updateDB(db, myDbHelper);
                }
            });
            return convertView;
        }
}
