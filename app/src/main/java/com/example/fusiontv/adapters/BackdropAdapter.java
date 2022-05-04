package com.example.fusiontv.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Backdrop;

import org.w3c.dom.Text;

import java.util.List;

public class BackdropAdapter extends RecyclerView.Adapter<BackdropAdapter.MyBackdropViewHolder> {

    private Context mContext;
    private List<Backdrop> mData;

    public BackdropAdapter(Context mContext, List<Backdrop> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BackdropAdapter.MyBackdropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.image_item, parent, false);

        return new MyBackdropViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BackdropAdapter.MyBackdropViewHolder Backdropholder, int position) {
        //Using Glide to display the image
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getFilePath()).into(Backdropholder.screen);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyBackdropViewHolder extends RecyclerView.ViewHolder {

        ImageView screen;

        public MyBackdropViewHolder(@NonNull View itemView) {
            super(itemView);
            screen = itemView.findViewById(R.id.screen_img);
        }
    }
}
