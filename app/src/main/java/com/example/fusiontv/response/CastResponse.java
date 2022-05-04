package com.example.fusiontv.response;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.ShowDetailModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CastResponse {
    @SerializedName("cast")
    @Expose()

    private List<Cast> casts;

    public List<Cast> getCast() {
        return casts;
    }

    @Override
    public String toString() {
        return "CastResponse{" +
                "casts=" + casts +
                '}';
    }
}
