package com.example.fusiontv.response;

import com.example.fusiontv.models.TVShowModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This is a response class for getting popular tv shows (returns a list of results)
public class TVShowPopularResponse {
    @SerializedName("total_results")
    @Expose()
    private int total_count;


    @SerializedName("results")
    @Expose()
    private List<TVShowModel> shows;


    public int getTotal_count() {
        return total_count;
    }

    public List<TVShowModel> getShows() {
        return shows;
    }

    @Override
    public String toString() {
        return "TVShowPopularResponse{" +
                "total_count=" + total_count +
                ", shows=" + shows +
                '}';
    }
}
