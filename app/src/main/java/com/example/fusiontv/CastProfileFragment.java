package com.example.fusiontv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.adapters.ActorCreditAdapter;
import com.example.fusiontv.adapters.ActorImageAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.TVCredit;
import com.example.fusiontv.models.TVCredits;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.requests.Services;
import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastProfileFragment extends Fragment implements OnShowListener {


    //Widgets
    private ImageView actorPhoto, backArrow;
    private TextView actorName, actorPopularity, actorBday, actorBiography, actorBirthplace;

    RecyclerView imageRecyclerView;
    RecyclerView creditRecyclerView;

    List<Profile> imageList;
    List<TVCredit> creditList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actorName = getView().findViewById(R.id.season_appBar_show_name);
        actorBday = getView().findViewById(R.id.actor_bday);
        actorBiography = getView().findViewById(R.id.actor_bio);
        actorPhoto = getView().findViewById(R.id.actor_photo);
        actorBirthplace = getView().findViewById(R.id.actor_birthplace);
        backArrow = getView().findViewById(R.id.cast_profile_back_arrow);

        imageRecyclerView = getView().findViewById(R.id.imageRecyclerview);
        creditRecyclerView = getView().findViewById(R.id.tvCreditRecyclerview);

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

        GetDataFromInternet(cast);
        GetActorImagesRetrofitResponse(cast);
        GetActorTVCreditsRetrofitResponse(cast);

    }

    private void GetDataFromInternet(Cast cast) {

        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + cast.getProfilePath()).apply(new RequestOptions().transform(new RoundedCorners(60)))
                .into(actorPhoto);
        actorName.setText(cast.getName());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TVApi tvApi = retrofit.create(TVApi.class);

        Call<Actor> call = tvApi.searchActor(cast.getId(), Credentials.API_KEY);

        call.enqueue(new Callback<Actor>() {
            @Override
            public void onResponse(Call<Actor> call, Response<Actor> response) {

                if (response.code() == 200) {
                    Log.v("Tag", "response body = "+response.body().toString());

                    if (response.body().getName() != null) {
                        actorName.setText(response.body().getName());
                    } else {
                        actorName.setText("N/A");
                    }
                    if (response.body().getBirthday() != null) {
                        actorBday.setText(convertDate(response.body().getBirthday()));
                    } else {
                        actorBday.setText("N/A");
                    }
                    if (response.body().getPlaceOfBirth()!= null) {
                        actorBirthplace.setText(response.body().getPlaceOfBirth());
                    } else {
                        actorBirthplace.setText("N/A");
                    }
                    if (response.body().getBiography() != null) {
                        actorBiography.setText(response.body().getBiography());
                    } else {
                        actorPopularity.setText("N/A");
                    }
                }
            }
            @Override
            public void onFailure(Call<Actor> call, Throwable t) {

            }
        });

        //}
    }
    private void GetActorImagesRetrofitResponse(Cast cast) {
        TVApi tvApi = Services.getTvApi();

        Call<ActorProfile> responseCall = tvApi
                .searchActorImages(
                        cast.getId(),
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<ActorProfile>() {
            @Override
            public void onResponse(Call<ActorProfile> call, Response<ActorProfile> response) {
                if(response.code() == 200) {
                    imageList = new ArrayList<>(response.body().getProfiles());
                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PutActorImageDataIntoRecyclerView(imageList);
            }

            @Override
            public void onFailure(Call<ActorProfile> call, Throwable t) {

            }
        });
    }
    private void GetActorTVCreditsRetrofitResponse(Cast cast) {
        TVApi tvApi = Services.getTvApi();

        Call<TVCredits> responseCall = tvApi
                .searchActorCredits(
                        cast.getId(),
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<TVCredits>() {
            @Override
            public void onResponse(Call<TVCredits> call, Response<TVCredits> response) {
                if(response.code() == 200) {
                    creditList = new ArrayList<>(response.body().getCast());
                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PutActorCreditDataIntoRecyclerView(creditList);
            }

            @Override
            public void onFailure(Call<TVCredits> call, Throwable t) {

            }
        });
    }


    private void PutActorImageDataIntoRecyclerView(List<Profile> imageList) {
        ActorImageAdapter actorImageAdapter = new ActorImageAdapter(getContext(), imageList, new ActorImageAdapter.ActorImageClickListener() {
            @Override
            public void onItemClick(Profile result) {
                EnlargeImageFragment enlargeImageFragment = new EnlargeImageFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrameLayout, enlargeImageFragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putString("image", result.getFilePath());
                enlargeImageFragment.setArguments(bundle);
            }
        });
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        imageRecyclerView.setAdapter(actorImageAdapter);
    }
    private void PutActorCreditDataIntoRecyclerView(List<TVCredit> creditList) {
        ActorCreditAdapter actorCreditAdapter = new ActorCreditAdapter(getContext(), creditList, new ActorCreditAdapter.CreditClickListener() {
            @Override
            public void onItemClick(TVCredit result) {

                TVShowModel tvShowModel = new TVShowModel();

                tvShowModel.setId(result.getId());
                tvShowModel.setPoster_path(result.getPosterPath());
                tvShowModel.setBackdrop_path(result.getBackdropPath());
                tvShowModel.setName(result.getName());
                tvShowModel.setOverview(result.getOverview());

                ShowDetailFragment showDetailFragment = new ShowDetailFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrameLayout, showDetailFragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("showInfo", tvShowModel);
                showDetailFragment.setArguments(bundle);

                //Toast.makeText(getContext(), "Clicked season", Toast.LENGTH_SHORT).show();
            }
        });
        creditRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        creditRecyclerView.setAdapter(actorCreditAdapter);
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
}