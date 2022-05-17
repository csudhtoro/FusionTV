package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class ActorImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    ImageView imageView;


    //Click Listener
    OnShowListener onShowActorImageListener;


    public ActorImageViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowActorImageListener = onShowListener;


        imageView = itemView.findViewById(R.id.screen_img);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onShowActorImageListener.onShowActorImageClick(getAdapterPosition());
    }
}
