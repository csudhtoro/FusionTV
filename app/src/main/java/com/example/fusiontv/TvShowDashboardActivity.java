package com.example.fusiontv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TvShowDashboardActivity extends AppCompatActivity {

    //Initialize bottom navigation bar
    BottomNavigationView bottomNavigationView;

    //Initialize all fragments
    DashboardFragment dashboardFragment = new DashboardFragment();
    FavoritesFragment favoritesFragment = new FavoritesFragment();
    ScheduleFragment scheduleFragment = new ScheduleFragment();
    SearchFragment searchFragment = new SearchFragment();
    ProfileFragment profileFragment = new ProfileFragment();



    /*//RecyclerView Widget
    private RecyclerView popularRecyclerView;
    private RecyclerView airingTodayRecyclerView;
    private ShowRecyclerView showRecyclerViewAdapter;
    private ShowAiringTodayRecyclerView showAiringTodayRecyclerViewAdapter;*/


    /*//ViewModel
    private ShowListViewModel showListViewModel;
    boolean isPopular = true;*/


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView = findViewById(R.id.bottomNavigator);

        //replace the framelayout on tvdashboard activity with the dashboard fragment layout
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, dashboardFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, dashboardFragment).commit();
                        return true;

                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, favoritesFragment).commit();
                        return true;

                    case R.id.schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, scheduleFragment).commit();
                        return true;

                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, searchFragment).commit();
                        return true;

                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrameLayout, profileFragment).commit();
                        return true;
                }
                return true;
            }
        });
    }

        /*popularRecyclerView = findViewById(R.id.popular_recyclerView);
        airingTodayRecyclerView = findViewById(R.id.airing_today_recyclerView);

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        //POPULAR SHOWS RECYCLERVIEW
        ConfigurePopularRecyclerView();
        //Calling the observers
        ObservePopularShowChange();
        //Performing Popular show query for data to show on activity
        showListViewModel.searchShowPopular(1);

        //ObserveAnyChange();
        //Performing search show query for data to show on activity
        //searchShowApi("The", 1);


        //AIRING TODAY RECYCLERVIEW
        ConfigureAiringTodayRecyclerView();
        ObserveAiringTodayShowChange();
        //Performing Airing today show query for data to show on activity
        showListViewModel.searchShowAiringToday(1);



    }


    //Observer for LiveData -- listens for any data change
    private void ObserveAnyChange() {
        showListViewModel.getShows().observe(this, new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                //Observing for any data change
                if(tvShowModels != null) {
                    for(TVShowModel showModel: tvShowModels) {
                        //Get the data in the log
                        //Log.v("Tag", "onChanged: " + showModel.getName());

                        showRecyclerViewAdapter.setmShows(tvShowModels);
                    }
                }
            }
        });
    }
    //Observer for LiveData - Popular
    private void ObservePopularShowChange() {
        showListViewModel.getShowsPopular().observe(this, new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                //Observing for any data change
                if(tvShowModels != null) {
                    for(TVShowModel showModel: tvShowModels) {
                        //Get the data in the log
                        //Log.v("Tag", "onChanged: " + showModel.getName());

                        showRecyclerViewAdapter.setmShows(tvShowModels);
                    }
                }
            }
        });
    }
    //Observer for LiveData - Airing Today
    private void ObserveAiringTodayShowChange() {
        showListViewModel.getShowsAiringToday().observe(this, new Observer<List<TVShowModel>>() {
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
    private void searchShowApi(String query, int pageNumber) {
        showListViewModel.searchShowApi(query, pageNumber);
    }

    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR POPULAR SHOWS
    private void ConfigurePopularRecyclerView() {
        showRecyclerViewAdapter = new ShowRecyclerView(this);

        popularRecyclerView.setAdapter(showRecyclerViewAdapter);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
    }

    //5 - Initializing recyclerView and adding data to it - RECYCLERVIEW FOR AIRING TODAY SHOWS
    private void ConfigureAiringTodayRecyclerView() {
        showAiringTodayRecyclerViewAdapter = new ShowAiringTodayRecyclerView(this);

        airingTodayRecyclerView.setAdapter(showAiringTodayRecyclerViewAdapter);
        airingTodayRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
        Intent intent = new Intent(TvShowDashboardActivity.this, ShowDetails.class);
        intent.putExtra("show", showRecyclerViewAdapter.getSelectedShow(position));
        startActivity(intent);
    }

    @Override
    public void onShowAiringTodayClick(int position) {
        Intent intent = new Intent(TvShowDashboardActivity.this, ShowDetails.class);
        intent.putExtra("show", showAiringTodayRecyclerViewAdapter.getSelectedShow(position));
        startActivity(intent);
    }

    @Override
    public void onGenreClick(String Genre) {

    }*/



}