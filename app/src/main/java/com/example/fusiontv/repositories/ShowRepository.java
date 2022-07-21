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
    private String mStartDate;
    private String mEndDate;
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
    public LiveData<ShowDetailModel> getShowDetails() {return showApiClient.getShowDetails();}
    public LiveData<SeasonDetail> getSeasonDetails() {return showApiClient.getSeasonDetails();}
    public LiveData<Actor> getActorDetails() { return showApiClient.getActorDetails(); }

    //GENRES
    public LiveData<List<TVShowModel>> getShowsByActionAdventure() { return showApiClient.getActionAdventureGenre(); }
    public LiveData<List<TVShowModel>> getShowsByAnimation() { return showApiClient.getAnimationGenre(); }
    public LiveData<List<TVShowModel>> getShowsByComedy() { return showApiClient.getComedyGenre(); }
    public LiveData<List<TVShowModel>> getShowsByCrime() { return showApiClient.getCrimeGenre(); }
    public LiveData<List<TVShowModel>> getShowsByDocumentary() { return showApiClient.getDocumentaryGenre(); }
    public LiveData<List<TVShowModel>> getShowsByDrama() { return showApiClient.getDramaGenre(); }
    public LiveData<List<TVShowModel>> getShowsByFamily() { return showApiClient.getFamilyGenre(); }
    public LiveData<List<TVShowModel>> getShowsByKids() { return showApiClient.getKidsGenre(); }
    public LiveData<List<TVShowModel>> getShowsByMystery() { return showApiClient.getMysteryGenre(); }
    public LiveData<List<TVShowModel>> getShowsByNews() { return showApiClient.getNewsGenre(); }
    public LiveData<List<TVShowModel>> getShowsByReality() { return showApiClient.getRealityGenre(); }
    public LiveData<List<TVShowModel>> getShowsBySciFiFantasy() { return showApiClient.getSciFiFantasyGenre(); }
    public LiveData<List<TVShowModel>> getShowsBySoap() { return showApiClient.getSoapGenre(); }
    public LiveData<List<TVShowModel>> getShowsByTalk() { return showApiClient.getTalkGenre(); }
    public LiveData<List<TVShowModel>> getShowsByWarPolitics() { return showApiClient.getWarPoliticsGenre(); }
    public LiveData<List<TVShowModel>> getShowsByWestern() { return showApiClient.getWesternGenre(); }



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
        showApiClient.searchSimilar(mId, mPageNumber);
    }
    public void searchShowRecommended(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchRecommended(mId, mPageNumber);
    }
    public void searchSeasonDetails(int id, int num) {
        mId = id;
        mNum = num;
        showApiClient.searchSeasonDetails(mId, mNum);
    }

    //GENRES
    public void searchShowsByActionAdventure(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByActionAdventure(mId, mPageNumber);
    }
    public void searchShowsByAnimation(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByAnimation(mId, mPageNumber);
    }
    public void searchShowsByComedy(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByComedy(mId, mPageNumber);
    }
    public void searchShowsByCrime(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByCrime(mId, mPageNumber);
    }
    public void searchShowsByDocumentary(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByDocumentary(mId, mPageNumber);
    }
    public void searchShowsByDrama(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByDrama(mId, mPageNumber);
    }
    public void searchShowsByFamily(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByFamily(mId, mPageNumber);
    }
    public void searchShowsByKids(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByKids(mId, mPageNumber);
    }
    public void searchShowsByMystery(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByMystery(mId, mPageNumber);
    }
    public void searchShowsByNews(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByNews(mId, mPageNumber);
    }
    public void searchShowsByReality(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByReality(mId, mPageNumber);
    }
    public void searchShowsBySciFiFantasy(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsBySciFiFantasy(mId, mPageNumber);
    }
    public void searchShowsBySoap(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsBySoap(mId, mPageNumber);
    }
    public void searchShowsByTalk(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByTalk(mId, mPageNumber);
    }
    public void searchShowsByWarPolitics(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByWarPolitics(mId, mPageNumber);
    }
    public void searchShowsByWestern(int id, int pageNumber) {
        mId = id;
        mPageNumber = pageNumber;
        showApiClient.searchShowsByWestern(mId, mPageNumber);
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

    //GENRES
    /*public void searchNextActionAdventurePage() { searchShowsByActionAdventure(mId, mPageNumber+1); }
    public void searchNextAnimationPage() { searchShowsByAnimation(mId, mPageNumber+1); }
    public void searchNextComedyPage() { searchShowsByComedy(mId, mPageNumber+1); }
    public void searchNextCrimePage() { searchShowsByCrime(mId, mPageNumber+1); }
    public void searchNextDocumentaryPage() { searchShowsByDocumentary(mId, mPageNumber+1); }
    public void searchNextDramaPage() { searchShowsByDrama(mId, mPageNumber+1); }
    public void searchNextFamilyPage() { searchShowsByFamily(mId, mPageNumber+1); }
    public void searchNextKidsPage() { searchShowsByKids(mId, mPageNumber+1); }
    public void searchNextMysteryPage() { searchShowsByMystery(mId, mPageNumber+1); }
    public void searchNextNewsPage() { searchShowsByNews(mId, mPageNumber+1); }
    public void searchNextRealityPage() { searchShowsByReality(mId, mPageNumber+1); }
    public void searchNextSciFiFantasyPage() { searchShowsBySciFiFantasy(mId, mPageNumber+1); }
    public void searchNextSoapPage() { searchShowsBySoap(mId, mPageNumber+1); }
    public void searchNextTalkPage() { searchShowsByTalk(mId, mPageNumber+1); }
    public void searchNextWarPoliticsPage() { searchShowsByWarPolitics(mId, mPageNumber+1); }
    public void searchNextWesternPage() { searchShowsByWestern(mId, mPageNumber+1); }*/
}

