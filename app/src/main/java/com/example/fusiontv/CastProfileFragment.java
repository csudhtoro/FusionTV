package com.example.fusiontv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.adapters.ActorCreditAdapter;
import com.example.fusiontv.adapters.ActorImageAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CastProfileFragment extends Fragment implements OnShowListener {


    //Widgets
    private ImageView actorPhoto, backArrow;
    private TextView actorName, actorBday, actorBiography, actorBirthplace;


    private ShowListViewModel showListViewModel;

    //ACTOR IMAGES
    RecyclerView imageRecyclerView;
    private ActorImageAdapter actorImageRecyclerViewAdapter;

    //ACTOR TV CREDITS
    RecyclerView creditRecyclerView;
    private ActorCreditAdapter actorCreditRecyclerViewAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //VIEWMODEL
        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        //FORM VARIABLES
        actorName = getView().findViewById(R.id.season_appBar_show_name);
        actorBday = getView().findViewById(R.id.actor_bday);
        actorBiography = getView().findViewById(R.id.actor_bio);
        actorPhoto = getView().findViewById(R.id.actor_photo);
        actorBirthplace = getView().findViewById(R.id.actor_birthplace);
        backArrow = getView().findViewById(R.id.cast_profile_back_arrow);

        //RECYCLERVIEWS
        imageRecyclerView = getView().findViewById(R.id.imageRecyclerview);
        creditRecyclerView = getView().findViewById(R.id.tvCreditRecyclerview);

        //BACK ARROW
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
        Cast cast = getArguments().getParcelable("show2");


        //ACTOR DETAILS
        showListViewModel.searchActorDetails(cast.getId());
        ObserveActorDetailShowChange();

        //ACTOR IMAGES
        showListViewModel.searchActorImages(cast.getId());
        PutActorImageDataIntoRecyclerView();
        ObserveActorImageChange();

        //ACTOR TV CREDITS
        showListViewModel.searchActorTVCredits(cast.getId());
        PutActorCreditDataIntoRecyclerView();
        ObserveActorTVCreditChange();

    }

    //Observer for LiveData - Actor Details
    private void ObserveActorDetailShowChange() {
        showListViewModel.getActorDetail().observe(getViewLifecycleOwner(), new Observer<Actor>() {
            @Override
            public void onChanged(Actor actor) {
                if(actor != null) {
                    actorName.setText(actor.getName());
                    actorBday.setText(convertDate(actor.getBirthday()));
                    actorBirthplace.setText(actor.getPlaceOfBirth());
                    actorBiography.setText(actor.getBiography());
                    Glide.with(getContext()).load("https://image.tmdb.org/t/p/w780"+actor.getProfilePath())
                            .apply(new RequestOptions().transform(new RoundedCorners(60)))
                            .into(actorPhoto);
                }
            }
        });
    }
    //Observer for LiveData - Actor Images
    private void ObserveActorImageChange() {
        showListViewModel.getActorImages().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                actorImageRecyclerViewAdapter.setmShows(profiles);
            }
        });
    }
    //Observer for LiveData - Actor TV Credits
    private void ObserveActorTVCreditChange() {
        showListViewModel.getActorTVCredits().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModel) {
                actorCreditRecyclerViewAdapter.setmShows(tvShowModel);
            }
        });
    }


    private void PutActorImageDataIntoRecyclerView() {
        actorImageRecyclerViewAdapter = new ActorImageAdapter(this);

        imageRecyclerView.setAdapter(actorImageRecyclerViewAdapter);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
    }
    private void PutActorCreditDataIntoRecyclerView() {
        actorCreditRecyclerViewAdapter = new ActorCreditAdapter(this);

        creditRecyclerView.setAdapter(actorCreditRecyclerViewAdapter);
        creditRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
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


    @Override
    public void onShowClick(int position) {

    }
    @Override
    public void onGenreClick(String Genre) {

    }
    @Override
    public void onShowAiringTodayClick(int position) {

    }
    @Override
    public void onShowTrendingTodayClick(int position) {

    }
    @Override
    public void onShowSearchClick(int position) {

    }
    @Override
    public void onFavoritesClick(int position) {

    }
    @Override
    public void onWatchlistClick(int adapterPosition) {

    }
    @Override
    public void onSeasonClick(int position) {

    }
    @Override
    public void onShowSimilarClick(int position) {

    }
    @Override
    public void onShowRecommendedClick(int position) {

    }
    @Override
    public void onShowCastClick(int position) {

    }
    @Override
    public void onShowBackdropClick(int position) {

    }

    @Override
    public void onActorTVCreditClick(int position) {
        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", actorCreditRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);
    }

    @Override
    public void onShowActorImageClick(int position) {
        EnlargeImageFragment enlargeImageFragment = new EnlargeImageFragment();
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
                .replace(R.id.fragmentFrameLayout, enlargeImageFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putString("image", actorImageRecyclerViewAdapter.getSelectedShow(position).getFilePath());
        enlargeImageFragment.setArguments(bundle);
    }

    @Override
    public void onShowGenreClick(int adapterPosition) {

    }
}