package com.example.fusiontv.response;

import com.example.fusiontv.models.Result;
import com.example.fusiontv.models.ResultRec;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendationResponse {
    @SerializedName("results")
    @Expose()

    private List<ResultRec> recommendations;

    public List<ResultRec> getRecommendations() {return recommendations;}

    @Override
    public String toString() {
        return "RecommendationResponse{" +
                "recommendations=" + recommendations +
                '}';
    }
}
