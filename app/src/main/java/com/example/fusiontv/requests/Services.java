package com.example.fusiontv.requests;

import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//SINGLETON PATTERN FOR RETROFIT API
public class Services {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Credentials.Base_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static TVApi tvApi = retrofit.create(TVApi.class);

    public static TVApi getTvApi() {
        return tvApi;
    }

}
