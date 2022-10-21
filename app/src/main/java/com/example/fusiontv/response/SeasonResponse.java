package com.example.fusiontv.response;

import com.example.fusiontv.models.ResultRec;
import com.example.fusiontv.models.SeasonDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse {

    private SeasonDetail season;

    public SeasonDetail getSeason() {return season;}

    @Override
    public String toString() {
        return "SeasonResponse{" +
                "season=" + season +
                '}';
    }
}
