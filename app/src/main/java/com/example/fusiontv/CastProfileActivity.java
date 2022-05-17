package com.example.fusiontv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.response.ActorResponse;
import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastProfileActivity extends AppCompatActivity implements OnShowListener {

    //Widgets
    private ImageView actorPhoto;
    private TextView actorName, actorPopularity, actorBday, actorBiography, actorBirthplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_profile);

        actorName = findViewById(R.id.actor_name);
        actorPopularity = findViewById(R.id.actor_popularity);
        actorBday = findViewById(R.id.actor_bday);
        actorBiography = findViewById(R.id.actor_bio);
        actorPhoto = findViewById(R.id.actor_photo);
        actorBirthplace = findViewById(R.id.actor_birthplace);

        Cast cast = null;

        if(getIntent().hasExtra("show2")) {
            cast = getIntent().getParcelableExtra("show2");
            GetDataFromInternet(cast);
        }
    }


    private void GetDataFromInternet(Cast cast) {

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
                            actorBday.setText(response.body().getBirthday());
                        } else {
                            actorBday.setText("N/A");
                        }
                        if (response.body().getPopularity() != null) {
                            actorPopularity.setText(response.body().getPopularity().toString());
                        } else {
                            actorPopularity.setText("N/A");
                        }
                        if (response.body().getBiography() != null) {
                            actorBiography.setText(response.body().getBiography());
                        } else {
                            actorPopularity.setText("N/A");
                        }
                        if (response.body().getPlaceOfBirth()!= null) {
                            actorBirthplace.setText(response.body().getPlaceOfBirth());
                        } else {
                            actorBirthplace.setText("N/A");
                        }

                        Glide.with(CastProfileActivity.this).load("https://image.tmdb.org/t/p/w500" + response.body().getProfilePath()).apply(new RequestOptions().transform(new RoundedCorners(60)))
                                .into(actorPhoto);
                    }
                }
                @Override
                public void onFailure(Call<Actor> call, Throwable t) {

                }
            });

        //}
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