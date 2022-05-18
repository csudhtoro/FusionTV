package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.adapters.OnShowListener;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title, genre, runtime;
    ImageView imageView;
    RatingBar ratings;


    //Click Listener
    OnShowListener onShowFavoriteListener;


    public FavoritesViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onShowFavoriteListener = onShowListener;


        title = itemView.findViewById(R.id.show_title);
        //genre = itemView.findViewById(R.id.search_show_genre);
        imageView = itemView.findViewById(R.id.show_img);
        //ratings = itemView.findViewById(R.id.ratings);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        onShowFavoriteListener.onFavoritesClick(getAdapterPosition());
    }
}
