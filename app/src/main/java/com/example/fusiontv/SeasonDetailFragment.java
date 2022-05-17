package com.example.fusiontv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.adapters.EpisodeAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Episode;
import com.example.fusiontv.models.Season;
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

public class SeasonDetailFragment extends Fragment implements OnShowListener {


    //Widgets
    private ImageView seasonPoster, backArrow;
    private TextView seasonName, seasonOverview, seasonAirDate, seasonRating;

    RecyclerView episodeRecyclerView;

    List<Episode> episodeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_season_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seasonPoster = getView().findViewById(R.id.season_poster);
        seasonName = getView().findViewById(R.id.season_appBar_show_name);
        seasonOverview = getView().findViewById(R.id.season_overview);
        backArrow = getView().findViewById(R.id.season_appbar_back_arrow);
        //seasonAirDate = getView().findViewById(R.id.season_air_date);
        //seasonRating = getView().findViewById(R.id.season_rating);


        episodeRecyclerView = getView().findViewById(R.id.episode_recyclerView);

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
        Season season = getArguments().getParcelable("seasonInfo");
        int showId = getArguments().getInt("showId");

        GetDataFromInternet(season, showId);
    }

    private void GetDataFromInternet(Season season, int showId) {

        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + season.getPosterPath()).apply(new RequestOptions().transform(new RoundedCorners(60)))
                .into(seasonPoster);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TVApi tvApi = retrofit.create(TVApi.class);

        Call<Season> call = tvApi.searchSeasonDetails(
                showId,
                season.getSeasonNumber(),
                Credentials.API_KEY
        );

        call.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {

                if (response.code() == 200) {

                    if (response.body().getName() != null) {
                        seasonName.setText(response.body().getName());
                    } else {
                        seasonName.setText("N/A");
                    }
                    //if (response.body().getAirDate() != null) {
                        //seasonAirDate.setText(convertDate(response.body().getAirDate()));
                    //} else {
                        //seasonAirDate.setText("N/A");
                    //}
                    if (response.body().getOverview()!= null) {
                        seasonOverview.setText(response.body().getOverview());
                    } else {
                        seasonOverview.setText("N/A");
                    }
                    episodeList = new ArrayList<>(response.body().getEpisodes());
                    PutEpisodeDataIntoRecyclerView(episodeList);
                }
            }
            @Override
            public void onFailure(Call<Season> call, Throwable t) {

            }
        });
    }
    private void PutEpisodeDataIntoRecyclerView(List<Episode> episodeList) {
        EpisodeAdapter episodeAdapter = new EpisodeAdapter(getContext(), episodeList);
        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        episodeRecyclerView.setAdapter(episodeAdapter);
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

    }

    @Override
    public void onShowActorImageClick(int position) {

    }

    @Override
    public void onShowGenreClick(int adapterPosition) {

    }
}