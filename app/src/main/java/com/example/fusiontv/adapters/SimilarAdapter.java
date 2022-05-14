package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Result;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TVShowModel> mData;
   private OnShowListener onShowSimilarListener;

    public SimilarAdapter(OnShowListener onShowListener) {
        this.onShowSimilarListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_show_item,
                parent, false);

        return new ShowSimilarViewHolder(view, onShowSimilarListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ShowSimilarViewHolder)holder).title.setText(mData.get(position).getName());
        Glide.with(((ShowSimilarViewHolder) holder).itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPoster_path()).into(((ShowSimilarViewHolder)holder).imageView);
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
