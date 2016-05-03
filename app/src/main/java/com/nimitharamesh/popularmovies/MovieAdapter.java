package com.nimitharamesh.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nimitharamesh on 4/30/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context context;
    private ArrayList data;
    private LayoutInflater inflater;


    public MovieAdapter(Activity context, ArrayList<Movie> movies) {
        super(context, R.layout.grid_item_movie, movies);
        this.context = context;
        this.data = movies;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie,
                    parent, false);
        }

        String url = (String) movie.poster;
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.poster)
                .into((ImageView) convertView);

        return convertView;
    }
}
