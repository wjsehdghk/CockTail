package com.mingle.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.myapplication.R;
import com.mingle.myapplication.model.Resion;

import java.util.List;

/**
 * Created by multimedia on 2016-05-29.
 */
public class MainListAdapter extends ArrayAdapter<Resion> {
    Activity activity;
    int resource;

    public MainListAdapter(Context context, int resource, List<Resion> objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null) {
            itemView = this.activity.getLayoutInflater().inflate(this.resource, null);

        }
        Resion item = getItem(position);

        if(item != null) {
            ImageView imageView = (ImageView) itemView.findViewById(R.id.mainImage);
            ImageView imageView1 = (ImageView)itemView.findViewById(R.id.resion_ic);
            TextView textView = (TextView) itemView.findViewById(R.id.resion_txt);


            imageView1.setImageResource(item.getImage1());
            imageView.setBackground(item.getImage2());
            textView.setText(item.getResion());

        }
        return itemView;
    }


}
