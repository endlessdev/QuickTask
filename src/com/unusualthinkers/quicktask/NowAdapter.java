package com.unusualthinkers.quicktask;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NowAdapter extends ArrayAdapter<Now> {
 
    private ArrayList<Now> items;
 
    public NowAdapter(Context nowActivity, int textViewResourceId,
            ArrayList<Now> items) {
        super(nowActivity, textViewResourceId, items);
        this.items = items; }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
             
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.now_row, null);
        }
        Now custom_list_data = items.get(position);
 
        if (custom_list_data != null) {
            TextView tv_Main = (TextView) v.findViewById(R.id.nt);
            TextView tv_Sub = (TextView) v.findViewById(R.id.ns);
            tv_Main.setText(custom_list_data.getNow_title());
            tv_Sub.setText(custom_list_data.getNow_summary());
            tv_Sub.setSelected(true);
            tv_Sub.setSingleLine(true);
    		tv_Sub.setEllipsize(TruncateAt.MARQUEE);
    		tv_Sub.setMarqueeRepeatLimit(-1);
        }
 
        return v;
    }
}
 
class Now {
    private String now_title;
    private String now_summary;
 
    public Now(String _Main_Title, String _Sub_Title) {
        this.setNow_title(_Main_Title);
        this.setNow_summary(_Sub_Title);
    }
 

 
    public String getNow_title() {
        return now_title;
    }
 
    public void setNow_title(String main_Title) {
    	now_title = main_Title;
    }
 
    public String getNow_summary() {
        return now_summary;
    }
 
    public void setNow_summary(String sub_Title) {
    	now_summary = sub_Title;
    }
     
}