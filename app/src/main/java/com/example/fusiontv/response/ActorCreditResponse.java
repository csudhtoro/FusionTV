package com.example.fusiontv.response;

import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.TVCredit;
import com.example.fusiontv.models.TVCredits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorCreditResponse {
    @SerializedName("cast")
    @Expose()

    private List<TVCredits> credits;

    public List<TVCredits> getCredits() {return credits;}

    @Override
    public String toString() {
        return "ActorCreditResponse{" +
                "credits=" + credits +
                '}';
    }
}
