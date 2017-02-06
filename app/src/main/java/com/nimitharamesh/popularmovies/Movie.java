package com.nimitharamesh.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class Movie implements Parcelable {

    String id;
    /* Movie attributes */
    String title;
    String poster;
    String overview;
    double rating;
    String releaseDate;

    /* Constructors */

    // Default constructor
    public Movie(String title, String overview, Double rating, String releaseDate, String poster,
     String id            ) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public Movie(Parcel  in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
        poster = in.readString();
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
        parcel.writeString(id);
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

}
