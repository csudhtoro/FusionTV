package com.example.fusiontv.response;

import com.example.fusiontv.models.TVShowModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This is a response class for a single TV Show search request (returns a list of results)
public class TVShowSearchResponse {
    @SerializedName("results")
    @Expose
    private List<TVShowModel> shows;

    public List<TVShowModel> getShows() {
        return shows;
    }

    @Override
    public String toString() {
        return "TVShowSearchResponse{" +
                "show=" + shows +
                '}';
    }
}
