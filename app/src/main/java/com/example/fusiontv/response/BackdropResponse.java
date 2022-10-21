package com.example.fusiontv.response;

import com.example.fusiontv.models.Backdrop;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BackdropResponse {
    @SerializedName("backdrops")
    @Expose()

    private List<Backdrop> backdrops;

    public List<Backdrop> getImages() {return backdrops;}

    @Override
    public String toString() {
        return "ImageResponse{" +
                "backdrops=" + backdrops +
                '}';
    }
}
