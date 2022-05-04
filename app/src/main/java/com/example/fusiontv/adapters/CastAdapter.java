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
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyCastViewHolder> {


    private Context mContext;
    private List<Cast> mData;
    private CastClickListener mItemListener;

    public CastAdapter(Context mContext, List<Cast> mData, CastClickListener castClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = castClickListener;
    }

    @NonNull
    @Override
    public MyCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflator = LayoutInflater.from(mContext);
        v = inflator.inflate(R.layout.cast_item, parent, false);

        return new MyCastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCastViewHolder Castholder, int position) {
        Castholder.name.setText(mData.get(position).getName());
        Castholder.character.setText(mData.get(position).getCharacter());

        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getProfilePath()).into(Castholder.profile_pic);

        Castholder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(mData.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface  CastClickListener {
        void onItemClick(Cast cast);
    }


    public static class MyCastViewHolder extends RecyclerView.ViewHolder {

        TextView name, character;
        ImageView profile_pic;

        public MyCastViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.actor_name);
            character = itemView.findViewById(R.id.character_name);
            profile_pic = itemView.findViewById(R.id.profile_pic);
        }
    }

    //Getting the id of the show clicked
    public Cast getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}