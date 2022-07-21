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
    public LiveData<List<TVShowModel>> getActorTVCredits() { return showRepository.getActorTVCredits(); }
    public LiveData<Actor> getActorDetail() { return showRepository.getActorDetails(); }

    //GENRES
    public LiveData<List<TVShowModel>> getShowsByActionAdventure() { return showRepository.getShowsByActionAdventure(); }
    public LiveData<List<TVShowModel>> getShowsByAnimation() { return showRepository.getShowsByAnimation(); }
    public LiveData<List<TVShowModel>> getShowsByComedy() { return showRepository.getShowsByComedy(); }
    public LiveData<List<TVShowModel>> getShowsByCrime() { return showRepository.getShowsByCrime(); }
    public LiveData<List<TVShowModel>> getShowsByDocumentary() { return showRepository.getShowsByDocumentary(); }
    public LiveData<List<TVShowModel>> getShowsByDrama() { return showRepository.getShowsByDrama(); }
    public LiveData<List<TVShowModel>> getShowsByFamily() { return showRepository.getShowsByFamily(); }
    public LiveData<List<TVShowModel>> getShowsByKids() { return showRepository.getShowsByKids(); }
    public LiveData<List<TVShowModel>> getShowsByMystery() { return showRepository.getShowsByMystery(); }
    public LiveData<List<TVShowModel>> getShowsByNews() { return showRepository.getShowsByNews(); }
    public LiveData<List<TVShowModel>> getShowsByReality() { return showRepository.getShowsByReality(); }
    public LiveData<List<TVShowModel>> getShowsBySciFiFantasy() { return showRepository.getShowsBySciFiFantasy(); }
    public LiveData<List<TVShowModel>> getShowsBySoap() { return showRepository.getShowsBySoap(); }
    public LiveData<List<TVShowModel>> getShowsByTalk() { return showRepository.getShowsByTalk(); }
    public LiveData<List<TVShowModel>> getShowsByWarPolitics() { return showRepository.getShowsByWarPolitics(); }
    public LiveData<List<TVShowModel>> getShowsByWestern() { return showRepository.getShowsByWestern(); }



    //3 - Calling method in ViewModel
    public void searchShowApi(String query, int pageNumber) { showRepository.searchShowApi(query, pageNumber); }
    public void searchShowPopular(int pageNumber) { showRepository.searchShowPopular(pageNumber); }
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

    //GENRES
    public void searchShowByActionAdventure(int id, int pageNumber) { showRepository.searchShowsByActionAdventure(id, pageNumber); }
    public void searchShowByAnimation(int id, int pageNumber) { showRepository.searchShowsByAnimation(id, pageNumber); }
    public void searchShowByComedy(int id, int pageNumber) { showRepository.searchShowsByComedy(id, pageNumber); }
    public void searchShowByCrime(int id, int pageNumber) { showRepository.searchShowsByCrime(id, pageNumber); }
    public void searchShowByDocumentary(int id, int pageNumber) { showRepository.searchShowsByDocumentary(id, pageNumber); }
    public void searchShowByDrama(int id, int pageNumber) { showRepository.searchShowsByDrama(id, pageNumber); }
    public void searchShowByFamily(int id, int pageNumber) { showRepository.searchShowsByFamily(id, pageNumber); }
    public void searchShowByKids(int id, int pageNumber) { showRepository.searchShowsByKids(id, pageNumber); }
    public void searchShowByMystery(int id, int pageNumber) { showRepository.searchShowsByMystery(id, pageNumber); }
    public void searchShowByNews(int id, int pageNumber) { showRepository.searchShowsByNews(id, pageNumber); }
    public void searchShowByReality(int id, int pageNumber) { showRepository.searchShowsByReality(id, pageNumber); }
    public void searchShowBySciFiFantasy(int id, int pageNumber) { showRepository.searchShowsBySciFiFantasy(id, pageNumber); }
    public void searchShowBySoap(int id, int pageNumber) { showRepository.searchShowsBySoap(id, pageNumber); }
    public void searchShowByTalk(int id, int pageNumber) { showRepository.searchShowsByTalk(id, pageNumber); }
    public void searchShowByWarPolitics(int id, int pageNumber) { showRepository.searchShowsByWarPolitics(id, pageNumber); }
    public void searchShowByWestern(int id, int pageNumber) { showRepository.searchShowsByWestern(id, pageNumber); }


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

    //GENRE
    /*public void searchActionAdventureNextPage() { showRepository.searchNextActionAdventurePage(); }
    public void searchAnimationNextPage() { showRepository.searchNextAnimationPage(); }
    public void searchComedyNextPage() { showRepository.searchNextComedyPage(); }
    public void searchCrimeNextPage() { showRepository.searchNextCrimePage(); }
    public void searchDocumentaryNextPage() { showRepository.searchNextDocumentaryPage(); }
    public void searchDramaNextPage() { showRepository.searchNextDramaPage(); }
    public void searchFamilyNextPage() { showRepository.searchNextFamilyPage(); }
    public void searchKidsNextPage() { showRepository.searchNextKidsPage(); }
    public void searchMysteryNextPage() { showRepository.searchNextMysteryPage(); }
    public void searchNewsNextPage() { showRepository.searchNextNewsPage(); }
    public void searchRealityNextPage() { showRepository.searchNextRealityPage(); }
    public void searchSciFiFantasyNextPage() { showRepository.searchNextSciFiFantasyPage(); }
    public void searchSoapNextPage() { showRepository.searchNextSoapPage(); }
    public void searchTalkNextPage() { showRepository.searchNextTalkPage(); }
    public void searchWarPoliticsNextPage() { showRepository.searchNextWarPoliticsPage(); }
    public void searchWesternNextPage() { showRepository.searchNextWesternPage(); }*/

}
