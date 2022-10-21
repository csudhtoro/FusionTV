package com.example.fusiontv.response;

import com.example.fusiontv.models.Result;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SimilarResponse {
  @SerializedName("results")
  @Expose()

    private List<TVShowModel> similars = new ArrayList<>();

  public List<TVShowModel> getSimilars() {return similars;}

    @Override
    public String toString() {
        return "SimilarResponse{" +
                "similars=" + similars +
                '}';
    }
}
