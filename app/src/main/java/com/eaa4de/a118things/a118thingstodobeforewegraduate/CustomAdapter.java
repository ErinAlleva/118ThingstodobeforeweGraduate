package com.eaa4de.a118things.a118thingstodobeforewegraduate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import static com.eaa4de.a118things.a118thingstodobeforewegraduate.ChecklistContent.checklistModelItems;

/**
 * Created by ErinA on 5/24/2017.
 */

public class CustomAdapter extends ArrayAdapter{//&lt;ChecklistModel&gt;{
        ChecklistContent checklistContent = new ChecklistContent();
        public static ArrayList<Boolean> itemChecked1 = null;
        //public static ArrayList<ChecklistModel> checkedItems= new ArrayList<>();
        //public static ArrayList<ChecklistModel> uncheckedItems= new ArrayList<>();
        Context context;
        public CustomAdapter(Context context, ChecklistModel[] resource){
        super(context,R.layout.list_view_row,resource);
        this.context=context;
        checklistContent.checklistModelItems=resource;
            itemChecked1 = new ArrayList<Boolean>();
           for (int i = 0; i < checklistContent.checklistModelItems.length; i++) {
                itemChecked1.add(i,checklistContent.checklistModelItems[i].getValue()==0);
            }

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_row, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.textView1);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
            name.setText(checklistModelItems[position].getName());
            if(checklistModelItems[position].getValue()==1) {
                cb.setChecked(true);
               // checkedItems.add(checklistContent.checklistModelItems[position]);
            } else {
                cb.setChecked(false);
                //uncheckedItems.add(checklistContent.checklistModelItems[position]);
            }

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View view) {
                    if (cb.isChecked())
                        checklistModelItems[position].setValue(1);
                    else
                        checklistModelItems[position].setValue(0);
                }
            });
            return convertView;
        }
}
