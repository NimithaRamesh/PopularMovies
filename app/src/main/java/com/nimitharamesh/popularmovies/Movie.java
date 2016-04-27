package com.nimitharamesh.popularmovies;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class Movie {

    /* Movie attributes */
    String id;
    String title;
    String poster;
    String overview;
    double rating;
    String releaseDate;

    /* Constructors */

    // Default constructor
    public Movie(){
    }

    // Constructor for initial screen
    public Movie(String poster, String id){
        this.poster = poster;
        this.id = id;
    }

    // Constructor for Details screen
    public Movie(Movie movie) {
        this.title = movie.title;
        this.poster = movie.poster;
        this.overview = movie.overview;
        this.rating = movie.rating;
        this.releaseDate = movie.releaseDate;
    }

    /* Getters and Setters */
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
