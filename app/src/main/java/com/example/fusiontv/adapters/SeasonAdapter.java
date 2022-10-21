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
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.SeasonDetail;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MySeasonViewHolder> {

    private Context mContext;
    private List<Season> mData;
    private SeasonClickListener mItemListener;


    public SeasonAdapter(Context mContext, List<Season> mData, SeasonClickListener seasonClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = seasonClickListener;
    }

    @NonNull
    @Override
    public MySeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.similar_show_item, parent, false);

        return new MySeasonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySeasonViewHolder Seasonholder, int position) {
        Seasonholder.seasonName.setText(mData.get(position).getName());

        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPosterPath()).into(Seasonholder.seasonPoster);

        Seasonholder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(mData.get(position));
        });
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public interface SeasonClickListener {
        void onItemClick(Season result);
    }


    public static class MySeasonViewHolder extends RecyclerView.ViewHolder {
        TextView seasonName, seasonNumber;
        ImageView seasonPoster;

        public MySeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            seasonName = itemView.findViewById(R.id.similar_show_title);
            seasonPoster = itemView.findViewById(R.id.similar_show_img);
        }
    }

    //Getting the id of the show clicked
    public Season getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }
}
