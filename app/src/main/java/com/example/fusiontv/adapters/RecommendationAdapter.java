package com.example.fusiontv.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.ShowDetails;
import com.example.fusiontv.models.ResultRec;
import com.example.fusiontv.models.TVShowModel;

import java.util.ConcurrentModificationException;
import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.MyRecommendationViewHolder> {

    private Context mContext;
    private List<TVShowModel> mData;
    private RecommendClickListener mItemListener;

    public RecommendationAdapter(Context mContext, List<TVShowModel> mData, RecommendClickListener recommendClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = recommendClickListener;
    }

    @NonNull
    @Override
    public MyRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.recommendation_item, parent, false);

        return new MyRecommendationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecommendationViewHolder Recommendationholder, int position) {
        Recommendationholder.title.setText(mData.get(position).getName());

        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPoster_path()).into(Recommendationholder.showImg);

        Recommendationholder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(mData.get(position));
        });
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public interface RecommendClickListener {
        void onItemClick(TVShowModel result);
    }


    public static class MyRecommendationViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView showImg;

        public MyRecommendationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.recommendation_show_title);
            showImg = itemView.findViewById(R.id.recommendation_show_img);
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
