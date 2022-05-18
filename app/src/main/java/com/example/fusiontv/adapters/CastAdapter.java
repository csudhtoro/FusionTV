package com.example.fusiontv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cast> mData;
    private OnShowListener onShowCastListener;

    public CastAdapter(OnShowListener onShowListener) {
        this.onShowCastListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item,
                parent, false);


        return new ShowCastViewHolder(view, onShowCastListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ShowCastViewHolder)holder).name.setText(mData.get(position).getName());
        ((ShowCastViewHolder)holder).character.setText(mData.get(position).getCharacter());
        Glide.with(((ShowCastViewHolder) holder).itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getProfilePath()).into(((ShowCastViewHolder)holder).profile_pic);
    }

    @Override
    public int getItemCount() {
        if(mData != null) return mData.size();
        return 0;
    }

    public void setmShows(List<Cast> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    //Getting the id of the show clicked
    public Cast getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}