package com.example.fusiontv.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.R;
import com.example.fusiontv.models.Cast;

public class EnlargeImageFragment extends Fragment {

    ImageView image, backArrow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enlarge_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backArrow = (ImageView) getView().findViewById(R.id.search_back_arrow);
        image = (ImageView) getView().findViewById(R.id.image);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                fm.popBackStack();

                //Toast.makeText(getContext(), "back arrow clicked", Toast.LENGTH_SHORT).show();
            }
        });

        assert getArguments() != null;
        String imageDir = getArguments().getString("image");

        recieveImage(imageDir);
    }

    private void recieveImage(String imageDir) {

        Glide.with(this).load("https://image.tmdb.org/t/p/w780" + imageDir).apply(new RequestOptions().transform(new RoundedCorners(60)))
                .into(image);
    }
}