package com.example.fusiontv;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.adapters.ShowAiringTodayAdapter;
import com.example.fusiontv.adapters.ShowTrendingAdapter;
import com.example.fusiontv.adapters.ViewPagerAdapter;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class DashboardFragment extends Fragment implements OnShowListener {

    //RecyclerView Widget
    //private RecyclerView popularRecyclerView;
    private RecyclerView trendingRecyclerView;
    private RecyclerView airingTodayRecyclerView;
    private ShowAiringTodayAdapter showAiringTodayRecyclerViewAdapter;
    private ShowTrendingAdapter showTrendingRecyclerViewAdapter;


    //ViewModel
    private ShowListViewModel showListViewModel;
    boolean isPopular = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //popularRecyclerView = getView().findViewById(R.id.popular_recyclerView);
        trendingRecyclerView = getView().findViewById(R.id.trending_recyclerView);
        airingTodayRecyclerView = getView().findViewById(R.id.airing_today_recyclerView);

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);


        //TRENDING SHOWS RECYCLERVIEW
        ConfigureTrendingRecyclerView();
        ObserveTrendingShowChange();
        //Performing Popular show query for data to show on activity
        showListViewModel.searchShowTrending(1);

        //AIRING TODAY RECYCLERVIEW
        ConfigureAiringTodayRecyclerView();
        ObserveAiringTodayShowChange();
        //Performing Airing today show query for data to show on activity
        showListViewModel.searchShowAiringToday(1);
    }

    //Observer for LiveData - Trending
    private void ObserveTrendingShowChange() {
        showListViewModel.getShowsTrending().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                //Observing for any data change
                if(tvShowModels != null) {
                    for(TVShowModel showModel: tvShowModels) {
                        //Get the data in the log
                        //Log.v("Tag", "onChanged: " + showModel.getName());

                        showTrendingRecyclerViewAdapter.setmShows(tvShowModels);
                    }
                }
            }
        });
    }
    //Observer for LiveData - Airing Today
    private void ObserveAiringTodayShowChange() {
        showListViewModel.getShowsAiringToday().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                //Observing for any data change
                if(tvShowModels != null) {
                    for(TVShowModel showModel: tvShowModels) {
                        //Get the data in the log
                        //Log.v("Tag", "onChanged: " + showModel.getName());

                        showAiringTodayRecyclerViewAdapter.setmShows(tvShowModels);
                    }
                }
            }
        });
    }



    //4 - Calling method in Main Activity
    /*private void searchShowApi(String query, int pageNumber) {
        showListViewModel.searchShowApi(query, pageNumber);
    }*/

    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR POPULAR SHOWS
    /*private void ConfigurePopularRecyclerView() {
        showRecyclerViewAdapter = new ShowRecyclerView(this);

        popularRecyclerView.setAdapter(showRecyclerViewAdapter);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //RecyclerView Pagination
        //Loading next page of api response
        popularRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchPopularNextPage();
                }
            }
        });
    }*/
    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR POPULAR SHOWS
    private void ConfigureTrendingRecyclerView() {
        showTrendingRecyclerViewAdapter = new ShowTrendingAdapter(this);

        trendingRecyclerView.setAdapter(showTrendingRecyclerViewAdapter);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //RecyclerView Pagination
        //Loading next page of api response
        trendingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchTrendingNextPage();
                }
            }
        });
    }

    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR AIRING TODAY SHOWS
    private void ConfigureAiringTodayRecyclerView() {
        showAiringTodayRecyclerViewAdapter = new ShowAiringTodayAdapter(this);

        airingTodayRecyclerView.setAdapter(showAiringTodayRecyclerViewAdapter);
        airingTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //RecyclerView Pagination
        //Loading next page of api response
        airingTodayRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchAiringTodayNextPage();
                }
            }
        });
    }

    @Override
    public void onShowClick(int position) {

    }

    @Override
    public void onGenreClick(String Genre) {
        //Intent intent = new Intent(getActivity(), ShowDetails.class);
        //intent.putExtra("show", showAiringTodayRecyclerViewAdapter.getSelectedShow(position));
        //startActivity(intent);
    }

    @Override
    public void onShowAiringTodayClick(int position) {

        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentFrameLayout, showDetailFragment)
                            .addToBackStack(DashboardFragment.class.getName())
                            .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", showAiringTodayRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);

    }

    @Override
    public void onShowTrendingTodayClick(int position) {

        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", showTrendingRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);
    }

    @Override
    public void onShowSearchClick(int position) {

    }

    @Override
    public void onFavoritesClick(int position) {

    }

    @Override
    public void onWatchlistClick(int adapterPosition) {

    }

    @Override
    public void onSeasonClick(int position) {

    }

}