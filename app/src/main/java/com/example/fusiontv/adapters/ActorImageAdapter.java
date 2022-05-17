package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class ActorImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Profile> mData;
    private OnShowListener onShowActorImageListener;

    public ActorImageAdapter(OnShowListener onShowListener) {
        this.onShowActorImageListener = onShowListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.larger_image_item,
                parent, false);

        return new ActorImageViewHolder(view, onShowActorImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(((ActorImageViewHolder) holder).itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+mData.get(position)
                        .getFilePath())
                .into(((ActorImageViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mData != null) return mData.size();
        return 0;
    }

    public void setmShows(List<Profile> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    //Getting the id of the show clicked
    public Profile getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}
