package com.nimitharamesh.popularmovies;

import android.support.v4.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

//    private MovieAdapter movieAdapter;
//
//    private ArrayList<Movie> movieList;
//
//    Movie[] popularMovies = {
//            new Movie("Batman", R.drawable.poster, "First movie", 8.9, "December 2002"),
//            new Movie("Batman 2", R.drawable.poster, "Second movie", 8.9, "December 2004"),
//            new Movie("The Dark Knight", R.drawable.poster, "First movie", 8.9, "December 2006"),
//            new Movie("The Dark Knight Rises", R.drawable.poster, "First movie", 8.9, "December 2008"),
//            new Movie("Batman", R.drawable.poster, "First movie", 8.9, "December 2002"),
//            new Movie("Batman 2", R.drawable.poster, "Second movie", 8.9, "December 2004"),
//            new Movie("The Dark Knight", R.drawable.poster, "First movie", 8.9, "December 2006"),
//            new Movie("The Dark Knight Rises", R.drawable.poster, "First movie", 8.9, "December 2008"),
//            new Movie("Batman", R.drawable.poster, "First movie", 8.9, "December 2002"),
//            new Movie("Batman 2", R.drawable.poster, "Second movie", 8.9, "December 2004"),
//            new Movie("The Dark Knight", R.drawable.poster, "First movie", 8.9, "December 2006"),
//            new Movie("The Dark Knight Rises", R.drawable.poster, "First movie", 8.9, "December 2008")
//    };
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
//            movieList = new ArrayList<Movie>(Arrays.asList(popularMovies));
//        } else {
//            movieList = savedInstanceState.getParcelableArrayList("movies");
//        }
//    }
//
//    public MainActivityFragment() {
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("movies", movieList);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//
//        movieAdapter = new MovieAdapter(getActivity(), movieList);
//
//        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
//        gridView.setAdapter(movieAdapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        return rootView;
//    }
}
