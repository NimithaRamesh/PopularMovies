package com.nimitharamesh.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nimitharamesh on 4/11/16.
 */
public class Movie implements Parcelable {

    String id;
    String title;
    String posterThumbnail;
    String overview;
    double rating;
    String releaseDate;

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

    public String getPosterThumbnail() {
        return this.posterThumbnail;
    }

    public void setPosterThumbnail(String posterThumbnail) {
        this.posterThumbnail = posterThumbnail;
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



    public Movie(String title, String poster, String overview, double rating, String date ){
        this.title = title;
        this.posterThumbnail = poster;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = date;
    }

    public Movie(){
    }

    private Movie(Parcel in){
        title = in.readString();
        posterThumbnail = in.readString();
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
        parcel.writeString(posterThumbnail);
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
