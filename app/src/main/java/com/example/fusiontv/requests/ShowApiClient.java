package com.example.fusiontv.requests;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fusiontv.AppExecutors;
import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Backdrop;
import com.example.fusiontv.models.Cast;
import com.example.fusiontv.models.Profile;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.SeasonDetail;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVCredits;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.response.BackdropResponse;
import com.example.fusiontv.response.CastResponse;
import com.example.fusiontv.response.TVShowSearchResponse;
import com.example.fusiontv.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ShowApiClient {


    private static ShowApiClient instance;

    //LiveData for search tv shows
    private MutableLiveData<List<TVShowModel>> mShows;
    //Global runnable request
    private RetrieveShowsRunnable retrieveShowsRunnable;

    //liveData for popular tv shows
    private MutableLiveData<List<TVShowModel>> mShowsPopular;
    //Popular runnable request
    private RetrieveShowsRunnablePopular retrieveShowsRunnablePopular;

    //liveData for trending tv shows
    private MutableLiveData<List<TVShowModel>> mShowsTrending;
    //Popular runnable request
    private RetrieveShowsRunnableTrending retrieveShowsRunnableTrending;

    //liveData for airing today shows
    private MutableLiveData<List<TVShowModel>> mShowsAiringToday;
    //Popular runnable request
    private RetrieveShowsRunnableAiringToday retrieveShowsRunnableAiringToday;

    //liveData for show details
    private MutableLiveData<ShowDetailModel> mShowDetail;
    //Show Detail runnable request
    private RetrieveShowRunnableShowDetails retrieveShowRunnableShowDetails;

    //liveData for cast details
    private MutableLiveData<List<Cast>> mCast;
    //Show Detail runnable request
    private RetrieveShowRunnableCast retrieveShowRunnableCast;

    //liveData for similar shows
    private MutableLiveData<List<TVShowModel>> mSimilar;
    //Show Detail runnable request
    private RetrieveShowRunnableSimilar retrieveShowRunnableSimilar;

    //liveData for recommended shows
    private MutableLiveData<List<TVShowModel>> mRecommended;
    //Show Detail runnable request
    private RetrieveShowRunnableRecommended retrieveShowRunnableRecommended;

    //liveData for show images
    private MutableLiveData<List<Backdrop>> mImages;
    //Show Detail runnable request
    private RetrieveShowRunnableShowImages retrieveShowRunnableShowImages;

    //liveData for actor details
    private MutableLiveData<Actor> mActor;
    //Show Detail runnable request
    private RetrieveShowRunnableShowActor retrieveShowRunnableShowActor;

    //liveData for actor images
    private MutableLiveData<List<Profile>> mActorImages;
    //Show Detail runnable request
    private RetrieveShowRunnableActorImages retrieveShowRunnableActorImages;

    //liveData for actor tv credits
    private MutableLiveData<List<TVShowModel>> mActorTVCredits;
    //Show Detail runnable request
    private RetrieveShowRunnableActorTVCredits retrieveShowRunnableActorTVCredits;

    //liveData for season details
    private MutableLiveData<SeasonDetail> mSeasonDetail;
    //Show Detail runnable request
    private RetrieveShowRunnableSeasonDetails retrieveShowRunnableSeasonDetails;

    //LiveData for search tv shows by genre
    private MutableLiveData<List<TVShowModel>> mActionAdventureGenres;
    //Global runnable request
    private RetrieveShowsRunnableActionAdventureGenres retrieveShowsRunnableActionAdventureGenres;

    //LiveData for search tv shows by genre
    private MutableLiveData<List<TVShowModel>> mAnimationGenres;
    //Global runnable request
    private RetrieveShowsRunnableAnimationGenres retrieveShowsRunnableAnimationGenres;





    //Singleton method
    public static ShowApiClient getInstance() {
        if(instance == null) {
            instance = new ShowApiClient();
        }
        return instance;
    }

    //Constructor
    private ShowApiClient() {
        mShows = new MutableLiveData<>();
        mShowsPopular = new MutableLiveData<>();
        mShowsTrending = new MutableLiveData<>();
        mShowsAiringToday = new MutableLiveData<>();
        mShowDetail = new MutableLiveData<>();
        mCast = new MutableLiveData<>();
        mSimilar = new MutableLiveData<>();
        mRecommended = new MutableLiveData<>();
        mImages = new MutableLiveData<>();
        mActor = new MutableLiveData<>();
        mActorImages = new MutableLiveData<>();
        mActorTVCredits = new MutableLiveData<>();
        mSeasonDetail = new MutableLiveData<>();
        mActionAdventureGenres = new MutableLiveData<>();
        mAnimationGenres = new MutableLiveData<>();
    }


    //GETTERS
    public LiveData<List<TVShowModel>> getShows() { return mShows; }
    public LiveData<List<TVShowModel>> getShowsPopular() {
        return mShowsPopular;
    }
    public LiveData<List<TVShowModel>> getShowsTrending() {
        return mShowsTrending;
    }
    public LiveData<List<TVShowModel>> getShowsAiringToday() { return mShowsAiringToday; }
    public LiveData<List<Cast>> getShowCast() { return mCast; }
    public LiveData<List<TVShowModel>> getSimilarShows() { return mSimilar;}
    public LiveData<List<TVShowModel>> getRecommendedShows() { return mRecommended; }
    public LiveData<List<Backdrop>> getShowImages() { return mImages; }
    public LiveData<ShowDetailModel> getShowDetails() { return mShowDetail; }
    public LiveData<SeasonDetail> getSeasonDetails() { return mSeasonDetail; }
    public LiveData<Actor> getActorDetails() { return mActor; }
    public LiveData<List<Profile>> getActorImages() { return mActorImages; }
    public LiveData<List<TVShowModel>> getActorTVCredit() { return mActorTVCredits; }
    public LiveData<List<TVShowModel>> getActionAdventureGenre() { return mActionAdventureGenres; }
    public LiveData<List<TVShowModel>> getAnimationGenre() { return mAnimationGenres; }



    //SEARCH METHODS
    //1 - This method will be called through classes
    public void searchShowsApi(String query, int pageNumber) {

        if(retrieveShowsRunnable != null) {
            retrieveShowsRunnable = null;
        }

        retrieveShowsRunnable = new RetrieveShowsRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //POPULAR
    public void searchShowsApiPopular(int pageNumber) {

        if(retrieveShowsRunnablePopular != null) {
            retrieveShowsRunnablePopular = null;
        }

        retrieveShowsRunnablePopular = new RetrieveShowsRunnablePopular(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnablePopular);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }
    //TRENDING
    public void searchShowsApiTrending(int pageNumber) {

        if(retrieveShowsRunnableTrending != null) {
            retrieveShowsRunnableTrending = null;
        }

        retrieveShowsRunnableTrending = new RetrieveShowsRunnableTrending(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnableTrending);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }
    //AIRING TODAY
    public void searchShowsApiAiringToday(int pageNumber) {

        if(retrieveShowsRunnableAiringToday != null) {
            retrieveShowsRunnableAiringToday = null;
        }

        retrieveShowsRunnableAiringToday = new RetrieveShowsRunnableAiringToday(pageNumber);

        final Future myHandler3 = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnableAiringToday);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler3.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }
    //SHOW DETAILS
    public void searchShowDetails(int id) {
        if(retrieveShowRunnableShowDetails != null) retrieveShowRunnableShowDetails = null;

        retrieveShowRunnableShowDetails = new RetrieveShowRunnableShowDetails(id);
        final Future myHandler4 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableShowDetails);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler4.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);
    }
    //CAST
    public void searchShowsCast(int id) {

        if(retrieveShowRunnableCast != null) {
            retrieveShowRunnableCast = null;
        }

        retrieveShowRunnableCast = new RetrieveShowRunnableCast(id);

        final Future myHandler5 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableCast);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler5.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }
    //SIMILAR
    public void searchSimilar(int id, int pageNumber) {
        if(retrieveShowRunnableSimilar != null) {
            retrieveShowRunnableSimilar = null;
        }

        retrieveShowRunnableSimilar = new RetrieveShowRunnableSimilar(id, pageNumber);

        final Future myHandler6 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableSimilar);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler6.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //RECOMMENDED
    public void searchRecommended(int id, int pageNumber) {
        if(retrieveShowRunnableRecommended != null) {
            retrieveShowRunnableRecommended = null;
        }

        retrieveShowRunnableRecommended = new RetrieveShowRunnableRecommended(id, pageNumber);

        final Future myHandler7 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableRecommended);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler7.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //IMAGES
    public void searchImages(int id) {
        if(retrieveShowRunnableShowImages != null) {
            retrieveShowRunnableShowImages = null;
        }

        retrieveShowRunnableShowImages = new RetrieveShowRunnableShowImages(id);

        final Future myHandler8 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableShowImages);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler8.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //ACTOR DETAILS
    public void searchActorDetails(int id) {
        if(retrieveShowRunnableShowActor != null) retrieveShowRunnableShowActor = null;

        retrieveShowRunnableShowActor = new RetrieveShowRunnableShowActor(id);
        final Future myHandler9 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableShowActor);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler9.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);
    }
    //ACTOR IMAGES
    public void searchActorImages(int id) {
        if(retrieveShowRunnableActorImages != null) {
            retrieveShowRunnableActorImages = null;
        }

        retrieveShowRunnableActorImages = new RetrieveShowRunnableActorImages(id);

        final Future myHandler10 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableActorImages);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler10.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //ACTOR IMAGES
    public void searchActorTVCredits(int id) {
        if(retrieveShowRunnableActorTVCredits != null) {
            retrieveShowRunnableActorTVCredits = null;
        }

        retrieveShowRunnableActorTVCredits = new RetrieveShowRunnableActorTVCredits(id);

        final Future myHandler11 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableActorTVCredits);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler11.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    //SEASON DETAILS
    public void searchSeasonDetails(int id, int num) {
        if(retrieveShowRunnableSeasonDetails != null) retrieveShowRunnableSeasonDetails = null;

        retrieveShowRunnableSeasonDetails = new RetrieveShowRunnableSeasonDetails(id, num);
        final Future myHandler12 = AppExecutors.getInstance().networkIO().submit(retrieveShowRunnableSeasonDetails);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler12.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);
    }

    //GENRES
    public void searchShowsByActionAdventure(int id, int pageNumber) {

        if(retrieveShowsRunnableActionAdventureGenres != null) {
            retrieveShowsRunnableActionAdventureGenres = null;
        }

        retrieveShowsRunnableActionAdventureGenres = new RetrieveShowsRunnableActionAdventureGenres(id, pageNumber);
        final Future myHandler13 = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnableActionAdventureGenres);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler13.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    public void searchShowsByAnimation(int id, int pageNumber) {

        if(retrieveShowsRunnableAnimationGenres != null) {
            retrieveShowsRunnableAnimationGenres = null;
        }

        retrieveShowsRunnableAnimationGenres = new RetrieveShowsRunnableAnimationGenres(id, pageNumber);
        final Future myHandler14 = AppExecutors.getInstance().networkIO().submit(retrieveShowsRunnableAnimationGenres);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call requests
                myHandler14.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }


    //RUNNABLE METHODS
    //Retrieving REST API by runnable class
    private class RetrieveShowsRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

            public RetrieveShowsRunnable(String query, int pageNumber) {
                this.query = query;
                this.pageNumber = pageNumber;
                cancelRequest = false;
            }

            @Override
            public void run() {
                //Getting response objects
                try {
                    Response response = getShows(query, pageNumber).execute();
                    if (cancelRequest) {
                        return;
                    }
                    if(response.code() == 200) {
                        List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response.body()).getShows());
                        if(pageNumber == 1) {
                            //Sending data to live data
                            //PostValue: used for background thread
                            //setValue: not used for background thread

                            mShows.postValue(list);
                        }
                        else {
                            List<TVShowModel> currentShows = mShows.getValue();
                            currentShows.addAll(list);
                            mShows.postValue(currentShows);
                        }
                    }
                    else {
                        String error = response.errorBody().string();
                        Log.v("Tag", "Error: " + error);
                        mShows.postValue(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mShows.postValue(null);
                }

            }
                //Search query in background thread
                private Call<TVShowSearchResponse> getShows(String query, int pageNumber) {
                    return Services.getTvApi().searchShow(
                            Credentials.API_KEY,
                            query,
                            pageNumber
                    );

        }
        private void cancelRequest() {
                Log.v("Tag", "Cancelling Search Request");
                cancelRequest = true;
        }


    }

    //Retrieving REST API Popular by runnable class
    private class RetrieveShowsRunnablePopular implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowsRunnablePopular(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response2 = getPopular(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response2.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response2.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread

                        mShowsPopular.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mShowsPopular.getValue();
                        currentShows.addAll(list);
                        mShowsPopular.postValue(currentShows);
                    }
                }
                else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mShowsPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mShowsPopular.postValue(null);
            }




        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getPopular(int pageNumber) {
            return Services.getTvApi().getPopularShows(
                    Credentials.API_KEY,
                    pageNumber
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Airing Today by runnable class
    private class RetrieveShowsRunnableAiringToday implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowsRunnableAiringToday(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response3 = getAiringToday(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response3.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response3.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread

                        mShowsAiringToday.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mShowsAiringToday.getValue();
                        currentShows.addAll(list);
                        mShowsAiringToday.postValue(currentShows);
                    }
                }
                else {
                    String error = response3.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mShowsAiringToday.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mShowsAiringToday.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getAiringToday(int pageNumber) {
            return Services.getTvApi().getAiringToday(
                    Credentials.API_KEY,
                    pageNumber
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Trending by runnable class
    private class RetrieveShowsRunnableTrending implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowsRunnableTrending(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response4 = getAiringTrending(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response4.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response4.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread

                        mShowsTrending.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mShowsTrending.getValue();
                        currentShows.addAll(list);
                        mShowsTrending.postValue(currentShows);
                    }
                }
                else {
                    String error = response4.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mShowsTrending.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mShowsTrending.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getAiringTrending(int pageNumber) {
            return Services.getTvApi().getTrendingShows(
                    Credentials.API_KEY,
                    pageNumber
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Show Details by runnable class
    private class RetrieveShowRunnableShowDetails implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableShowDetails(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response5 = getShowDetails(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response5.code() == 200) {
                    ShowDetailModel show = ((ShowDetailModel)response5.body());
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread
                        mShowDetail.postValue(show);

                }
                else {
                    String error = response5.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mShowsTrending.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mShowDetail.postValue(null);
            }

        }
        //Search query in background thread
        private Call<ShowDetailModel> getShowDetails(int id) {
            return Services.getTvApi().getShowDetails(
                    id,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Show Cast by runnable class
    private class RetrieveShowRunnableCast implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableCast(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response6 = getShowCast(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response6.code() == 200) {
                    List<Cast> list = new ArrayList<>(((CastResponse)response6.body()).getCast());
                        mCast.postValue(list);
                }
                else {
                    String error = response6.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mShowsTrending.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mShowDetail.postValue(null);
            }

        }
        //Search query in background thread
        private Call<CastResponse> getShowCast(int id) {
            return Services.getTvApi().searchCast(
                    id,
                    Credentials.API_KEY
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Similar by runnable class
    private class RetrieveShowRunnableSimilar implements Runnable {

        private int id;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowRunnableSimilar(int id, int pageNumber) {
            this.id = id;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response7 = getSimilar(id, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response7.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response7.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread

                        mSimilar.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mSimilar.getValue();
                        currentShows.addAll(list);
                        mSimilar.postValue(currentShows);
                    }
                }
                else {
                    String error = response7.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mSimilar.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSimilar.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getSimilar(int id, int pageNumber) {
            return Services.getTvApi().searchSimilar(
                    id,
                    Credentials.API_KEY,
                    pageNumber
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    //Retrieving REST API Recommended by runnable class
    private class RetrieveShowRunnableRecommended implements Runnable {

        private int id;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowRunnableRecommended(int id, int pageNumber) {
            this.id = id;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response8 = getRecommended(id, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response8.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response8.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread

                        mRecommended.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mRecommended.getValue();
                        currentShows.addAll(list);
                        mRecommended.postValue(currentShows);
                    }
                }
                else {
                    String error = response8.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mRecommended.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecommended.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getRecommended(int id, int pageNumber) {
            return Services.getTvApi().searchRecommendations(
                    id,
                    Credentials.API_KEY,
                    pageNumber
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Show Images by runnable class
    private class RetrieveShowRunnableShowImages implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableShowImages(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response9 = getImages(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response9.code() == 200) {
                    List<Backdrop> list = new ArrayList<>(((BackdropResponse)response9.body()).getImages());
                        mImages.postValue(list);
                }
                else {
                    String error = response9.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mImages.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mImages.postValue(null);
            }

        }
        //Search query in background thread
        private Call<BackdropResponse> getImages(int id) {
            return Services.getTvApi().searchImages(
                    id,
                    Credentials.API_KEY
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    //Retrieving REST API Actor Details by runnable class
    private class RetrieveShowRunnableShowActor implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableShowActor(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response10 = getActorDetails(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response10.code() == 200) {
                    Actor actor = ((Actor)response10.body());
                    //Sending data to live data
                    //PostValue: used for background thread
                    //setValue: not used for background thread
                    mActor.postValue(actor);

                }
                else {
                    String error = response10.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mActor.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mActor.postValue(null);
            }

        }
        //Search query in background thread
        private Call<Actor> getActorDetails(int id) {
            return Services.getTvApi().searchActor(
                    id,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Actor Images by runnable class
    private class RetrieveShowRunnableActorImages implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableActorImages(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response11 = getActorImages(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response11.code() == 200) {
                    List<Profile> list = new ArrayList<>(((ActorProfile)response11.body()).getProfiles());
                    mActorImages.postValue(list);
                }
                else {
                    String error = response11.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mActorImages.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mActorImages.postValue(null);
            }

        }
        //Search query in background thread
        private Call<ActorProfile> getActorImages(int id) {
            return Services.getTvApi().searchActorImages(
                    id,
                    Credentials.API_KEY
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    //Retrieving REST API Actor Images by runnable class
    private class RetrieveShowRunnableActorTVCredits implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveShowRunnableActorTVCredits(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response12 = getTVCredits(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response12.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVCredits)response12.body()).getCast());
                    mActorTVCredits.postValue(list);
                }
                else {
                    String error = response12.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mActorTVCredits.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mActorTVCredits.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVCredits> getTVCredits(int id) {
            return Services.getTvApi().searchActorCredits(
                    id,
                    Credentials.API_KEY
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    //Retrieving REST API Season Details by runnable class
    private class RetrieveShowRunnableSeasonDetails implements Runnable {

        private int id;
        private int num;
        boolean cancelRequest;

        public RetrieveShowRunnableSeasonDetails(int id, int num) {
            this.id = id;
            this.num = num;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response13 = getSeasonDetails(id, num).execute();
                if (cancelRequest) {
                    return;
                }
                if(response13.code() == 200) {
                    SeasonDetail season = ((SeasonDetail)response13.body());
                    //Sending data to live data
                    //PostValue: used for background thread
                    //setValue: not used for background thread
                    mSeasonDetail.postValue(season);

                }
                else {
                    String error = response13.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mSeasonDetail.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSeasonDetail.postValue(null);
            }

        }
        //Search query in background thread
        private Call<Season> getSeasonDetails(int id, int num) {
            return Services.getTvApi().searchSeasonDetails(
                    id,
                    num,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Season Details by runnable class
    private class RetrieveShowsRunnableActionAdventureGenres implements Runnable {

        private int id;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowsRunnableActionAdventureGenres(int id, int pageNumber) {
            this.id = id;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response14 = getActionAdventureShows(id, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response14.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response14.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread
                        mActionAdventureGenres.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mActionAdventureGenres.getValue();
                        currentShows.addAll(list);
                        mActionAdventureGenres.postValue(currentShows);
                    }
                }
                else {
                    String error = response14.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mActionAdventureGenres.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mActionAdventureGenres.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getActionAdventureShows(int id, int pageNumber) {
            return Services.getTvApi().searchByGenre(
                    id,
                    pageNumber,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

    //Retrieving REST API Season Details by runnable class
    private class RetrieveShowsRunnableAnimationGenres implements Runnable {

        private int id;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveShowsRunnableAnimationGenres(int id, int pageNumber) {
            this.id = id;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting response objects
            try {
                Response response15 = getAnimationShows(id, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response15.code() == 200) {
                    List<TVShowModel> list = new ArrayList<>(((TVShowSearchResponse)response15.body()).getShows());
                    if(pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not used for background thread
                        mAnimationGenres.postValue(list);
                    }
                    else {
                        List<TVShowModel> currentShows = mAnimationGenres.getValue();
                        currentShows.addAll(list);
                        mAnimationGenres.postValue(currentShows);
                    }
                }
                else {
                    String error = response15.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mAnimationGenres.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mAnimationGenres.postValue(null);
            }

        }
        //Search query in background thread
        private Call<TVShowSearchResponse> getAnimationShows(int id, int pageNumber) {
            return Services.getTvApi().searchByGenre(
                    id,
                    pageNumber,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }

}
