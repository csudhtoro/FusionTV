package com.example.fusiontv.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Credit;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.requests.ShowApiClient;

import java.util.List;

public class ShowRepository {
    //This class is show repository

    private static ShowRepository instance;
    private ShowApiClient showApiClient;
    private String mQuery;
    private int mPageNumber;
    private int mId;


    //Singleton method
    public static ShowRepository getInstance() {
        if(instance == null) {
            instance = new ShowRepository();
        }
        return  instance;
    }

    private ShowRepository() {
        showApiClient = ShowApiClient.getInstance();
    }


    //GETTERS
    public LiveData<List<TVShowModel>> getShows() {return showApiClient.getShows();}
    public LiveData<List<TVShowModel>> getShowsPopular() {return showApiClient.getShowsPopular();}
    public LiveData<List<TVShowModel>> getShowsTrending() {return showApiClient.getShowsTrending();}
    public LiveData<List<TVShowModel>> getShowsAiringToday() {return showApiClient.getShowsAiringToday();}


    //SEARCH METHODS
    //2 - Calling the method
    public void searchShowApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        showApiClient.searchShowsApi(query, pageNumber);
    }

    public void searchShowPopular(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiPopular(pageNumber);
    }

    public void searchShowTrending(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiTrending(pageNumber);
    }

    public void searchShowAiringToday(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiAiringToday(pageNumber);
    }



    public void searchNextPage() {
        searchShowApi(mQuery, mPageNumber+1);
    }
    public void searchPopularNextPage() {
        searchShowPopular(mPageNumber+1);
    }
    public void searchTrendingNextPage() {
        searchShowTrending(mPageNumber+1);
    }
    public void searchAiringTodayNextPage() { searchShowAiringToday(mPageNumber+1); }


}

