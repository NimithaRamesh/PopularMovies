package com.nimitharamesh.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class Movie implements Parcelable {

    String title;
    int posterThumbnail;
    String overview;
    double rating;
    String releaseDate;

    public Movie(String title, int poster, String overview, double rating, String date ){
        this.title = title;
        this.posterThumbnail = poster;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = date;
    }

    private Movie(Parcel in){
        title = in.readString();
        posterThumbnail = in.readInt();
        overview = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return title + posterThumbnail + overview + " " + rating + " " + releaseDate;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(posterThumbnail);
        parcel.writeString(overview);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);

    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
