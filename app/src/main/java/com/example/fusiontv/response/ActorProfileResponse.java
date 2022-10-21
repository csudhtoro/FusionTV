package com.example.fusiontv.response;

import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorProfileResponse {
    @SerializedName("profiles")
    @Expose()

    private List<ActorProfile> images;

    public List<ActorProfile> getImages() {return images;}

    @Override
    public String toString() {
        return "ActorProfileResponse{" +
                "images=" + images +
                '}';
    }
}
