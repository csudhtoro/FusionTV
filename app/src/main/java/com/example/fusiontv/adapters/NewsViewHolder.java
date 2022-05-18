package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title;
    ImageView imageView;


    //Click Listener
    OnShowListener onGenreListener;


    public NewsViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onGenreListener = onShowListener;

        title = itemView.findViewById(R.id.similar_show_title);
        imageView = itemView.findViewById(R.id.similar_show_img);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onGenreListener.onShowGenreClick(getAdapterPosition());
    }
}
