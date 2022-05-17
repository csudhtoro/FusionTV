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

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.example.fusiontv.adapters.SeasonAdapter;
import com.example.fusiontv.adapters.SimilarAdapter;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Genre;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.viewmodels.ShowListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ShowDetailFragment extends Fragment implements OnShowListener {

    FirebaseUser currUser;
    String currUserId;
    DatabaseReference db;

    //SIMILAR
    RecyclerView similarRecyclerView;
    private SimilarAdapter showSimilarRecyclerViewAdapter;

    //RECOMMENDED
    RecyclerView recommendationRecyclerView;
    private RecommendationAdapter showRecommendedRecyclerViewAdapter;

    //CAST
    RecyclerView castRecyclerView;
    private CastAdapter showCastRecyclerViewAdapter;

    //IMAGES
    RecyclerView imageRecyclerView;
    private BackdropAdapter showImageRecyclerViewAdapter;


    RecyclerView screenshotRecyclerView;
    RecyclerView genreRecyclerView;
    RecyclerView seasonRecyclerView;

    //Widgets
    private ImageView posterImage, backdropImage, favoriteIcon, backArrow, scheduleIcon, networkLogo;
    private TextView showOverview, firstAirDate, lastAirDate, nextAirDate, network, runtime, voteAvg, season, episodes, appBarShowName, showName;
    private RatingBar showRatingBar;

    private ShowListViewModel showListViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail2, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showListViewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

            castRecyclerView = (RecyclerView) getView().findViewById(R.id.cast_recyclerview);
            similarRecyclerView = (RecyclerView) getView().findViewById(R.id.similar_shows_recyclerview);
            screenshotRecyclerView = (RecyclerView) getView().findViewById(R.id.screenshots_recyclerview);
            recommendationRecyclerView = (RecyclerView) getView().findViewById(R.id.recommendations_recyclerview);
            genreRecyclerView = (RecyclerView) getView().findViewById(R.id.genre_recyclerview);
            seasonRecyclerView = (RecyclerView) getView().findViewById(R.id.seasons_recyclerview);

            posterImage = (ImageView) getView().findViewById(R.id.showDetailsPoster);
            backdropImage = (ImageView) getView().findViewById(R.id.showDetailsBackdrop);
            showOverview = (TextView) getView().findViewById(R.id.showDetailsoverview);
            firstAirDate = (TextView) getView().findViewById(R.id.firstAirDate);
            //network = (TextView) getView().findViewById(R.id.network);
            networkLogo = (ImageView) getView().findViewById(R.id.network);
            voteAvg = (TextView) getView().findViewById(R.id.rating);
            showRatingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
            appBarShowName = (TextView) getView().findViewById(R.id.appBar_show_name);
            showName = (TextView) getView().findViewById(R.id.show_name);
            favoriteIcon = (ImageView) getView().findViewById(R.id.favorite_icon);
            scheduleIcon = (ImageView) getView().findViewById(R.id.schedule_icon);
            backArrow = (ImageView) getView().findViewById(R.id.back_arrow);
            //season = (TextView) getView().findViewById(R.id.seasons);
            //episodes = (TextView) getView().findViewById(R.id.episodes);
            //runtime = (TextView) getView().findViewById(R.id.runtime);
            //lastAirDate = (TextView) getView().findViewById(R.id.lastAirDate);
            //nextAirDate = (TextView) getView().findViewById(R.id.nextAirDate);
            currUser = FirebaseAuth.getInstance().getCurrentUser();


            //LOAD SHOW DATA BASED ON INTENT FROM OTHER FRAGMENTS/ACTIVITIES
            assert getArguments() != null;

            TVShowModel tvShowModel = getArguments().getParcelable("showInfo");
            int showId = tvShowModel.getTv_id();


            //SHOW DETAILS
            showListViewModel.searchShowDetails(showId);
            ObserveShowDetailShowChange();

            //CAST
            showListViewModel.searchShowCast(showId);
            ObserveShowCastChange();
            PutCastDataIntoRecyclerView();

            //SIMILAR
            showListViewModel.searchShowSimilar(showId, 1);
            PutSimilarDataIntoRecyclerView();
            ObserveShowSimilarChange();

            //RECOMMENDED
            showListViewModel.searchShowRecommended(showId, 1);
            PutRecommendationDataIntoRecyclerView();
            ObserveShowRecommendedChange();

            //IMAGES
            showListViewModel.searchShowImages(showId);
            PutImageDataIntoRecyclerView();
            ObserveShowImageChange();

            CheckFavorites(tvShowModel);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                fm.popBackStack();

                //Toast.makeText(getContext(), "back arrow clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //RECYCLERVIEW CODE
    private void PutSimilarDataIntoRecyclerView() {
        showSimilarRecyclerViewAdapter = new SimilarAdapter(this);

        similarRecyclerView.setAdapter(showSimilarRecyclerViewAdapter);
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));

        //RecyclerView Pagination
        //Loading next page of api response
        similarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchSimilarNextPage();
                }
            }
        });
    }
    private void PutCastDataIntoRecyclerView() {
        showCastRecyclerViewAdapter = new CastAdapter(this);

        castRecyclerView.setAdapter(showCastRecyclerViewAdapter);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));

    }
    private void PutRecommendationDataIntoRecyclerView() {
        showRecommendedRecyclerViewAdapter = new RecommendationAdapter(this);

        recommendationRecyclerView.setAdapter(showRecommendedRecyclerViewAdapter);
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));


        //RecyclerView Pagination
        //Loading next page of api response
        recommendationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)) {
                    //Here we need to display the next result on the next page of api
                    //showListViewModel.searchNextPage();
                    showListViewModel.searchRecommendedNextPage();
                }
            }
        });
    }
    private void PutImageDataIntoRecyclerView() {
        showImageRecyclerViewAdapter = new BackdropAdapter(this);

        screenshotRecyclerView.setAdapter(showImageRecyclerViewAdapter);
        screenshotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));

    }
    private void PutGenreDataIntoRecyclerView(List<Genre> genreList) {
        GenreAdapter genreAdapter = new GenreAdapter(getContext(), genreList);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false));
        genreRecyclerView.setAdapter(genreAdapter);
    }
    private void PutSeasonDataIntoRecyclerView(List<Season> seasonList, int showId) {
        SeasonAdapter  seasonAdapter = new SeasonAdapter(getContext(), seasonList, new SeasonAdapter.SeasonClickListener() {
            @Override
            public void onItemClick(Season season) {
                SeasonDetailFragment seasonDetailFragment = new SeasonDetailFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_up,
                                R.anim.fragment_slide_down,
                                R.anim.fragment_slide_up,
                                R.anim.fragment_slide_down
                        )
                        .replace(R.id.fragmentFrameLayout, seasonDetailFragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("seasonInfo", season);
                bundle.putInt("showId", showId);
                seasonDetailFragment.setArguments(bundle);
            }
        });
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        seasonRecyclerView.setAdapter(seasonAdapter);

    }


    //Observer for LiveData - ShowDetails
    private void ObserveShowDetailShowChange() {
        showListViewModel.getShowDetail().observe(getViewLifecycleOwner(), new Observer<ShowDetailModel>() {
            @Override
            public void onChanged(ShowDetailModel showDetailModel) {
                if (showDetailModel != null) {
                    showName.setText(showDetailModel.getName());
                    Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500" +showDetailModel.getPosterPath()).apply(new RequestOptions().transform(new RoundedCorners(60)))
                            .into(posterImage);
                    Glide.with(getContext()).load("https://image.tmdb.org/t/p/w780" +showDetailModel.getBackdropPath())
                            .into(backdropImage);
                    showName.setText(showDetailModel.getName());
                    showOverview.setText(showDetailModel.getOverview());
                    appBarShowName.setText(showDetailModel.getName());
                    voteAvg.setText(String.valueOf(showDetailModel.getVoteAverage()));
                    showRatingBar.setRating((showDetailModel.getVoteAverage()) / 2);
                    //if(showDetailModel.getNetworks() != null && showDetailModel.getNetworks().size() > 0) network.setText(showDetailModel.getNetworks().get(0).getName());
                    if(showDetailModel.getNetworks() != null && showDetailModel.getNetworks().size() > 0) {
                        Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500" +showDetailModel.getNetworks().get(0).getLogoPath())
                                .into(networkLogo);
                    }
                    firstAirDate.setText(returnYear(showDetailModel.getFirstAirDate()));

                    PutGenreDataIntoRecyclerView(showDetailModel.getGenres());
                    PutSeasonDataIntoRecyclerView(showDetailModel.getSeasons(),showDetailModel.getId());
                }
            }
        });
    }
    //Observer for LiveData - Cast
    private void ObserveShowCastChange() {
        showListViewModel.getCast().observe(getViewLifecycleOwner(), new Observer<List<Cast>>() {
            @Override
            public void onChanged(List<Cast> casts) {
                showCastRecyclerViewAdapter.setmShows(casts);
            }
        });
    }
    //Observer for LiveData - Similar
    private void ObserveShowSimilarChange() {
        showListViewModel.getShowsSimilar().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                showSimilarRecyclerViewAdapter.setmShows(tvShowModels);
            }
        });
    }
    //Observer for LiveData - Recommended
    private void ObserveShowRecommendedChange() {
        showListViewModel.getShowsRecommended().observe(getViewLifecycleOwner(), new Observer<List<TVShowModel>>() {
            @Override
            public void onChanged(List<TVShowModel> tvShowModels) {
                showRecommendedRecyclerViewAdapter.setmShows(tvShowModels);
            }
        });
    }
    //Observer for LiveData - Images
    private void ObserveShowImageChange() {
        showListViewModel.getShowImages().observe(getViewLifecycleOwner(), new Observer<List<Backdrop>>() {
            @Override
            public void onChanged(List<Backdrop> images) {
                showImageRecyclerViewAdapter.setmShows(images);
            }
        });
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
                        if(snapshot.getKey().equals(String.valueOf(tvShowModel.getTv_id()))) {
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
    private String returnYear(String inDate) {
        SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outSDF = new SimpleDateFormat("(yyyy)");

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
    public void onShowClick(int position) {}
    @Override
    public void onGenreClick(String Genre) {}
    @Override
    public void onShowAiringTodayClick(int position) {}
    @Override
    public void onShowTrendingTodayClick(int position) {}
    @Override
    public void onShowSearchClick(int position) {}
    @Override
    public void onFavoritesClick(int position) {}
    @Override
    public void onWatchlistClick(int adapterPosition) {}

    @Override
    public void onSeasonClick(int position) {

    }

    @Override
    public void onShowSimilarClick(int position) {
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
        bundle.putParcelable("showInfo", showSimilarRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);
    }

    @Override
    public void onShowRecommendedClick(int position) {
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
        bundle.putParcelable("showInfo", showRecommendedRecyclerViewAdapter.getSelectedShow(position));
        showDetailFragment.setArguments(bundle);
    }

    @Override
    public void onShowCastClick(int position) {
        CastProfileFragment castProfileFragment = new CastProfileFragment();
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down,
                        R.anim.fragment_slide_up,
                        R.anim.fragment_slide_down
                )
                .replace(R.id.fragmentFrameLayout, castProfileFragment)
                .addToBackStack(DashboardFragment.class.getName())
                .commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("show2", showCastRecyclerViewAdapter.getSelectedShow(position));
        castProfileFragment.setArguments(bundle);
    }

    @Override
    public void onShowBackdropClick(int position) {
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
        bundle.putString("image", showImageRecyclerViewAdapter.getSelectedShow(position).getFilePath());
        enlargeImageFragment.setArguments(bundle);
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