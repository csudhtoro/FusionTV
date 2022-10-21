package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.ShowDetailModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotifListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ShowDetailModel> mShows;
    private OnShowListener onShowNotifListListener;

    public NotifListAdapter(OnShowListener onShowListener, Context mContext, List<ShowDetailModel> mShows) {
        this.onShowNotifListListener = onShowListener;
        this.mContext = mContext;
        this.mShows = mShows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_add,
                parent, false);

        return new NotifListViewHolder(view, onShowNotifListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((NotifListViewHolder)holder).title.setText(mShows.get(i).getName());
        ((NotifListViewHolder)holder).nextAirDate.setText(convertDate(mShows.get(i).getNextEpisodeToAir().getAirDate()));
        //((ShowSearchViewHolder)holder).genre.setText(mShows.get(i).getGenres().get());
        //((ShowViewHolder)holder).runtime.setText("Runtime here");
        //((ShowViewHolder)holder).runtime.setText(mShows.get(i).getOriginal_language());

        //vote avg is over 10 and ratings bar is over 5, so divide by two to get correct rating
        //((ShowViewHolder)holder).ratings.setRating((mShows.get(i).getVote_average())/2);

        //Imageview - using Glide library
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500/"+mShows.get(i).getPosterPath())
                .into(((NotifListViewHolder)holder).imageView);

    }

    @Override
    public int getItemCount() {
        if(mShows != null) return mShows.size();
        return 0;
    }

    public void setmShows(List<ShowDetailModel> mShows) {
        this.mShows = mShows;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public ShowDetailModel getSelectedShow(int position) {
        if(mShows != null) {
            if(mShows.size() > 0) {
                return mShows.get(position);
            }
        }
        return null;
    }

    public void remove(int position) {
        mShows.remove(position);
        notifyItemRemoved(position);
    }

    private String convertDate(String inDate) {

        SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outSDF = new SimpleDateFormat("mm-dd-yyyy");

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
}


