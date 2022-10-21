package com.example.fusiontv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.TVShowModel;

import java.util.List;

public class ActionAdventureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TVShowModel> mData;
    private OnShowListener onGenreListener;

    public ActionAdventureAdapter(OnShowListener onShowListener) {
        this.onGenreListener = onShowListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item,
                parent, false);

        return new ActionAdventureViewHolder(view, onGenreListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ActionAdventureViewHolder)holder).title.setText(mData.get(position).getName());
        Glide.with(((ActionAdventureViewHolder) holder).itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+mData.get(position)
                        .getPoster_path())
                .into(((ActionAdventureViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mData != null) return mData.size();
        return 0;
    }

    public void setmShows(List<TVShowModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public TVShowModel getSelectedShow(int position) {
        if(mData != null) {
            if(mData.size() > 0) {
                return mData.get(position);
            }
        }
        return null;
    }

    String convertGenreIds(int genreId) {
        String  genreName = "";

        switch (genreId) {
            case 10759:
                genreName = "Action & Adventure";
                break;
            case 16:
                genreName = "Animation";
                break;
            case 35:
                genreName = "Comedy";
                break;
            case 80:
                genreName = "Crime";
                break;
            case 99:
                genreName = "Documentary";
                break;
            case 18:
                genreName = "Drama";
                break;
            case 10751:
                genreName = "Family";
                break;
            case 10762:
                genreName = "Kids";
                break;
            case 9648:
                genreName = "Mystery";
                break;
            case 10763:
                genreName = "News";
                break;
            case 10764:
                genreName = "Reality";
                break;
            case 10765:
                genreName = "Sci-Fi & Fantasy";
                break;
            case 10766:
                genreName = "Soap";
                break;
            case 10767:
                genreName = "Talk";
                break;
            case 10768:
                genreName = "War & Politics";
                break;
            case 37:
                genreName = "Western";
                break;
        }
        return genreName;
    }
}
