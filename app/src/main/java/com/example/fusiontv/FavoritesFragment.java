package com.example.fusiontv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusiontv.adapters.FavoritesAdapter;
import com.example.fusiontv.adapters.OnShowListener;
import com.example.fusiontv.models.TVShowModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment implements OnShowListener {


    FirebaseUser currUser;
    DatabaseReference currFavs;
    String currUserId;
    ArrayList<TVShowModel> favList;
    private FavoritesAdapter favoritesRecyclerViewAdapter;
    private RecyclerView favoritesRecyclerView;
    private Context mContext;

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

        //favoritesRecyclerView = getView().findViewById(R.id.favorites_recyclerview);
        favoritesRecyclerView = getView().findViewById(R.id.favorites_recyclerview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //favoritesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoritesRecyclerView.setHasFixedSize(false);

        ConfigureFavoritesRecyclerView();
    }

    private void checkDbForFavorites() {
        if(checkUserLoggedIn(currUser)) {
            currUserId = currUser.getUid();
            currFavs = FirebaseDatabase.getInstance().getReference("Users").child(currUserId).child("Favorites");
        }
    }

    private boolean checkUserLoggedIn(FirebaseUser user) {
        if(user != null) return true;
        return false;
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

                        TVShowModel tvShowModel = new TVShowModel();
                        tvShowModel.setId(ds.child("tv_id").getValue(Integer.class));
                        tvShowModel.setName(ds.child("name").getValue(String.class));
                        tvShowModel.setPoster_path(ds.child("poster_path").getValue(String.class));
                        tvShowModel.setBackdrop_path(ds.child("backdrop_path").getValue(String.class));

                        favList.add(tvShowModel);
                    }
                    favoritesRecyclerViewAdapter = new FavoritesAdapter(FavoritesFragment.this, getContext(), favList);
                    favoritesRecyclerView.setAdapter(favoritesRecyclerViewAdapter);
                    favoritesRecyclerViewAdapter.notifyDataSetChanged();
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
                .replace(R.id.fragmentFrameLayout, showDetailFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("showInfo", favoritesRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);

    }

    @Override
    public void onWatchlistClick(int adapterPosition) {

    }
}