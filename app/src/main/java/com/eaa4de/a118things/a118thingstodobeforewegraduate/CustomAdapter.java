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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

//import static com.eaa4de.a118things.a118thingstodobeforewegraduate.ChecklistContent.allList;


/**
 * Created by ErinA on 5/24/2017.
 */

public class CustomAdapter extends ArrayAdapter{//&lt;ChecklistModel&gt;{
        ChecklistContent checklistContent = new ChecklistContent(getContext());
        public ArrayList<Boolean> itemChecked1 = null;


        final DbHelper myDbHelper = new DbHelper(getContext());
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        final Cursor cursor = db.rawQuery("select * from " + DbHelper.FeedEntry.TABLE_NAME,null);
        Context context;
        public CustomAdapter(Context context, ArrayList<ChecklistModel> resource){
        super(context,R.layout.list_view_row,resource);
        this.context=context;
        checklistContent.showList=resource;
            itemChecked1 = new ArrayList<Boolean>();
           for (int i = 0; i < checklistContent.showList.size(); i++) {
                itemChecked1.add(i,checklistContent.showList.get(i).getValue()==0);
            }

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_row, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.textView1);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
            name.setText(ChecklistContent.showList.get(position).getName());
            if(ChecklistContent.showList.get(position).getValue()==1) {
                cb.setChecked(true);

            } else {
                cb.setChecked(false);
            }

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View view) {
                    //checklistContent.updateDB(db, myDbHelper);
                    if (cb.isChecked()) {
                        int pos = checklistContent.masterList.indexOf(checklistContent.showList.get(position));
                        checklistContent.masterList.get(pos).setValue(1);
                    } else {
                        int pos = checklistContent.masterList.indexOf(checklistContent.showList.get(position));
                        checklistContent.masterList.get(pos).setValue(0);
                    }
                    checklistContent.updateDB(db, myDbHelper);
                }
            });
            return convertView;
        }
}
