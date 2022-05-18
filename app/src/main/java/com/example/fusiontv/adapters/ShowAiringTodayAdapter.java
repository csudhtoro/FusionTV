package com.example.fusiontv.adapters;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class ShowAiringTodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TVShowModel> mShows;
    private List<ShowDetailModel> mShow;
    private OnShowListener onShowAiringTodayListener;

    public ShowAiringTodayAdapter(OnShowListener onShowListener) {
        this.onShowAiringTodayListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.airing_today_list_item,
                parent, false);

        return new ShowAiringTodayViewHolder(view, onShowAiringTodayListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((ShowAiringTodayViewHolder)holder).title.setText(mShows.get(i).getName());
        ((ShowAiringTodayViewHolder)holder).genre.setText("Genre");


        //vote avg is over 10 and ratings bar is over 5, so divide by two to get correct rating
        //((ShowAiringTodayViewHolder)holder).ratings.setRating((mShows.get(i).getVote_average())/2);

        //Imageview - using Glide library
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+mShows.get(i).getBackdrop_path()).into(((ShowAiringTodayViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mShows != null) return mShows.size();
        return 0;
    }

    public void setmShows(List<TVShowModel> mShows) {
        this.mShows = mShows;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public TVShowModel getSelectedShow(int position) {
        if(mShows != null) {
            if(mShows.size() > 0) {
                return mShows.get(position);
            }
        }
        return null;
    }
}
