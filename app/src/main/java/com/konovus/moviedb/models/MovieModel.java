package com.konovus.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    private String title;
    private String poster_path;
    private String release_date;
    private String overview;
    private int id;
    private float vote_average;
    private int runtime;

    public MovieModel(String title, String poster_path, String release_date, String overview, int movie_id
            , float vote_average, int runtime) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
        this.id = movie_id;
        this.vote_average = vote_average;
        this.runtime = runtime;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        overview = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        runtime = in.readInt();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeInt(id);
        dest.writeFloat(vote_average);
    }
}
