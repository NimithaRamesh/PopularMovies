package com.nimitharamesh.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class MovieFragment extends Fragment {

    private final String LOG_TAG = MovieFragment.class.getSimpleName();

    private final String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private ArrayAdapter<String> mMovieAdapter;

    private GridView gridView;
    private String[] movieIds;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Handles menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The ArrayAdapter will take data from a source and
        // use it to populate the GridView it's attached to.

        mMovieAdapter = new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.grid_item_movie, // The name of the layout ID.
                        R.id.grid_item_movie_imageview, // The ID of the textview to populate.
                        new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the GridView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String movieId = movieIds[position];
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void updateMovies() {
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = prefs.getString(getString(R.string.pref_sort_key), "");
        movieTask.execute(sortBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private ArrayList<Movie> getMovieDataFromJson(String moviesJsonStr) throws JSONException {

            // Names of JSON objects that need to be extracted
            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_ID = "id";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

            ArrayList<Movie> movieCollection = new ArrayList<>();

            for(int i=0; i<moviesArray.length(); i++){

                Movie movie = new Movie();

                JSONObject movieObject = moviesArray.getJSONObject(i);
                movie.setId(movieObject.getString(TMDB_ID));
                String imageURL = "" + TMDB_IMAGE_URL + movieObject.getString(TMDB_POSTER_PATH);
                movie.setPoster(imageURL);

                movieCollection.add(movie);

            }

            return movieCollection;
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {


            // If there's no category mentioned, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String sortOrder = params[0];

            try {
                // Construct the URL for the TheMovieDb query
                // Possible parameters are available at TMDB's API page, at
                // https://www.themoviedb.org/documentation/api/discover
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";

                Uri buildUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendPath(sortOrder)
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
                movieJsonStr = buffer.toString();

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
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the movies.
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            String[] urlArray = new String[result.size()];
            movieIds = new String[result.size()];
            if(result != null){
                for(int i=0; i<result.size(); i++){
                    urlArray[i] = ("" + TMDB_IMAGE_URL + result.get(i).getPoster());
                    movieIds[i] = result.get(i).getId();
                }
                ArrayList<String> imageList = new ArrayList<String>(Arrays.asList(urlArray));

                ImageAdapter adapter = new ImageAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);

            }
        }
    }
}
