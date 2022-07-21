package com.example.fusiontv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TVShowModel implements Parcelable {


    private String name;
    private String poster_path;
    private String backdrop_path;
    private int id;
    private float vote_average;
    private String overview;
    //private String firstAirDate;
    private String network;
    private List<Integer> genre_ids;


    public void setName(String name) {
        this.name = name;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setGenres(List<Integer> genres_ids) {
        this.genre_ids = genre_ids;
    }

    public TVShowModel() {
    }

    protected TVShowModel(Parcel in) {
        name = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
        network = in.readString();
    }

    public static final Creator<TVShowModel> CREATOR = new Creator<TVShowModel>() {
        @Override
        public TVShowModel createFromParcel(Parcel in) {
            return new TVShowModel(in);
        }

        @Override
        public TVShowModel[] newArray(int size) {
            return new TVShowModel[size];
        }
    };

    @Override
    public String toString() {
        return "TVShowModel{" +
                "name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", id=" + id +
                ", vote_average=" + vote_average +
                ", overview='" + overview + '\'' +
                ", network='" + network + '\'' +
                ", genre_ids=" + genre_ids +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getTv_id() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getNetwork() {
        return network;
    }

    public List<Integer> getGenres() {
        return genre_ids;
    }

    public static Creator<TVShowModel> getCREATOR() {
        return CREATOR;
    }

    public TVShowModel(String name, String poster_path, String backdrop_path, int id, float vote_average, String overview, String network, List<Integer> genre_ids) {
        this.name = name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.vote_average = vote_average;
        this.overview = overview;
        this.network = network;
        this.genre_ids = genre_ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeInt(id);
        parcel.writeFloat(vote_average);
        parcel.writeString(overview);
        parcel.writeString(network);
    }
}


