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



}