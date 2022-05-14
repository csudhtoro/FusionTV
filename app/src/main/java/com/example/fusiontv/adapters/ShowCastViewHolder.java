package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class ShowCastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView name, character;
    ImageView profile_pic;


    //Click Listener
    OnShowListener onShowCastListener;


    public ShowCastViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowCastListener = onShowListener;


        name = itemView.findViewById(R.id.actor_name);
        character = itemView.findViewById(R.id.character_name);
        profile_pic = itemView.findViewById(R.id.profile_pic);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onShowCastListener.onShowCastClick(getAdapterPosition());
    }
}
