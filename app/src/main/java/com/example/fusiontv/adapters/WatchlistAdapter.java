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

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ShowDetailModel> mShows;
    private OnShowListener onShowWatchlistListener;

    public WatchlistAdapter(OnShowListener onShowListener, Context mContext, List<ShowDetailModel> mShows) {
        this.onShowWatchlistListener = onShowListener;
        this.mContext = mContext;
        this.mShows = mShows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_item,
                parent, false);

        return new WatchlistViewHolder(view, onShowWatchlistListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((WatchlistViewHolder)holder).title.setText(mShows.get(i).getName());
        //((ShowSearchViewHolder)holder).genre.setText(mShows.get(i).getGenres().get());
        //((ShowViewHolder)holder).runtime.setText("Runtime here");
        //((ShowViewHolder)holder).runtime.setText(mShows.get(i).getOriginal_language());

        //vote avg is over 10 and ratings bar is over 5, so divide by two to get correct rating
        //((ShowViewHolder)holder).ratings.setRating((mShows.get(i).getVote_average())/2);

        //Imageview - using Glide library
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500/"+mShows.get(i).getPosterPath())
                .into(((WatchlistViewHolder)holder).imageView);


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
}


