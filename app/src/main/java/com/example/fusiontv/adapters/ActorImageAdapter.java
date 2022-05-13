package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Profile;

import java.util.List;

public class ActorImageAdapter extends RecyclerView.Adapter<ActorImageAdapter.MyActorImageViewHolder> {

    private Context mContext;
    private List<Profile> mData;
    private ActorImageClickListener mItemListener;

    public ActorImageAdapter(Context mContext, List<Profile> mData, ActorImageClickListener actorImageClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = actorImageClickListener;
    }

    @NonNull
    @Override
    public ActorImageAdapter.MyActorImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.larger_image_item, parent, false);

        return new MyActorImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorImageAdapter.MyActorImageViewHolder actorImageViewHolder, int position) {
        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getFilePath()).into(actorImageViewHolder.screen);

        actorImageViewHolder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(mData.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ActorImageClickListener {
        void onItemClick(Profile result);
    }

    public static class MyActorImageViewHolder extends RecyclerView.ViewHolder {

        ImageView screen;

        public MyActorImageViewHolder(@NonNull View itemView) {
            super(itemView);
            screen = itemView.findViewById(R.id.screen_img);
        }
    }
}
