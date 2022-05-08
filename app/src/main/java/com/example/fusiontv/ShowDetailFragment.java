package com.example.fusiontv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.fusiontv.adapters.BackdropAdapter;
import com.example.fusiontv.adapters.CastAdapter;
import com.example.fusiontv.adapters.GenreAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.adapters.RecommendationAdapter;
import com.example.fusiontv.adapters.SimilarAdapter;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Genre;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.requests.Services;
import com.example.fusiontv.response.BackdropResponse;
import com.example.fusiontv.response.CastResponse;
import com.example.fusiontv.response.TVShowSearchResponse;
import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShowDetailFragment extends Fragment implements OnShowListener {

    FirebaseUser currUser;
    String currUserId;
    DatabaseReference db;

    List<Cast> castList;
    List<TVShowModel> similarList;
    List<Backdrop> imageList;
    List<TVShowModel> recommendationList;
    List<Genre> genreList;

    RecyclerView castRecyclerView;
    RecyclerView similarRecyclerView;
    RecyclerView screenshotRecyclerView;
    RecyclerView recommendationRecyclerView;
    RecyclerView genreRecyclerView;

    private SimilarAdapter similarAdapter;
    private CastAdapter castAdapter;

    //Widgets
    private ImageView posterImage, backdropImage, favoriteIcon, backArrow, scheduleIcon;
    private TextView showOverview, firstAirDate, lastAirDate, nextAirDate, network, runtime, voteAvg, season, episodes, appBarShowName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        castRecyclerView = (RecyclerView) getView().findViewById(R.id.cast_recyclerview);
        similarRecyclerView = (RecyclerView) getView().findViewById(R.id.similar_shows_recyclerview);
        screenshotRecyclerView = (RecyclerView) getView().findViewById(R.id.screenshots_recyclerview);
        recommendationRecyclerView = (RecyclerView) getView().findViewById(R.id.recommendations_recyclerview);
        genreRecyclerView = (RecyclerView) getView().findViewById(R.id.genre_recyclerview);

        posterImage = (ImageView) getView().findViewById(R.id.showDetailsPoster);
        backdropImage = (ImageView) getView().findViewById(R.id.showDetailsBackdrop);
        showOverview = (TextView) getView().findViewById(R.id.showDetailsoverview);
        firstAirDate = (TextView) getView().findViewById(R.id.firstAirDate);
        lastAirDate = (TextView) getView().findViewById(R.id.lastAirDate);
        nextAirDate = (TextView) getView().findViewById(R.id.nextAirDate);
        network = (TextView) getView().findViewById(R.id.network);
        runtime = (TextView) getView().findViewById(R.id.runtime);
        voteAvg = (TextView) getView().findViewById(R.id.rating);
        season = (TextView) getView().findViewById(R.id.seasons);
        episodes = (TextView) getView().findViewById(R.id.episodes);
        appBarShowName = (TextView) getView().findViewById(R.id.appBar_show_name);
        favoriteIcon = (ImageView) getView().findViewById(R.id.favorite_icon);
        scheduleIcon = (ImageView) getView().findViewById(R.id.schedule_icon);
        backArrow = (ImageView) getView().findViewById(R.id.back_arrow);

        currUser = FirebaseAuth.getInstance().getCurrentUser();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "back arrow clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //LOAD SHOW DATA BASED ON INTENT FROM OTHER FRAGMENTS/ACTIVITIES
            //TVShowModel tvShowModel = getActivity().getIntent().getParcelableExtra("showId");

        assert getArguments() != null;
        TVShowModel tvShowModel = getArguments().getParcelable("showInfo");

        int showId = tvShowModel.getTv_id();

            GetDataFromInternet(tvShowModel);
            GetCastRetrofitResponse(tvShowModel);
            GetSimilarRetrofitResponse(tvShowModel);
            GetImagesRetrofitResponse(tvShowModel);
            GetRecommendationRetrofitResponse(tvShowModel);
            CheckFavorites(tvShowModel);
    }


    //API CALL CODE
    private void GetDataFromInternet(TVShowModel tvShowModel) {

        final ShowDetailModel[] showDetail = new ShowDetailModel[1];

        //pulling images from api (poster and backdrop)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +tvShowModel.getPoster_path()).apply(new RequestOptions().transform(new RoundedCorners(60)))
                .into(posterImage);

        Glide.with(this).load("https://image.tmdb.org/t/p/w780" +tvShowModel.getBackdrop_path())
                .into(backdropImage);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TVApi tvApi = retrofit.create(TVApi.class);

        Call<ShowDetailModel> call = tvApi.getShowDetails(tvShowModel.getTv_id(), Credentials.API_KEY);

        call.enqueue(new Callback<ShowDetailModel>() {
            @Override
            public void onResponse(Call<ShowDetailModel> call, Response<ShowDetailModel> response) {

                showDetail[0] = response.body();

                if (response.body().getOverview() != null) {
                    showOverview.setText(response.body().getOverview());
                } else {
                    showOverview.setText("No Overview");
                }
                if (response.body().getFirstAirDate() != null) {
                    firstAirDate.setText(response.body().getFirstAirDate());
                } else {
                    firstAirDate.setText("N/A");
                }
                if (response.body().getLastAirDate() != null) {
                    lastAirDate.setText(response.body().getLastAirDate());
                } else {
                    lastAirDate.setText("N/A");
                }
                if (response.body().getNextEpisodeToAir() != null) {
                    nextAirDate.setText(response.body().getNextEpisodeToAir().getAirDate());
                } else {
                    nextAirDate.setText("N/A");
                }
                if (response.body().getNetworks() != null && response.body().getNetworks().size() > 0) {
                    network.setText(response.body().getNetworks().get(0).getName());
                } else {
                    network.setText("N/A");
                }
                if (response.body().getEpisodeRunTime() != null && response.body().getEpisodeRunTime().size() > 0) {
                    runtime.setText(response.body().getEpisodeRunTime().get(0).toString());
                } else {
                    runtime.setText("N/A");
                }
                if (response.body().getVoteAverage() != null) {
                    voteAvg.setText(response.body().getVoteAverage().toString());
                } else {
                    voteAvg.setText("N/A");
                }
                if (response.body().getNumberOfSeasons() != null) {
                    season.setText(response.body().getNumberOfSeasons().toString());
                } else {
                    season.setText("N/A");
                }
                if (response.body().getNumberOfEpisodes() != null) {
                    episodes.setText(response.body().getNumberOfEpisodes().toString());
                } else {
                    episodes.setText("N/A");
                }
                appBarShowName.setText(response.body().getName());
                genreList = new ArrayList<>(response.body().getGenres());

                //CREATING SHOW GENRE RECYCLERVIEW
                PutGenreDataIntoRecyclerView(genreList);

                //CHECKING IF SHOW IS IN WATCHLIST AND PREPARING DB
                CheckWatchList(showDetail[0]);
            }
            @Override
            public void onFailure(Call<ShowDetailModel> call, Throwable t) {

            }
        });
    }
    private void GetCastRetrofitResponse(TVShowModel tvShowModel) {
        TVApi tvApi = Services.getTvApi();

        Call<CastResponse> responseCall = tvApi
                .searchCast(tvShowModel.getTv_id(),
                        Credentials.API_KEY);

        responseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if(response.code() == 200) {
                    //Log.v("Tag", "the response is: " +response.body().toString());

                    castList = new ArrayList<>(response.body().getCast());

                    for(Cast cast : castList) {
                        //Log.v("Tag", "Actor: " + cast.getName());
                    }
                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                PutCastDataIntoRecyclerView(castList);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });

    }
    private void GetSimilarRetrofitResponse(TVShowModel tvShowModel) {
        TVApi tvApi = Services.getTvApi();

        Call<TVShowSearchResponse> responseCall = tvApi
                .searchSimilar(
                        tvShowModel.getTv_id(),
                        Credentials.API_KEY,
                        1);

        responseCall.enqueue(new Callback<TVShowSearchResponse>() {
            @Override
            public void onResponse(Call<TVShowSearchResponse> call, Response<TVShowSearchResponse> response) {
                if(response.code() == 200) {
                    //Log.v("Tag", "The response is: " +response.body().toString());

                    similarList = new ArrayList<>(response.body().getShows());

                    for(TVShowModel similar : similarList) {
                        //Log.v("Tag", "Show title: " +similar.getName());
                    }

                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PutSimilarDataIntoRecyclerView(similarList);
            }

            @Override
            public void onFailure(Call<TVShowSearchResponse> call, Throwable t) {

            }
        });
    }
    private void GetImagesRetrofitResponse(TVShowModel tvShowModel) {
        TVApi tvApi = Services.getTvApi();

        Call<BackdropResponse> responseCall = tvApi
                .searchImages(
                        tvShowModel.getTv_id(),
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<BackdropResponse>() {
            @Override
            public void onResponse(Call<BackdropResponse> call, Response<BackdropResponse> response) {
                if(response.code() == 200) {
                    //Log.v("Tag", "The response is: " +response.body().toString());

                    imageList = new ArrayList<>(response.body().getImages());

                    for(Backdrop image : imageList) {
                        //Log.v("Tag", "Image path: " +image.getFilePath());
                    }

                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PutImageDataIntoRecyclerView(imageList);
            }

            @Override
            public void onFailure(Call<BackdropResponse> call, Throwable t) {

            }
        });
    }
    private void GetRecommendationRetrofitResponse(TVShowModel tvShowModel) {
        TVApi tvApi = Services.getTvApi();

        Call<TVShowSearchResponse> responseCall = tvApi
                .searchRecommendations(
                        tvShowModel.getTv_id(),
                        Credentials.API_KEY,
                        1
                );

        responseCall.enqueue(new Callback<TVShowSearchResponse>() {
            @Override
            public void onResponse(Call<TVShowSearchResponse> call, Response<TVShowSearchResponse> response) {
                if(response.code() == 200) {
                    //Log.v("Tag", "The response is: " +response.body().toString());

                    recommendationList = new ArrayList<>(response.body().getShows());

                    for(TVShowModel recommendation : recommendationList) {
                        //Log.v("Tag", "show name: " +recommendation.getName());
                    }

                }
                else {
                    try {
                        Log.v("Tag", "Error: " +response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PutRecommendationDataIntoRecyclerView(recommendationList);
            }

            @Override
            public void onFailure(Call<TVShowSearchResponse> call, Throwable t) {

            }
        });
    }

    //RECYCLERVIEW CODE
    private void PutSimilarDataIntoRecyclerView(List<TVShowModel> similarList) {
        SimilarAdapter similarAdapter = new SimilarAdapter(getContext(), similarList, new SimilarAdapter.SimilarClickListener() {

            @Override
            public void onItemClick(TVShowModel result) {

                ShowDetailFragment showDetailFragment = new ShowDetailFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrameLayout, showDetailFragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("showInfo", result);
                showDetailFragment.setArguments(bundle);

            }
        });
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        similarRecyclerView.setAdapter(similarAdapter);
    }
    private void PutCastDataIntoRecyclerView(List<Cast> castList) {
        CastAdapter castAdapter = new CastAdapter(getContext(), castList, new CastAdapter.CastClickListener() {
            @Override
            public void onItemClick(Cast cast) {
                Intent intent = new Intent(getContext(), CastProfileActivity.class);
                intent.putExtra("show2", cast);
                startActivity(intent);
            }
        });
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castAdapter);
    }
    private void PutGenreDataIntoRecyclerView(List<Genre> genreList) {
        GenreAdapter genreAdapter = new GenreAdapter(getContext(), genreList);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        genreRecyclerView.setAdapter(genreAdapter);
    }
    private void PutRecommendationDataIntoRecyclerView(List<TVShowModel> recommendationList) {
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getContext(), recommendationList, new RecommendationAdapter.RecommendClickListener() {
            @Override
            public void onItemClick(TVShowModel result) {

                ShowDetailFragment showDetailFragment = new ShowDetailFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrameLayout, showDetailFragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("showInfo", result);
                showDetailFragment.setArguments(bundle);
            }
        });
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        recommendationRecyclerView.setAdapter(recommendationAdapter);
    }
    private void PutImageDataIntoRecyclerView(List<Backdrop> imageList) {
        BackdropAdapter backdropAdapter = new BackdropAdapter(getContext(), imageList);
        screenshotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        screenshotRecyclerView.setAdapter(backdropAdapter);
    }



    //FAVORITES
    private void CheckFavorites(TVShowModel tvShowModel) {
        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();

            DatabaseReference currFavs = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites").child(String.valueOf(tvShowModel.getTv_id()));
            //DatabaseReference currWatchLists = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("WatchList").child(String.valueOf(showDetailModel.getNextEpisodeToAir().getAirDate()));

            currFavs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {

                        //if(snapshot.getKey().equals(tvShowModel.getName())) {
                        if(snapshot.getKey().equals(String.valueOf(tvShowModel.getTv_id()))) {
                            //Log.v("Tag", "Match!: " + ds.child("showName"));
                            favoriteIcon.setImageResource(R.drawable.favorite_icon_clicked);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.v("Tag", "onCancelled called!");
                }
            });

            /*currWatchLists.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {

                        if(snapshot.getKey().equals(String.valueOf(showId))) {
                            //Log.v("Tag", "Match!: " + ds.child("showName"));
                            scheduleIcon.setImageResource(R.drawable.schedule_icon_clicked);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.v("Tag", "onCancelled called!");
                }
            });*/

            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if current show is already in the db favs, delete it from the db and apply unclicked icon. I now have access to the id (the show name)
                    //db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites").child(tvShowModel.getName());
                    db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites").child(String.valueOf(tvShowModel.getTv_id()));
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                db.removeValue();
                                favoriteIcon.setImageResource(R.drawable.favorite_icon_unclicked);
                                Toast.makeText(getContext(), tvShowModel.getName()+ " removed from favorites!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites");
                                db.child(String.valueOf(tvShowModel.getTv_id())).setValue(tvShowModel);
                                favoriteIcon.setImageResource(R.drawable.favorite_icon_clicked);
                                Toast.makeText(getContext(), tvShowModel.getName()+ " added to favorites!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }
        else {
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Please sign in for favorites", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }

    }

    //WATCHLIST/SCHEDULE
    private void CheckWatchList(ShowDetailModel showDetailModel) {

        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();

            if(showDetailModel.getNextEpisodeToAir() != null) {

                String nextAirDate = convertDate(showDetailModel.getNextEpisodeToAir().getAirDate());

                DatabaseReference currWatchlist = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("WatchList").child(nextAirDate);

                currWatchlist.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.getKey().equals(nextAirDate)) {
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    if(Integer.valueOf(ds.getKey()).equals(showDetailModel.getId())) {
                                        scheduleIcon.setImageResource(R.drawable.schedule_icon_clicked);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v("Tag", "onCancelled called!");
                    }
                });
            }

            scheduleIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (showDetailModel.getNextEpisodeToAir() != null) {
                        String nextAirDate = convertDate(showDetailModel.getNextEpisodeToAir().getAirDate());
                        db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("WatchList").child(nextAirDate).child(String.valueOf(showDetailModel.getId()));
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                db.removeValue();
                                scheduleIcon.setImageResource(R.drawable.schedule_icon_unclicked);
                                Toast.makeText(getContext(), showDetailModel.getName() + " removed from watchlist!", Toast.LENGTH_SHORT).show();
                            } else {
                                db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("WatchList");
                                db.child(nextAirDate).child(String.valueOf(showDetailModel.getId())).setValue(showDetailModel);
                                scheduleIcon.setImageResource(R.drawable.schedule_icon_clicked);
                                Toast.makeText(getContext(), showDetailModel.getName() + " added to watchlist!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                        Toast.makeText(getContext(), "No future air date exist!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            scheduleIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Please sign in for watchlist", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
    }



    //CHECK IF USER IS LOGGED IN
    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(user != null) return true;
        return false;
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




    //ON CLICK INTERFACE CODE
    @Override
    public void onShowClick(int position){}
    @Override
    public void onGenreClick(String Genre){}
    @Override
    public void onShowAiringTodayClick(int position){}
    @Override
    public void onShowTrendingTodayClick(int position){}
    @Override
    public void onShowSearchClick(int position){}
    @Override
    public void onFavoritesClick(int position){}
    @Override
    public void onWatchlistClick(int adapterPosition){}
}