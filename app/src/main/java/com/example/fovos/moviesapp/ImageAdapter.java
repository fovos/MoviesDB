package com.example.fovos.moviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Fovos on 13/11/2016.
 */

public class ImageAdapter extends BaseAdapter {

    //FIELDS
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> images;

    //CONSTRUCTOR
    public ImageAdapter(Context c,ArrayList<String> img) {

        mContext = c;
        images=img;
        inflater=LayoutInflater.from(c);

        Log.e("Picasso", images.toString());
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_layout, parent, false);
        }

        Picasso.with(mContext)
                .load(images.get(position))
                .fit() // will explain later
                .into((ImageView) convertView);

            return convertView;
    }
}
