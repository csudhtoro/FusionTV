package com.example.fusiontv.repositories;

import androidx.lifecycle.LiveData;

import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.SeasonDetail;
import com.example.fusiontv.models.ShowDetailModel;
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
    private int mNum;


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
    public LiveData<List<Backdrop>> getShowsImages() { return showApiClient.getShowImages(); }
    public LiveData<List<Profile>> getActorImages() { return showApiClient.getActorImages(); }
    public LiveData<List<TVShowModel>> getActorTVCredits() { return showApiClient.getActorTVCredit(); }
    public LiveData<List<TVShowModel>> getShowsByActionAdventure() { return showApiClient.getActionAdventureGenre(); }
    public LiveData<List<TVShowModel>> getShowsByAnimation() { return showApiClient.getAnimationGenre(); }
    public LiveData<ShowDetailModel> getShowDetails() {return showApiClient.getShowDetails();}
    public LiveData<SeasonDetail> getSeasonDetails() {return showApiClient.getSeasonDetails();}
    public LiveData<Actor> getActorDetails() { return showApiClient.getActorDetails(); }



    //SEARCH METHODS
    //2 - Calling the method
    public void searchShowApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        showApiClient.searchShowsApi(mQuery, mPageNumber);
    }
    public void searchShowPopular(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiPopular(mPageNumber);
    }
    public void searchShowTrending(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiTrending(mPageNumber);
    }
    public void searchShowAiringToday(int pageNumber) {
        mPageNumber = pageNumber;
        showApiClient.searchShowsApiAiringToday(mPageNumber);
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
    public void searchSeasonDetails(int id, int num) {
        mId = id;
        mNum = num;
        showApiClient.searchSeasonDetails(id, mNum);
    }
    public void searchShowsByActionAdventure(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByActionAdventure(id, mPageNumber);
    }
    public void searchShowsByAnimation(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByAnimation(id, mPageNumber);
    }


    public void searchShowDetails(int id) { showApiClient.searchShowDetails(mId = id); }
    public void searchCast(int id) { showApiClient.searchShowsCast(mId = id); }
    public void searchShowImages(int id) { showApiClient.searchImages(mId = id); }
    public void searchActorImages(int id) { showApiClient.searchActorImages(mId = id); }
    public void searchActorTVCredits(int id) { showApiClient.searchActorTVCredits(mId = id); }
    public void searchActor(int id) { showApiClient.searchActorDetails(mId = id); }

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
    public void searchNextActionAdventurePage() { searchShowsByActionAdventure(mId, mPageNumber+1); }
    public void searchNextAnimationPage() { searchShowsByAnimation(mId, mPageNumber+1); }


}

