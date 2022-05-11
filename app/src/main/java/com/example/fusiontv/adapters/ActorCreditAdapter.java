package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.TVCredit;
import com.example.fusiontv.models.TVCredits;

import java.util.List;

public class ActorCreditAdapter extends RecyclerView.Adapter<ActorCreditAdapter.MyActorCreditViewHolder> {

    private Context mContext;
    private List<TVCredit> mData;

    public ActorCreditAdapter(Context mContext, List<TVCredit> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ActorCreditAdapter.MyActorCreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.recommendation_item, parent, false);

        return new MyActorCreditViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorCreditAdapter.MyActorCreditViewHolder actorCreditViewHolder, int position) {
        actorCreditViewHolder.name.setText(mData.get(position).getName());

        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPosterPath()).into(actorCreditViewHolder.screen);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyActorCreditViewHolder extends RecyclerView.ViewHolder {

        ImageView screen;
        TextView name;
        public MyActorCreditViewHolder(@NonNull View itemView) {
            super(itemView);
            screen = itemView.findViewById(R.id.recommendation_show_img);
            name = itemView.findViewById(R.id.recommendation_show_title);
        }
    }
}
