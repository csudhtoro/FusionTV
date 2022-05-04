package com.example.fusiontv.requests;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fusiontv.AppExecutors;
import com.example.fusiontv.models.TVShowModel;
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


}
