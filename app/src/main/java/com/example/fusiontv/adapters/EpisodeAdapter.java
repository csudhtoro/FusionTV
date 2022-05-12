package com.example.fusiontv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Episode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyEpisodeViewHolder> {

    private Context mContext;
    private List<Episode> mData;

    public EpisodeAdapter(Context mContext, List<Episode> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public EpisodeAdapter.MyEpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.episode_item, parent, false);

        return new MyEpisodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.MyEpisodeViewHolder MyEpisodeHolder, int position) {
        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getStillPath()).into(MyEpisodeHolder.screen);

        MyEpisodeHolder.name.setText(mData.get(position).getName());
        MyEpisodeHolder.date.setText(convertDate(mData.get(position).getAirDate()));
        MyEpisodeHolder.number.setText(String.valueOf(mData.get(position).getEpisodeNumber()));
        MyEpisodeHolder.overview.setText(mData.get(position).getOverview());
        MyEpisodeHolder.rating.setText(String.valueOf(mData.get(position).getVoteAverage() / 2));

        MyEpisodeHolder.ratingBar.setRating(convertDouble(mData.get(position).getVoteAverage() / 2));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyEpisodeViewHolder extends RecyclerView.ViewHolder {

        ImageView screen;
        TextView name, date, number, overview, rating;
        RatingBar ratingBar;

        public MyEpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.episode_name);
            screen = itemView.findViewById(R.id.episode_img);
            date = itemView.findViewById(R.id.episode_date);
            number = itemView.findViewById(R.id.episode_number);
            overview = itemView.findViewById(R.id.episode_overview);
            rating = itemView.findViewById(R.id.episode_rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }

    //CONVERT THE DEFAULT TMDB DATE FORMATS TO MM-DD-YYYY
    private String convertDate(String inDate) {

        SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outSDF = new SimpleDateFormat("mm-dd-yyyy");

        String outDate = "";

        if(inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outDate;
    }

    //CAST DOUBLE TO FLOAT
    private Float convertDouble(double inVal) {
        float outVal = (float) inVal;
        return outVal;
    }
}
