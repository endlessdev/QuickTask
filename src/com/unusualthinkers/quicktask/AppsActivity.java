package com.unusualthinkers.quicktask;
 
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class AppsActivity extends Fragment {
        List<ResolveInfo> pkgAppsList;
        MyAdapter adapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.activity_apps, container, false);
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                pkgAppsList = getActivity().getPackageManager()
                                .queryIntentActivities(mainIntent, 0);
                adapter = new MyAdapter(getActivity(), R.layout.layout_text,
                                pkgAppsList);
                ListView lv = (ListView) v.findViewById(R.id.app_list);
                final EditText as = (EditText)v.findViewById(R.id.app_search);
                as.addTextChangedListener(new TextWatcher() {
 
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count,
                                        int after) {
                                 
                               
                        }
 
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before,
                                        int count) {
                               
                               
                        }
 
                        @Override
                        public void afterTextChanged(Editable s) {
                                String text = as.getText().toString().toLowerCase(Locale.getDefault());
                                adapter.filter(text);
                                
                               
                        }
                       
                });
                lv.setAdapter(adapter);
                lv.setFastScrollEnabled(true);
                lv.setOnItemClickListener(new OnItemClickListener() {
 
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                                ResolveInfo temp = pkgAppsList.get(position);
                                Intent i = new Intent();
                                i.setClassName(temp.activityInfo.packageName,
                                                temp.activityInfo.name);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                getActivity().finish();
                        }
                });
                lv.setOnItemLongClickListener(new OnItemLongClickListener() {
 
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                               
                                final CharSequence[] items = { "애플리케이션 제거", "애플리케이션 정보" };
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                                getActivity());
                                ResolveInfo temp = pkgAppsList.get(position);
                                builder.setTitle(temp.loadLabel(getActivity().getPackageManager()));
                                builder.setIcon(temp.loadIcon(getActivity().getPackageManager()));
                                builder.setItems(items, new DialogInterface.OnClickListener() {
                                       
 
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                ResolveInfo temp = pkgAppsList.get(position);
                                                if (which == 0) {
                                                        Intent intent = new Intent(Intent.ACTION_DELETE)
                                                                        .setData(Uri.parse("package:"
                                                                                        + temp.activityInfo.packageName));
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                } else if (which == 1) {
                                                        Intent intent = new Intent(Intent.CATEGORY_DEFAULT)
                                                        .setData(Uri.parse("package:"
                                                                        + temp.activityInfo.packageName));
                                                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                }
                                        }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                return true;
                        }
                });
                return v;
        }
 
       
   
        public class MyAdapter extends ArrayAdapter<ResolveInfo>{
 
                List<ResolveInfo> child;
                public MyAdapter(Context context, int textViewResourceId,
                                 List<ResolveInfo> objects) {
                        super(context, textViewResourceId, objects);
                        child = objects;
                }
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                                LayoutInflater li = (LayoutInflater) getActivity()
                                                .getBaseContext().getSystemService(
                                                                Context.LAYOUT_INFLATER_SERVICE);
                                convertView = li.inflate(R.layout.apps_row, null);
                        }
                        ImageView ai = (ImageView) convertView.findViewById(R.id.ai);
                        TextView an = (TextView) convertView.findViewById(R.id.an);
 
                        ResolveInfo temp = child.get(position);
                        ai.setImageDrawable(temp
                                        .loadIcon(getActivity().getPackageManager()));
                        an.setText(temp.loadLabel(getActivity().getPackageManager()));
 
                        return convertView;
                }
                public void filter(String text) {
                        text = text.toLowerCase(Locale.getDefault());
                        pkgAppsList.clear();
                if (text.length() == 0) {
                        pkgAppsList.addAll(child);
                        Log.d("text.length()==0","testfilter");
                } else {
                	Log.d("161line","testfilter");
                    for (ResolveInfo ri : pkgAppsList) {
                    	Log.d("163","testfilter");
                        if (ri.loadLabel(getActivity().getPackageManager()).toString().toLowerCase(Locale.getDefault())
                                .contains(text)) {
                        	Log.d("165line","testfilter");
                                pkgAppsList.add(ri);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
       
}