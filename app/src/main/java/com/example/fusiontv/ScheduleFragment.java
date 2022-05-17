package com.example.fusiontv;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.adapters.WatchlistAdapter;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleFragment extends Fragment implements OnShowListener {


    FirebaseUser currUser;
    DatabaseReference currWatchList;
    String currUserId;
    ArrayList<ShowDetailModel> watchList;
    private WatchlistAdapter watchlistRecyclerViewAdapter;
    private CalendarView calendarView;
    private RecyclerView scheduleRecyclerView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //GETTING THE CURRENT DATE AND CONVERT TO STRING
        Format f = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = f.format(new Date());


        currUser = FirebaseAuth.getInstance().getCurrentUser();
        WatchListDateCheck(strDate);

        calendarView = getView().findViewById(R.id.schedule_calendar);
        scheduleRecyclerView = getView().findViewById(R.id.schedule_recyclerview);

        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date = (i1 + 1)+"-"+i2+"-"+i;
                String newDate = convertDate(date);

                WatchListDateCheck(newDate);
            }
        });
    }

    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(user != null) return true;
        return false;
    }

    private void WatchListDateCheck(String date) {
        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();
            currWatchList = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("WatchList").child(date);
            currWatchList.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClearAll();
                    if(snapshot.exists()) {
                        for(DataSnapshot ds : snapshot.getChildren()) {

                            ShowDetailModel showDetailModel = new ShowDetailModel();
                            showDetailModel.setId(ds.child("id").getValue(Integer.class));
                            showDetailModel.setName(ds.child("name").getValue(String.class));
                            showDetailModel.setPosterPath(ds.child("posterPath").getValue(String.class));
                            showDetailModel.setBackdropPath(ds.child("backdropPath").getValue(String.class));

                            watchList.add(showDetailModel);
                        }
                    }
                    watchlistRecyclerViewAdapter = new WatchlistAdapter(ScheduleFragment.this, getContext(), watchList);
                    scheduleRecyclerView.setAdapter(watchlistRecyclerViewAdapter);
                    watchlistRecyclerViewAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void ClearAll() {
        if(watchList != null) {
            watchList.clear();

            if(watchlistRecyclerViewAdapter != null) {
                watchlistRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        watchList = new ArrayList<>();
    }

    //ADD LEADING ZERO TO ALL DATE VALUES < 10
    private String convertDate(String inDate) {

        String outDate = "";
        String[] dateArr = new String[inDate.length()];

        for(int i = 0; i < inDate.length(); i++) {
            dateArr = inDate.split("-");
        }

        for(int i = 0; i < dateArr.length; i++) {
            if(dateArr[i].length() < 2) dateArr[i] = "0"+dateArr[i];
            if(i == dateArr.length - 1) outDate  += dateArr[i];
            else outDate  += dateArr[i]+"-";
        }
        return outDate;
    }

    @Override
    public void onShowClick(int position){}
    @Override
    public void onGenreClick(String Genre){}
    @Override
    public void onShowAiringTodayClick(int position){}
    @Override
    public void onShowTrendingTodayClick(int position){}
    @Override
    public void onShowSearchClick(int position){}
    @Override
    public void onFavoritesClick(int position){}

    @Override
    public void onWatchlistClick(int position) {
        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(ScheduleFragment.class.getName())
                .commit();

        TVShowModel tvShowModel = new TVShowModel();
        tvShowModel.setId(watchlistRecyclerViewAdapter.getSelectedShow(position).getId());
        tvShowModel.setPoster_path(watchlistRecyclerViewAdapter.getSelectedShow(position).getPosterPath());
        tvShowModel.setBackdrop_path(watchlistRecyclerViewAdapter.getSelectedShow(position).getBackdropPath());

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", tvShowModel);
        showDetailFragment.setArguments(bundle);
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
}