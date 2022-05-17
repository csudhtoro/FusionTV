package com.example.fusiontv.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.SeasonDetail;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.repositories.ShowRepository;

import java.util.List;

public class ShowListViewModel extends ViewModel {

    private ShowRepository showRepository;

    //Constructor
    public ShowListViewModel() {
        showRepository = ShowRepository.getInstance();
    }

    public LiveData<List<TVShowModel>> getShows() { return showRepository.getShows(); }
    public LiveData<List<TVShowModel>> getShowsPopular() { return showRepository.getShowsPopular(); }
    public LiveData<List<TVShowModel>> getShowsTrending() { return showRepository.getShowsTrending(); }
    public LiveData<List<TVShowModel>> getShowsAiringToday() { return showRepository.getShowsAiringToday(); }
    public LiveData<List<Cast>> getCast() { return showRepository.getShowCast(); }
    public LiveData<List<TVShowModel>> getShowsSimilar() { return showRepository.getShowsSimilar(); }
    public LiveData<List<TVShowModel>> getShowsRecommended() { return showRepository.getShowsRecommended(); }
    public LiveData<ShowDetailModel> getShowDetail() { return showRepository.getShowDetails(); }
    public LiveData<SeasonDetail> getSeasonDetail() { return showRepository.getSeasonDetails(); }
    public LiveData<List<Backdrop>> getShowImages() { return showRepository.getShowsImages(); }
    public LiveData<List<Profile>> getActorImages() { return showRepository.getActorImages(); }
    public LiveData<List<TVShowModel>> getShowsByActionAdventure() { return showRepository.getShowsByActionAdventure(); }
    public LiveData<List<TVShowModel>> getShowsByAnimation() { return showRepository.getShowsByAnimation(); }
    public LiveData<List<TVShowModel>> getActorTVCredits() { return showRepository.getActorTVCredits(); }
    public LiveData<Actor> getActorDetail() { return showRepository.getActorDetails(); }



    //3 - Calling method in ViewModel
    public void searchShowApi(String query, int pageNumber) { showRepository.searchShowApi(query, pageNumber); }
    public void searchShowPopular(int pageNumber) {
        showRepository.searchShowPopular(pageNumber);
    }
    public void searchShowTrending(int pageNumber) { showRepository.searchShowTrending(pageNumber); }
    public void searchShowAiringToday(int pageNumber) { showRepository.searchShowAiringToday(pageNumber); }
    public void searchShowDetails(int id) {showRepository.searchShowDetails(id); }
    public void searchSeasonDetails(int id, int num) {showRepository.searchSeasonDetails(id, num); }
    public void searchActorDetails(int id) {showRepository.searchActor(id); }
    public void searchShowCast(int id) { showRepository.searchCast(id); }
    public void searchShowImages(int id) { showRepository.searchShowImages(id); }
    public void searchActorImages(int id) { showRepository.searchActorImages(id); }
    public void searchActorTVCredits(int id) { showRepository.searchActorTVCredits(id); }
    public void searchShowSimilar(int id, int pageNumber) { showRepository.searchShowSimilar(id, pageNumber);}
    public void searchShowRecommended(int id, int pageNumber) { showRepository.searchShowRecommended(id, pageNumber);}
    public void searchShowByActionAdventure(int id, int pageNumber) { showRepository.searchShowsByActionAdventure(id, pageNumber); }
    public void searchShowByAnimation(int id, int pageNumber) { showRepository.searchShowsByAnimation(id, pageNumber); }


    public void searchNextPage() {
        showRepository.searchNextPage();
    }
    public void searchPopularNextPage() {
        showRepository.searchPopularNextPage();
    }
    public void searchTrendingNextPage() {
        showRepository.searchTrendingNextPage();
    }
    public void searchAiringTodayNextPage() { showRepository.searchAiringTodayNextPage(); }
    public void searchSimilarNextPage() { showRepository.searchNextSimilarPage(); }
    public void searchRecommendedNextPage() { showRepository.searchNextRecommendedPage(); }
    public void searchActionAdventureNextPage() { showRepository.searchNextActionAdventurePage(); }
    public void searchAnimationNextPage() { showRepository.searchNextAnimationPage(); }

}
