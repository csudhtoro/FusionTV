package com.example.fusiontv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;

public class FiscalWeekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    TextView title, genre;
    ImageView imageView;


    //Click Listener
    OnShowListener onFiscalWeekListener;


    public FiscalWeekViewHolder(@NonNull View itemView, OnShowListener onShowListener) {
        super(itemView);

        this.onFiscalWeekListener = onShowListener;


        title = itemView.findViewById(R.id.show_title);
        genre = itemView.findViewById(R.id.show_genre);
        imageView = itemView.findViewById(R.id.show_img);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        onFiscalWeekListener.onFiscalWeekClick(getAdapterPosition());
    }
}
