package com.example.fusiontv.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Credit;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.requests.ShowApiClient;
import com.example.fusiontv.response.CastResponse;

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
    public LiveData<List<Cast>> getShowCast() { return showApiClient.getShowCast(); }
    public LiveData<List<TVShowModel>> getShowsSimilar() { return showApiClient.getSimilarShows(); }
    public LiveData<List<TVShowModel>> getShowsRecommended() { return showApiClient.getRecommendedShows(); }
    public LiveData<ShowDetailModel> getShowDetails() {return showApiClient.getShowDetails();}



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

    public void searchShowSimilar(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchSimilar(id, mPageNumber);
    }

    public void searchShowRecommended(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchRecommended(id, mPageNumber);
    }

    public void searchShowDetails(int id) { showApiClient.searchShowDetails(mId = id); }
    public void searchCast(int id) { showApiClient.searchShowsCast(mId = id); }

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
    public void searchNextSimilarPage() {
        searchShowSimilar(mId, mPageNumber+1);
    }
    public void searchNextRecommendedPage() { searchShowRecommended(mId, mPageNumber+1); }


}

