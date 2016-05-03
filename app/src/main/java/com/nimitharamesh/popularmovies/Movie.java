package com.nimitharamesh.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class Movie implements Parcelable {

    /* Movie attributes */
    String title;
    String poster;
    String overview;
    double rating;
    String releaseDate;

    /* Constructors */

    // Default constructor
    public Movie(String title, String overview, Double rating, String releaseDate, String poster) {
        this.title = title;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public Movie(Parcel  in) {
        title = in.readString();
        overview = in.readString();;
        rating = in.readDouble();;
        releaseDate = in.readString();;
        poster = in.readString();;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return title + "--" + overview + "--" + rating + "--" + releaseDate + "--" +poster;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeString(poster);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

//    // Constructor for initial screen
//    public Movie(String poster, String id){
//        this.poster = poster;
//        this.id = id;
//    }
//
//    // Constructor for Details screen
//    public Movie(Movie movie) {
//        this.title = movie.title;
//        this.poster = movie.poster;
//        this.overview = movie.overview;
//        this.rating = movie.rating;
//        this.releaseDate = movie.releaseDate;
//    }
//
//    /* Getters and Setters */
//
//    public String getId() {
//        return this.id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return this.title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getPoster() {
//        return this.poster;
//    }
//
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
//
//    public String getOverview() {
//        return this.overview;
//    }
//
//    public void setOverview(String overview) {
//        this.overview = overview;
//    }
//
//    public double getRating() {
//        return this.rating;
//    }
//
//    public void setRating(double rating) {
//        this.rating = rating;
//    }
//
//    public String getReleaseDate() {
//        return this.releaseDate;
//    }
//
//    public void setReleaseDate(String releaseDate) {
//        this.releaseDate = releaseDate;
//    }

}
