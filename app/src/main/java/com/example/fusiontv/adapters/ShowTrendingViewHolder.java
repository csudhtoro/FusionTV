package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.adapters.OnShowListener;

public class ShowTrendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title, genre;
    ImageView imageView;


    //Click Listener
    OnShowListener onShowTrendingListener;


    public ShowTrendingViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowTrendingListener = onShowListener;


        title = itemView.findViewById(R.id.show_title);
        genre = itemView.findViewById(R.id.show_genre);
        imageView = itemView.findViewById(R.id.show_img);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        onShowTrendingListener.onShowTrendingTodayClick(getAdapterPosition());
    }
}
