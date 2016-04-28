package com.nimitharamesh.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nimitharamesh on 4/24/16.
 */
public class DetailActivity extends ActionBarActivity {

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

        Movie movie = new Movie();
        private TextView mTitle;
        private ImageView mPoster;
        private TextView mOverview;
        private TextView mRating;
        private TextView mReleaseDate;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // The detail Activity called via intent.  Inspect the intent for movie id.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);

                updateMovieDetails(movieId);
            }

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            mTitle = (TextView) rootView.findViewById(R.id.detail_title);
            mTitle.setText(movie.title);
            mOverview = (TextView) rootView.findViewById(R.id.detail_overview);
            mOverview.setText(movie.overview);
            mRating = (TextView) rootView.findViewById(R.id.detail_rating);
            mRating.setText("" + movie.rating);
            mReleaseDate = (TextView) rootView.findViewById(R.id.detail_release_date);
            mReleaseDate.setText(movie.releaseDate);
            mPoster = (ImageView) rootView.findViewById(R.id.detail_imageview);

            String posterURL = "" + TMDB_IMAGE_URL + movie.poster;

            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.drawable.poster)
                    .into(mPoster);

            return rootView;
        }

        private void updateMovieDetails(String id) {
            FetchDetailsTask detailsTask = new FetchDetailsTask();
            detailsTask.execute(id);
        }

        public class FetchDetailsTask extends AsyncTask<String, Void, Movie> {

            private final String LOG_TAG = FetchDetailsTask.class.getSimpleName();

            private Movie getDetailsFromJson(String detailsJsonString) throws JSONException {
                // Names of JSON objects that need to be extracted
                final String TMDB_POSTER_PATH = "poster_path";
                final String TMDB_ORIGINAL_TITLE = "original_title";
                final String TMDB_PLOT_SYNOPSIS = "overview";
                final String TMDB_USER_RATING = "vote_average";
                final String TMDB_RELEASE_DATE = "release_date";

                JSONObject movieResult = new JSONObject(detailsJsonString);
                movie.setPoster(movieResult.getString(TMDB_POSTER_PATH));
                movie.setTitle(movieResult.getString(TMDB_ORIGINAL_TITLE));
                movie.setOverview(movieResult.getString(TMDB_PLOT_SYNOPSIS));
                movie.setRating(movieResult.getDouble(TMDB_USER_RATING));
                movie.setReleaseDate(movieResult.getString(TMDB_RELEASE_DATE));

                return movie;
            }

            @Override
            protected Movie doInBackground(String... params) {

                // If there's no category mentioned, there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }

                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String detailsJsonStr = null;

                String movieId = params[0];

                try {
                    // Construct the URL for the TheMovieDb query
                    // Possible parameters are available at TMDB's API page, at
                    // https://www.themoviedb.org/documentation/api/discover
                    final String DETAILS_BASE_URL = "http://api.themoviedb.org/3/movie/";
                    final String APPID_PARAM = "api_key";

                    Uri buildUri = Uri.parse(DETAILS_BASE_URL).buildUpon()
                            .appendPath(movieId)
                            .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DATABASE_API_KEY)
                            .build();

                    URL url = new URL(buildUri.toString());

                    // Create the request to TheMovieDb, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    detailsJsonStr = buffer.toString();

                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in
                    // attempting to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    return getDetailsFromJson(detailsJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                // This will only happen if there was an error getting or parsing the movies.
                return null;
            }

            @Override
            protected void onPostExecute(Movie selectedMovie) {
                if(selectedMovie != null){
                    movie = selectedMovie;
                }
            }
        }
    }
}



