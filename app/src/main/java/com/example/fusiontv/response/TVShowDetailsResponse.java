package com.example.fusiontv.response;
import com.example.fusiontv.models.ShowDetailModel;
import com.google.gson.annotations.Expose;


public class TVShowDetailsResponse {
    @Expose
    private ShowDetailModel show;

    public ShowDetailModel getShow() {
        return show;
    }

    @Override
    public String toString() {
        return "TVShowDetailsResponse{" +
                "show=" + show +
                '}';
    }
}
