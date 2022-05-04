package com.example.fusiontv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.adapters.ShowSearchAdapter;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;
import androidx.appcompat.widget.SearchView;


import java.util.List;

public class SearchFragment extends Fragment implements OnShowListener {

    private ShowListViewModel showListViewModel;
    private ShowSearchAdapter showSearchRecyclerViewAdapter;
    private RecyclerView searchRecyclerView;
    private ImageView searchBackArrow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchRecyclerView = getView().findViewById(R.id.search_results_recyclerview);
        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        /*searchBackArrow = getView().findViewById(R.id.search_back_arrow);
        searchBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        //Performing Airing today show query for data to show on activity
        SetupSearchView();
        ConfigureSearchRecyclerView();
        ObserveAnyChange();

    }

    private void SetupSearchView() {
        final SearchView searchView = getView().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                showListViewModel.searchShowApi(
                        //The user query from the searchView
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    //Observer for LiveData -- listens for any data change
    private void ObserveAnyChange() {
        showListViewModel.getShows().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                //Observing for any data change
                if(tvShowModels != null) {
                    for(TVShowModel showModel: tvShowModels) {
                        //Get the data in the log
                        //Log.v("Tag", "onChanged: " + showModel.getName());
                        showSearchRecyclerViewAdapter.setmShows(tvShowModels);
                    }
                }
            }
        });
    }

    //private void searchShowApi(String query, int pageNumber) {
        //showListViewModel.searchShowApi(query, pageNumber);
    //}

    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR AIRING TODAY SHOWS
    private void ConfigureSearchRecyclerView() {
        showSearchRecyclerViewAdapter = new ShowSearchAdapter(this);

        searchRecyclerView.setAdapter(showSearchRecyclerViewAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //RecyclerView Pagination
        //Loading next page of api response
        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchNextPage();
                }
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
        //Intent intent = new Intent(getActivity(), ShowDetails.class);
        //intent.putExtra("show", showSearchRecyclerViewAdapter.getSelectedShow(position));
        //startActivity(intent);

        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", showSearchRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);
    }

    @Override
    public void onFavoritesClick(int position) {

    }
}