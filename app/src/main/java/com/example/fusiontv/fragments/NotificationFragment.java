package com.example.fusiontv.fragments;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static com.example.fusiontv.utils.MyUtilities.checkUserLoggedIn;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.adapters.NotifListAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Network;
import com.example.fusiontv.models.NextEpisodeToAir;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.utils.SpacingRV;
import com.example.fusiontv.utils.SpacingRVVertical;
import com.example.fusiontv.utils.SwipeToDeleteCallBack;
import com.example.fusiontv.viewmodels.ShowListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NotificationFragment extends Fragment implements OnShowListener {


    FirebaseUser currUser;
    DatabaseReference notificationList, notif;
    String currUserId;
    ArrayList<ShowDetailModel> notifList;
    private NotifListAdapter notifListRecyclerViewAdapter;
    private RecyclerView notificationsRecyclerView;

    private Calendar calendar;
    String date;
    private TextView todaysDate;

    private ShowListViewModel showListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        GrabNotifsFromDb();

        calendar = Calendar.getInstance();
        Format f = new SimpleDateFormat("MM-dd-yyyy");
        date = f.format(calendar.getTime());

        todaysDate = getView().findViewById(R.id.todays_date);
        todaysDate.setText(date);

        notificationsRecyclerView = getView().findViewById(R.id.notifications_recyclerview);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);
    }


    private void GrabNotifsFromDb() {
        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();
            notificationList = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Notifications");
            notificationList.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClearAll();
                    if(snapshot.exists()) {
                        for(DataSnapshot ds : snapshot.getChildren()) {

                            ShowDetailModel showDetailModel = new ShowDetailModel();

                            showDetailModel.setId(ds.child("id").getValue(Integer.class));
                            showDetailModel.setName(ds.child("name").getValue(String.class));
                            showDetailModel.setPosterPath(ds.child("posterPath").getValue(String.class));
                            showDetailModel.setNextEpisodeToAir(ds.child("nextEpisodeToAir").getValue(NextEpisodeToAir.class));

                            GenericTypeIndicator<ArrayList<Network>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Network>>() {};
                            List<Network> networks = ds.child("networks").getValue(genericTypeIndicator);
                            showDetailModel.setNetworks(networks);

                            notifList.add(showDetailModel);
                        }
                    }
                    notifListRecyclerViewAdapter = new NotifListAdapter(NotificationFragment.this, getContext(), notifList);
                    SpacingRVVertical rvDecorator = new SpacingRVVertical(-35, -35);
                    notificationsRecyclerView.addItemDecoration(rvDecorator);
                    notificationsRecyclerView.setAdapter(notifListRecyclerViewAdapter);
                    notifListRecyclerViewAdapter.notifyDataSetChanged();

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(notifListRecyclerViewAdapter));
                    itemTouchHelper.attachToRecyclerView(notificationsRecyclerView);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void ClearAll() {
        if(notifList != null) {
            notifList.clear();

            if(notifListRecyclerViewAdapter != null) {
                notifListRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        notifList = new ArrayList<>();
    }
    private void addEventToCalendarFromNotifFragment(ShowDetailModel show, int position) {

        if(show.getNextEpisodeToAir() != null) {
            String airDate = show.getNextEpisodeToAir().getAirDate();

            String[] dateDigits = airDate.split("-");


            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Integer.valueOf(dateDigits[0]) , Integer.valueOf(dateDigits[1]) - 1, Integer.valueOf(dateDigits[2]), 9, 00);
            Calendar endTime = Calendar.getInstance();
            endTime.set(Integer.valueOf(dateDigits[0]), Integer.valueOf(dateDigits[1]) - 1, Integer.valueOf(dateDigits[2]), 22, 00);

            /*Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, show.getName() + " airing today on " + show.getNetworks().get(0).getName())
                    .putExtra(CalendarContract.Events.DESCRIPTION, show.getName() + " is airing today on at 8:30 on " + show.getNetworks().get(0).getName())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, show.getNetworks().get(0).getName())
                    .putExtra(Intent.EXTRA_EMAIL, "csudhtoro@hotmail.com");
            startActivity(intent);*/



            final int calID = 1;
            if(checkPermission(calID, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)) {
                ContentResolver cr = getActivity().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                values.put(CalendarContract.Events.TITLE, show.getName() + " airing today on " + show.getNetworks().get(0).getName());
                values.put(CalendarContract.Events.DESCRIPTION, show.getName() + " is airing today on at 8:30 on " + show.getNetworks().get(0).getName());
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_LOCATION, show.getNetworks().get(0).getName());
                values.put(CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Calendars.CALENDAR_TIME_ZONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    cr.insert(CalendarContract.Events.CONTENT_URI, values);
                }
            }


            DatabaseReference fav = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currUserId).child("Favorites")
                    .child(String.valueOf(show.getId()))
                    .child("Scheduled");
            fav.setValue(true);

            //Delete Notification entry from db
            notif = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currUserId).child("Notifications")
                    .child(String.valueOf(show.getId()));

            notif.removeValue();

            notifListRecyclerViewAdapter.remove(position);
            //notifListRecyclerViewAdapter.notifyDataSetChanged();
            notifListRecyclerViewAdapter.notifyItemRemoved(position);

        }
        else Toast.makeText(getContext(), "there is no future air date available", Toast.LENGTH_SHORT).show();
    }

    public boolean checkPermission(int callBackId, String... permissionsId) {
        boolean permissions = true;
        for(String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(getActivity(), p) == PERMISSION_GRANTED;
        }
        if(!permissions) {
            ActivityCompat.requestPermissions(getActivity(), permissionsId, callBackId);
            return false;
        }
        return true;
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
        String name = notifListRecyclerViewAdapter.getSelectedShow(adapterPosition).getName();

        addEventToCalendarFromNotifFragment(notifListRecyclerViewAdapter.getSelectedShow(adapterPosition), adapterPosition);
        Toast.makeText(getContext(),  name+" has been added to calendar!", Toast.LENGTH_SHORT).show();
    }
}