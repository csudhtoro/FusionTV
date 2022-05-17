package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class ActorTVCreditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView name;
    ImageView imageView;


    //Click Listener
    OnShowListener onActorTVCreditListener;


    public ActorTVCreditViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onActorTVCreditListener = onShowListener;

        name = itemView.findViewById(R.id.recommendation_show_title);
        imageView = itemView.findViewById(R.id.recommendation_show_img);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onActorTVCreditListener.onActorTVCreditClick(getAdapterPosition());
    }
}
