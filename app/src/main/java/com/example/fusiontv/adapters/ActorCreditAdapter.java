package com.example.fusiontv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class ActorCreditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TVShowModel> mData;
    private OnShowListener onActorTVCreditListener;

    public ActorCreditAdapter(OnShowListener onShowListener) {
        this.onActorTVCreditListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item,
                parent, false);

        return new ActorTVCreditViewHolder(view, onActorTVCreditListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ActorTVCreditViewHolder)holder).name.setText(mData.get(position).getName());
        Glide.with(((ActorTVCreditViewHolder) holder).itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+mData.get(position)
                        .getPoster_path())
                .into(((ActorTVCreditViewHolder)holder).imageView);
    }


    @Override
    public int getItemCount() {
        if(mData != null) return mData.size();
        return 0;
    }

    public void setmShows(List<TVShowModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public TVShowModel getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}
