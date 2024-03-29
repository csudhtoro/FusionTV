package com.example.fusiontv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.utils.SpacingRV;
import com.example.fusiontv.adapters.ComedyAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;

import java.util.List;

public class ComedyFragment extends Fragment implements OnShowListener {

    private ShowListViewModel showListViewModel;

    RecyclerView comedy_recyclerview;
    private ComedyAdapter comedyRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comedy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);
        comedy_recyclerview = (RecyclerView) getView().findViewById(R.id.comedy_recyclerview);


        showListViewModel.searchShowByComedy(35, 1);
        PutComedyDataIntoRecyclerView();
        ObserveGenreChange();
    }

    private void PutComedyDataIntoRecyclerView() {
        comedyRecyclerViewAdapter = new ComedyAdapter(this);

        SpacingRV rvDecorator = new SpacingRV(-35, -35);
        comedy_recyclerview.addItemDecoration(rvDecorator);

        comedy_recyclerview.setAdapter(comedyRecyclerViewAdapter);
        comedy_recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //RecyclerView Pagination
        //Loading next page of api response
        comedy_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                /*if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchComedyNextPage();
                }*/
            }
        });
    }
    private void ObserveGenreChange() {
        showListViewModel.getShowsByComedy().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                comedyRecyclerViewAdapter.setmShows(tvShowModels);
            }
        });
    }


    @Override
    public void onShowClick(int position) {

    }

    @Override
    public void onGenreClick(String Genre) {

    }

    @Override
    public void onShowAiringTodayClick(int position) {

    }

    @Override
    public void onShowTrendingTodayClick(int position) {

    }

    @Override
    public void onShowSearchClick(int position) {

    }

    @Override
    public void onFavoritesClick(int position) {

    }

    @Override
    public void onWatchlistClick(int position) {

    }

    @Override
    public void onSeasonClick(int position) {

    }

    @Override
    public void onShowSimilarClick(int position) {

    }

    @Override
    public void onShowRecommendedClick(int position) {

    }

    @Override
    public void onShowCastClick(int position) {

    }

    @Override
    public void onShowBackdropClick(int position) {

    }

    @Override
    public void onActorTVCreditClick(int position) {

    }

    @Override
    public void onShowActorImageClick(int position) {

    }

    @Override
    public void onShowGenreClick(int adapterPosition) {

    }

    @Override
    public void onFiscalWeekClick(int adapterPosition) {

    }

    @Override
    public void onNotificationClick(int adapterPosition) {

    }
}