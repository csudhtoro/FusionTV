package com.example.fusiontv.fragments;

import static com.example.fusiontv.utils.MyUtilities.UpdateFavAirDates;
import static com.example.fusiontv.utils.MyUtilities.checkUserLoggedIn;
import static com.example.fusiontv.utils.MyUtilities.getCurrUserId;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusiontv.R;
import com.example.fusiontv.adapters.FiscalWeekAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.adapters.ShowAiringTodayAdapter;
import com.example.fusiontv.adapters.ShowSearchAdapter;
import com.example.fusiontv.adapters.ShowTrendingAdapter;
import com.example.fusiontv.adapters.ViewPagerAdapter;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment implements OnShowListener {

    //RecyclerView Widget
    //private RecyclerView popularRecyclerView;
    private RecyclerView trendingRecyclerView;
    private RecyclerView airingTodayRecyclerView;
    private RecyclerView fiscalWeekRecyclerView;
    FirebaseUser currUser;

    private ShowAiringTodayAdapter showAiringTodayRecyclerViewAdapter;
    private ShowTrendingAdapter showTrendingRecyclerViewAdapter;


    //ViewModel
    private ShowListViewModel showListViewModel;
    boolean isPopular = true;
    private final String PREFS_NAME = "myPrefs";

    //TabLayout
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currUser = FirebaseAuth.getInstance().getCurrentUser();

        if(checkUserLoggedIn(currUser)) {

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(getCurrUserId()).child("Favorites");
            dailyFavoritesCheck(db);
        }

        //popularRecyclerView = getView().findViewById(R.id.popular_recyclerView);
        trendingRecyclerView = getView().findViewById(R.id.trending_recyclerView);
        airingTodayRecyclerView = getView().findViewById(R.id.airing_today_recyclerView);
        fiscalWeekRecyclerView = getView().findViewById(R.id.fiscal_week_recyclerView);

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        tabLayout = getView().findViewById(R.id.tablayout);
        viewPager2 = getView().findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setSaveEnabled(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

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
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
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
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
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

    private void dailyFavoritesCheck(DatabaseReference db) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long last_save = settings.getLong("last_save", 0);
        Date lastDay = new Date(last_save);
        Date today = new Date(System.currentTimeMillis());


        if (today.compareTo(lastDay) > 0) {
            //add score
            UpdateFavAirDates(db, getCurrUserId());
            Date now = new Date(System.currentTimeMillis());
            //update preference value
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong("last_save", now.getTime()).apply();
        }
    }

}