package com.nimitharamesh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nimitharamesh on 4/24/16.
 */
public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection simplifiable if statement
        if( id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        protected final String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

        private final String LOG_TAG = DetailFragment.class.getSimpleName();

        Movie myMovie;


        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // The detail Activity called via intent.  Inspect the intent for movie id.
            Intent intent = getActivity().getIntent();

            if (intent != null && intent.hasExtra("Movie")) {
                Log.v(LOG_TAG, "Inside if:");
                myMovie = intent.getParcelableExtra("Movie");

            }

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            TextView mTitle = (TextView) rootView.findViewById(R.id.detail_title);
            mTitle.setText(myMovie.title);
            TextView mOverview = (TextView) rootView.findViewById(R.id.detail_overview);
            mOverview.setText(myMovie.overview);
            TextView mRating = (TextView) rootView.findViewById(R.id.detail_rating);
            mRating.setText("" + myMovie.rating);
            TextView mReleaseDate = (TextView) rootView.findViewById(R.id.detail_release_date);
            mReleaseDate.setText(myMovie.releaseDate);
            ImageView mPoster = (ImageView) rootView.findViewById(R.id.detail_imageview);

            String posterURL = "" + TMDB_IMAGE_URL + myMovie.poster;

            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.drawable.poster)
                    .into(mPoster);

            return rootView;
        }
    }
}



