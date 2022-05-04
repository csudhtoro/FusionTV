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

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.MySimilarViewHolder> {

    private Context mContext;
    private List<TVShowModel> mData;
    private SimilarClickListener mItemListener;


    public SimilarAdapter(Context mContext, List<TVShowModel> mData, SimilarClickListener similarClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = similarClickListener;
    }

    @NonNull
    @Override
    public MySimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.similar_show_item, parent, false);

        return new MySimilarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySimilarViewHolder Similarholder, int position) {
        Similarholder.title.setText(mData.get(position).getName());

        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPoster_path()).into(Similarholder.showImg);

        Similarholder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(mData.get(position));
        });
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public interface SimilarClickListener {
        void onItemClick(TVShowModel result);
    }


    public static class MySimilarViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView showImg;

        public MySimilarViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.similar_show_title);
            showImg = itemView.findViewById(R.id.similar_show_img);
        }
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
