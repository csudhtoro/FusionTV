package com.example.fusiontv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.fusiontv.models.Result;
import com.example.fusiontv.models.ResultRec;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.requests.Services;
import com.example.fusiontv.response.CastResponse;
import com.example.fusiontv.response.BackdropResponse;
import com.example.fusiontv.response.RecommendationResponse;
import com.example.fusiontv.response.TVShowSearchResponse;
import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowDetails extends AppCompatActivity implements OnShowListener {

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
    private ImageView posterImage, backdropImage, favoriteIcon, backArrow;
    private TextView showOverview, firstAirDate, lastAirDate, nextAirDate, network, runtime, voteAvg, season, episodes, appBarShowName;
    //private RatingBar ratingBar;


    //ViewModel
    //private ShowListViewModel showListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        castRecyclerView = (RecyclerView) findViewById(R.id.cast_recyclerview);
        similarRecyclerView = (RecyclerView) findViewById(R.id.similar_shows_recyclerview);
        screenshotRecyclerView = (RecyclerView) findViewById(R.id.screenshots_recyclerview);
        recommendationRecyclerView = (RecyclerView) findViewById(R.id.recommendations_recyclerview);
        genreRecyclerView = (RecyclerView) findViewById(R.id.genre_recyclerview);


        posterImage = (ImageView) findViewById(R.id.showDetailsPoster);
        backdropImage = (ImageView) findViewById(R.id.showDetailsBackdrop);
        showOverview = (TextView) findViewById(R.id.showDetailsoverview);
        firstAirDate = (TextView) findViewById(R.id.firstAirDate);
        lastAirDate = (TextView) findViewById(R.id.lastAirDate);
        nextAirDate = (TextView) findViewById(R.id.nextAirDate);
        network = (TextView) findViewById(R.id.network);
        runtime = (TextView) findViewById(R.id.runtime);
        voteAvg = (TextView) findViewById(R.id.rating);
        season = (TextView) findViewById(R.id.seasons);
        episodes = (TextView) findViewById(R.id.episodes);
        appBarShowName = (TextView) findViewById(R.id.appBar_show_name);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        backArrow = (ImageView) findViewById(R.id.back_arrow);

        currUser = FirebaseAuth.getInstance().getCurrentUser();

        /*backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        //LOAD SHOW DATA BASED ON INTENT FROM OTHER FRAGMENTS/ACTIVITIES
        if (getIntent().hasExtra("show")) {
            TVShowModel tvShowModel = getIntent().getParcelableExtra("show");

            int showId = tvShowModel.getTv_id();

            GetDataFromInternet(tvShowModel);
            GetCastRetrofitResponse(showId);
            GetSimilarRetrofitResponse(showId);
            GetImagesRetrofitResponse(showId);
            GetRecommendationRetrofitResponse(showId);

            if(checkUserLoggedIn(currUser)) {
                currUserId = currUser.getUid();

                //DatabaseReference currFavs = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites").child(tvShowModel.getName());
                DatabaseReference currFavs = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites").child(String.valueOf(tvShowModel.getTv_id()));

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
                                     //Code to remove from favorite from db goes here...
                                    db.removeValue();
                                    favoriteIcon.setImageResource(R.drawable.favorite_icon_unclicked);
                                    Toast.makeText(ShowDetails.this, tvShowModel.getName()+ " removed from favorites!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites");
                                    //db.child(tvShowModel.getName()).setValue(tvShowModel);
                                    db.child(String.valueOf(tvShowModel.getTv_id())).setValue(tvShowModel);
                                    favoriteIcon.setImageResource(R.drawable.favorite_icon_clicked);
                                    Toast.makeText(ShowDetails.this, tvShowModel.getName()+ " added to favorites!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        //else
                        /*db = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites");
                        db.child(tvShowModel.getName()).setValue(tvShowModel);

                        favoriteIcon.setImageResource(R.drawable.favorite_icon_clicked);
                        Toast.makeText(ShowDetails.this, tvShowModel.getName()+ " added to favorites!", Toast.LENGTH_SHORT).show();*/

                    }
                });
            }
            else {
                favoriteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ShowDetails.this, "Please sign in for favorites", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

        }
    }


    private boolean isFavorite(DataSnapshot snapshot, TVShowModel currShow) {
        for(DataSnapshot ds : snapshot.getChildren()) {
            if(ds.child("ShowId").getValue(Integer.class) == currShow.getTv_id()) return true;
        }
        return false;
    }

    //API CALL CODE
    private void GetDataFromInternet(TVShowModel tvShowModel) {

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

                    PutGenreDataIntoRecyclerView(genreList);
                }
                @Override
                public void onFailure(Call<ShowDetailModel> call, Throwable t) {

                }
            });
    }
    private void GetCastRetrofitResponse(int showId) {
        TVApi tvApi = Services.getTvApi();

        Call<CastResponse> responseCall = tvApi
                .searchCast(showId,
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
                //PutCastDataIntoRecyclerView(castList);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });

    }
    private void GetSimilarRetrofitResponse(int showId) {
        TVApi tvApi = Services.getTvApi();

        Call<TVShowSearchResponse> responseCall = tvApi
                .searchSimilar(
                        showId,
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

                //PutSimilarDataIntoRecyclerView(similarList);
            }

            @Override
            public void onFailure(Call<TVShowSearchResponse> call, Throwable t) {

            }
        });
    }
    private void GetImagesRetrofitResponse(int showId) {
        TVApi tvApi = Services.getTvApi();

        Call<BackdropResponse> responseCall = tvApi
                .searchImages(
                        showId,
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
    private void GetRecommendationRetrofitResponse(int showId) {
        TVApi tvApi = Services.getTvApi();

        Call<TVShowSearchResponse> responseCall = tvApi
                .searchRecommendations(
                        showId,
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

                //PutRecommendationDataIntoRecyclerView(recommendationList);
            }

            @Override
            public void onFailure(Call<TVShowSearchResponse> call, Throwable t) {

            }
        });
    }

    //RECYCLERVIEW CODE
    /*private void PutSimilarDataIntoRecyclerView(List<TVShowModel> similarList) {
        SimilarAdapter similarAdapter = new SimilarAdapter(this, similarList, new SimilarAdapter.SimilarClickListener() {

            @Override
            public void onItemClick(TVShowModel result) {
                Intent intent = new Intent(ShowDetails.this, ShowDetails.class);
                intent.putExtra("show", result);
                startActivity(intent);
            }
        });
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false));
        similarRecyclerView.setAdapter(similarAdapter);
    }*/
    /*private void PutCastDataIntoRecyclerView(List<Cast> castList) {
        CastAdapter castAdapter = new CastAdapter(this, castList, new CastAdapter.CastClickListener() {
            @Override
            public void onItemClick(Cast cast) {
                Intent intent = new Intent(ShowDetails.this, CastProfileActivity.class);
                intent.putExtra("show2", cast);
                startActivity(intent);
            }
        });
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castAdapter);
    }*/
    private void PutGenreDataIntoRecyclerView(List<Genre> genreList) {
        GenreAdapter genreAdapter = new GenreAdapter(this, genreList);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false));
        genreRecyclerView.setAdapter(genreAdapter);
    }
    /*private void PutRecommendationDataIntoRecyclerView(List<TVShowModel> recommendationList) {
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendationList, new RecommendationAdapter.RecommendClickListener() {
            @Override
            public void onItemClick(TVShowModel result) {
                Intent intent = new Intent(ShowDetails.this, ShowDetails.class);
                intent.putExtra("show", result);
                startActivity(intent);
            }
        });
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false));
        recommendationRecyclerView.setAdapter(recommendationAdapter);
    }*/
    private void PutImageDataIntoRecyclerView(List<Backdrop> imageList) {
        BackdropAdapter backdropAdapter = new BackdropAdapter(this, imageList, new BackdropAdapter.BackdropClickListener() {
            @Override
            public void onItemClick(Backdrop result) {

            }
        });
        screenshotRecyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false));
        screenshotRecyclerView.setAdapter(backdropAdapter);
    }

    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(user != null) return true;
        return false;
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

}