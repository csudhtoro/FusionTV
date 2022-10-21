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

public class ShowSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TVShowModel> mShows;
    private OnShowListener onShowSearchListener;

    public ShowSearchAdapter(OnShowListener onShowListener) {
        this.onShowSearchListener = onShowListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,
                parent, false);

        return new ShowSearchViewHolder(view, onShowSearchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((ShowSearchViewHolder)holder).title.setText(mShows.get(i).getName());
        //((ShowSearchViewHolder)holder).genre.setText(mShows.get(i).getGenres().get());
        //((ShowViewHolder)holder).runtime.setText("Runtime here");
        //((ShowViewHolder)holder).runtime.setText(mShows.get(i).getOriginal_language());

        ((ShowSearchViewHolder)holder).vote_avg.setText(String.valueOf(mShows.get(i).getVote_average()));
        ((ShowSearchViewHolder)holder).ratings.setRating((mShows.get(i).getVote_average())/2);

        if(mShows.get(i).getGenres() != null && mShows.get(i).getGenres().size() > 0) {
            ((ShowSearchViewHolder)holder).genre.setText(convertGenreIds(mShows.get(i).getGenres().get(0)));
        } else ((ShowSearchViewHolder)holder).genre.setText("N/A");

        //Imageview - using Glide library
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+mShows.get(i).getPoster_path()).into(((ShowSearchViewHolder)holder).imageView);


    }

    @Override
    public int getItemCount() {
        if(mShows != null) return mShows.size();
        return 0;
    }

    public void setmShows(List<TVShowModel> mShows) {
        this.mShows = mShows;
        notifyDataSetChanged();
    }

    //Getting the id of the show clicked
    public TVShowModel getSelectedShow(int position) {
        if(mShows != null) {
            if(mShows.size() > 0) {
                return mShows.get(position);
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


