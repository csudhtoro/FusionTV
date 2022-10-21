package com.example.fusiontv.fragments;

import static com.example.fusiontv.utils.MyUtilities.checkUserLoggedIn;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fusiontv.R;
import com.example.fusiontv.adapters.FavoritesAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.response.TVShowSearchResponse;
import com.example.fusiontv.utils.Credentials;
import com.example.fusiontv.utils.TVApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesFragment extends Fragment implements OnShowListener {


    FirebaseUser currUser;
    DatabaseReference currFavs;
    String currUserId;
    ArrayList<ShowDetailModel> favList;
    private FavoritesAdapter favoritesRecyclerViewAdapter;
    private RecyclerView favoritesRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        currUser = FirebaseAuth.getInstance().getCurrentUser();
        checkDbForFavorites();

        favoritesRecyclerView = getView().findViewById(R.id.favorites_recyclerview);


        favoritesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        favoritesRecyclerView.setHasFixedSize(true);

        ConfigureFavoritesRecyclerView();

    }

    private void checkDbForFavorites() {
        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();
            currFavs = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites");
        }
    }
    private void ClearAll() {
        if(favList != null) {
            favList.clear();

            if(favoritesRecyclerViewAdapter != null) {
                favoritesRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        favList = new ArrayList<>();
    }
    private void ConfigureFavoritesRecyclerView() {

        if(checkUserLoggedIn(currUser)) {
            currFavs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClearAll();

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        ShowDetailModel showDetailModel = new ShowDetailModel();
                        showDetailModel.setId(ds.child("id").getValue(Integer.class));
                        showDetailModel.setName(ds.child("name").getValue(String.class));
                        showDetailModel.setPosterPath(ds.child("posterPath").getValue(String.class));
                        showDetailModel.setBackdropPath(ds.child("backdropPath").getValue(String.class));
                        showDetailModel.setOverview(ds.child("overview").getValue(String.class));
                        showDetailModel.setVoteAverage(ds.child("voteAverage").getValue(Float.class));

                        favList.add(showDetailModel);
                    }
                    favoritesRecyclerViewAdapter = new FavoritesAdapter(FavoritesFragment.this, getContext(), favList);
                    favoritesRecyclerView.setAdapter(favoritesRecyclerViewAdapter);
                    favoritesRecyclerViewAdapter.notifyDataSetChanged();
                    //CheckFavoritesForNextAirDate("2022-05-20", "2022-06-01");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            return;
        }
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
        //Intent intent = new Intent(getActivity(), ShowDetails.class);
        //intent.putExtra("show", favoritesRecyclerViewAdapter.getSelectedShow(position));
        //startActivity(intent);
        ShowDetailFragment showDetailFragment = new ShowDetailFragment();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        TVShowModel tvShowModelToPass = new TVShowModel();
        tvShowModelToPass.setId(favoritesRecyclerViewAdapter.getSelectedShow(position).getId());
        tvShowModelToPass.setName(favoritesRecyclerViewAdapter.getSelectedShow(position).getName());
        tvShowModelToPass.setPoster_path(favoritesRecyclerViewAdapter.getSelectedShow(position).getPosterPath());
        tvShowModelToPass.setBackdrop_path(favoritesRecyclerViewAdapter.getSelectedShow(position).getBackdropPath());
        tvShowModelToPass.setOverview(favoritesRecyclerViewAdapter.getSelectedShow(position).getOverview());
        tvShowModelToPass.setVote_average(favoritesRecyclerViewAdapter.getSelectedShow(position).getVoteAverage());


        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", tvShowModelToPass);
        showDetailFragment.setArguments(bundle);

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
    @Override
    public void onFiscalWeekClick(int adapterPosition) {

    }
    @Override
    public void onNotificationClick(int adapterPosition) {

    }
}