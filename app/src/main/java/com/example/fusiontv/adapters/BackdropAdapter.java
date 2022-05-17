package com.example.fusiontv.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.TVShowModel;

import org.w3c.dom.Text;

import java.util.List;

public class BackdropAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Backdrop> mData;
    private OnShowListener onShowImageListener;

    public BackdropAdapter(OnShowListener onShowListener) {
        this.onShowImageListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,
                parent, false);


        return new ShowImageViewHolder(view, onShowImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(((ShowImageViewHolder) holder).itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getFilePath()).into(((ShowImageViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mData != null) return mData.size();
        return 0;
    }

    public void setmShows(List<Backdrop> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public Backdrop getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}
