package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class ShowRecommendedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title;
    ImageView imageView;


    //Click Listener
    OnShowListener onShowRecommendedListener;


    public ShowRecommendedViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowRecommendedListener = onShowListener;

        title = itemView.findViewById(R.id.recommendation_show_title);
        imageView = itemView.findViewById(R.id.recommendation_show_img);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onShowRecommendedListener.onShowRecommendedClick(getAdapterPosition());
    }
}
