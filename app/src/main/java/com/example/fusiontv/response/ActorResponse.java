package com.example.fusiontv.response;

import com.example.fusiontv.models.Actor;
import com.google.gson.annotations.Expose;

public class ActorResponse {
    @Expose
    private Actor actor;

    public Actor getActor() {return actor;}

    @Override
    public String toString() {
        return "ActorResponse{" +
                "actor=" + actor +
                '}';
    }
}
