package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.adapters.OnShowListener;

public class ShowSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title, vote_avg, genre;
    ImageView imageView;
    RatingBar ratings;


    //Click Listener
    OnShowListener onShowSearchListener;


    public ShowSearchViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowSearchListener = onShowListener;


        title = itemView.findViewById(R.id.search_item_title);
        genre = itemView.findViewById(R.id.search_item_genre);
        imageView = itemView.findViewById(R.id.search_item_img);
        vote_avg = itemView.findViewById(R.id.search_item_voteAvg);
        ratings = itemView.findViewById(R.id.search_item_rating);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        onShowSearchListener.onShowSearchClick(getAdapterPosition());
    }
}
