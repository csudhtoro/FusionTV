package com.example.fusiontv.utils;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static com.example.fusiontv.utils.MyUtilities.checkUserLoggedIn;

import android.app.Activity;
import android.app.Application;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.models.Network;
import com.example.fusiontv.models.NextEpisodeToAir;
import com.example.fusiontv.models.ShowDetailModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.ContentResolver;
import android.widget.Toast;

public class MyUtilities {

    public static String getCurrUserId() {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if (checkUserLoggedIn(currUser)) {
            return currUser.getUid();
        }
        return null;
    }
    public static boolean checkUserLoggedIn(FirebaseUser user) {
        if(user != null) return true;
        return false;
    }
    public static Date convertDateFormat(Date inDate) {
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        return convertStringToDate(sdf.format(inDate));
    }
    public static String convertStringDateFormat(String inDate) {

        SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outSDF = new SimpleDateFormat("MM-dd-yyyy");

        String outDate = "";

        if(inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outDate;
    }
    private String convertDateLeadingZeros(String inDate) {

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
    public static String returnYear(String inDate) {
        SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outSDF = new SimpleDateFormat("(yyyy)");

        String outDate = "";

        if(inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outDate;
    }
    public static Date convertStringToDate(String strDate) {
        SimpleDateFormat sdt = new SimpleDateFormat("YYYY-MM-dd");
        Date result = null;
        try {
            result = sdt.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Date convertStringToDateV2(String strDate) {
        Date newDate = null;

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newDate = f.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }
    public static Date getTodaysDate() {

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        return today;
    }
    public static void addNotification(ShowDetailModel show, String currUserId, boolean isScheduled) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users")
                .child(currUserId).child("Notifications")
                .child(String.valueOf(show.getId()));

        db.setValue(show);

        if(isScheduled) db.child("isScheduled").setValue(true);
        else db.child("isScheduled").setValue(false);
    }
    public static void UpdateFavAirDates(DatabaseReference db, String currUserId) {
        //

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {


                    Integer showId = ds.child("id").getValue(Integer.class);
                    String name = ds.child("name").getValue(String.class);
                    String poster = ds.child("posterPath").getValue(String.class);
                    Boolean scheduled = ds.child("Scheduled").getValue(Boolean.class);
                    String nextAirDateStr = ds.child("nextEpisodeToAir").child("airDate").getValue(String.class);
                    Date today = getTodaysDate();



                    if (scheduled == null || scheduled == false) {
                        if (nextAirDateStr != null) {
                            Date nextAirDate = convertStringToDateV2(nextAirDateStr);

                            if ((!today.after(nextAirDate) && !nextAirDate.before(today)) || today.compareTo(nextAirDate) < 0) {
                                ShowDetailModel show = new ShowDetailModel();

                                show.setId(showId);
                                show.setName(name);
                                show.setPosterPath(poster);
                                show.setNextEpisodeToAir(ds.child("nextEpisodeToAir").getValue(NextEpisodeToAir.class));

                                GenericTypeIndicator<ArrayList<Network>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Network>>() {};
                                List<Network> networks = ds.child("networks").getValue(genericTypeIndicator);
                                show.setNetworks(networks);

                                addNotification(show, currUserId, false);
                            }
                            else if (getTodaysDate().after(nextAirDate) && scheduled) {

                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId)
                                        .child("Favorites")
                                        .child(String.valueOf(showId))
                                        .child("Scheduled");
                                db.setValue(false);
                            }
                        }
                    }
                    else if (scheduled == true && (nextAirDateStr == null || !convertStringToDate(nextAirDateStr).after(today))) {

                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId)
                                .child("Favorites")
                                .child(String.valueOf(showId))
                                .child("Scheduled");

                        db.setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
