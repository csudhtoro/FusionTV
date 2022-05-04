package com.example.fusiontv.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusiontv.models.Credit;
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


    //3 - Calling method in ViewModel
    public void searchShowApi(String query, int pageNumber) { showRepository.searchShowApi(query, pageNumber); }
    public void searchShowPopular(int pageNumber) {
        showRepository.searchShowPopular(pageNumber);
    }
    public void searchShowTrending(int pageNumber) { showRepository.searchShowTrending(pageNumber); }
    public void searchShowAiringToday(int pageNumber) { showRepository.searchShowAiringToday(pageNumber); }


    public void searchNextPage() {
        showRepository.searchNextPage();
    }
    public void searchPopularNextPage() {
        showRepository.searchPopularNextPage();
    }
    public void searchTrendingNextPage() {
        showRepository.searchTrendingNextPage();
    }
    public void searchAiringTodayNextPage() {
        showRepository.searchAiringTodayNextPage();
    }

}
