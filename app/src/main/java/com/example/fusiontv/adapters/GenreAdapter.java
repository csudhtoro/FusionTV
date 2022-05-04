package com.example.fusiontv.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.R;
import com.example.fusiontv.models.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyGenreViewHolder> {

    private Context mContext;
    private List<Genre> mData;

    public GenreAdapter(Context mContext, List<Genre> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflator = LayoutInflater.from(mContext);
        v = inflator.inflate(R.layout.genre_item, parent, false);

        return new MyGenreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGenreViewHolder Genreholder, int position) {
        Genreholder.name.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyGenreViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public MyGenreViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.genre_name);
        }
    }
}
