package com.nimitharamesh.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nimitharamesh on 4/22/16.
 */
public class ImageAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList data;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.grid_item_movie, data);
        this.context = context;
        this.data = data;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_movie, parent, false);
        }

        String url = (String) data.get(position);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.poster)
                .into((ImageView) convertView);

        return convertView;
    }
}
