package com.unusualthinkers.quicktask;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionsAdapter extends ArrayAdapter<Actions> {

	private ArrayList<Actions> items;

	public ActionsAdapter(Context c, int item, ArrayList<Actions> items) {
		super(c, item, items);
		this.items = items;
	}

	@SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.actions_row, null);
        }
		Actions custom_list_data = items.get(position);

		if (custom_list_data != null) {
			ImageView act_i = (ImageView) v.findViewById(R.id.act_i);
			TextView act_t = (TextView) v.findViewById(R.id.act_n);
			TextView act_s = (TextView) v.findViewById(R.id.act_s);
			Log.d("ActionsAdapter »£√‚","");
			act_i.setBackgroundResource(custom_list_data.getActions_img());
			act_t.setText(custom_list_data.getActions_title());
			act_s.setText(custom_list_data.getActions_summary());
			act_s.setSelected(true);
			act_s.setSingleLine(true);
			act_s.setEllipsize(TruncateAt.MARQUEE);
			act_s.setMarqueeRepeatLimit(-1);
		}
		return v;
	}
}

class Actions {
	private int actions_img;
	private String actions_title;
	private String actions_summary;

	public Actions(int _Image_ID, String _Main_Title, String _Sub_Title) {
		this.setActions_img(_Image_ID);
		this.setActions_title(_Main_Title);
		this.setActions_summary(_Sub_Title);
	}

	public int getActions_img() {
		return actions_img;
	}

	public void setActions_img(int actions_i) {
		actions_img = actions_i;
	}

	public String getActions_title() {
		return actions_title;
	}

	public void setActions_title(String main_Title) {
		actions_title = main_Title;
	}

	public String getActions_summary() {
		return actions_summary;
	}

	public void setActions_summary(String sub_Title) {
		actions_summary = sub_Title;
	}

}