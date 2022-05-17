package com.example.fusiontv.utils;

import com.example.fusiontv.models.Actor;
import com.example.fusiontv.models.ActorProfile;
import com.example.fusiontv.models.Season;
import com.example.fusiontv.models.SeasonDetail;
import com.example.fusiontv.models.ShowDetailModel;
import com.example.fusiontv.models.TVCredits;
import com.example.fusiontv.models.TVShowModel;
import com.example.fusiontv.response.CastResponse;
import com.example.fusiontv.response.BackdropResponse;
import com.example.fusiontv.response.TVShowSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*ADDING NEW API SEARCHES
-First, Update this file first with the correct url path (below),
-Second, update "ShowApiClient" file (Add to constructor, create LiveData get method, create searchShowApi method, create RetrieveShowRunnable method),
-Third, update "Show Repository" file (Create live data getter, search method and search next page method),
-Fourth, update "ShowListViewModel" file (Create live data getter and search method),
-Fifth, use the search method created in the last step in the main activity (TVShowDashboardActivity). (In main activity, create a boolean, create an Observe changes method,
create search method, create actual query),
-Sixth, create an adapter for the recyclerview to display the correct item in the relevant recyclerview created below
-Seventh, create a recyclerView to display it in on the main activity (TVShowDashboardActivity)???
-   To setup the onclick correctly
        -Interface -> OnShowListener: to create onClick for relevant api. ex: "onShowAiringTodayClick(int position)".
        Once added, the other method will bark to implement the method
        -Then create a new ShowViewHolder class for the relevant api. ex:ShowAiringTodayViewHolder, then add new onShow""Listener to relevant
        areas in the relevant api recyclerview.java*/

public interface TVApi {

    //Search for tv shows - https://api.themoviedb.org/3/search/tv?api_key=<<api_key>>&query=<<tv_show_name>>&page=1
    @GET("/3/search/tv")
    Call<TVShowSearchResponse> searchShow(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    //Get popular tv shows - https://api.themoviedb.org/3/tv/popular?api_key=<<api_key>>&language=en-US&page=1
    @GET("/3/tv/popular")
    Call<TVShowSearchResponse> getPopularShows(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Get trending tv shows - https://api.themoviedb.org/3/trending/tv/day?api_key=5711ccb17a8987bca87b6e6fd7dc4823
    @GET("/3/trending/tv/day")
    Call<TVShowSearchResponse> getTrendingShows(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Get airing today tv shows - https://api.themoviedb.org/3/tv/airing_today?api_key=<<api_key>>&language=en-US&page=1
    @GET("/3/tv/airing_today")
    Call<TVShowSearchResponse> getAiringToday(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Get specific show details - https://api.themoviedb.org/3/tv/{tv_id}?api_key=<<api_key>>&language=en-US
    @GET("/3/tv/{tv_id}?")
    Call<ShowDetailModel> getShowDetails(
            @Path("tv_id") int id,
            @Query("api_key") String key
    );

    //Get specific cast - https://api.themoviedb.org/3/tv/{tv_id}/credits?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US
    @GET("/3/tv/{tv_id}/credits?")
    Call<CastResponse> searchCast(
            @Path("tv_id") int id,
            @Query("api_key") String key
    );

    //Get similar shows to a specific show - https://api.themoviedb.org/3/tv/{tv_id}/similar?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US&page=1
    @GET("/3/tv/{tv_id}/similar?")
    Call<TVShowSearchResponse> searchSimilar(
            @Path("tv_id") int id,
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Get images for a specific show - https://api.themoviedb.org/3/tv/92749/images?api_key=5711ccb17a8987bca87b6e6fd7dc4823
    @GET("https://api.themoviedb.org/3/tv/{tv_id}/images?")
    Call<BackdropResponse> searchImages(
            @Path("tv_id") int id,
            @Query("api_key") String key

    );

    //Get similar shows to a specific show - https://api.themoviedb.org/3/tv/{tv_id}/recommendations?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US&page=1
    @GET("/3/tv/{tv_id}/recommendations?")
    Call<TVShowSearchResponse> searchRecommendations(
            @Path("tv_id") int id,
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Get actors information - https://api.themoviedb.org/3/person/{person_id}?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US
    @GET("/3/person/{person_id}?")
    Call<Actor> searchActor(
            @Path("person_id") int id,
            @Query("api_key") String key
    );

    //Get show season details information - https://api.themoviedb.org/3/tv/{tv_id}/season/{season_number}?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US
    @GET("https://api.themoviedb.org/3/tv/{tv_id}/season/{season_number}?")
    Call<SeasonDetail> searchSeason(
            @Path("tv_id") int id,
            @Path("season_number") int seasonNum,
            @Query("api_key") String key
    );

    //Get specific actor images - https://api.themoviedb.org/3/person/{person_id}/images?api_key=5711ccb17a8987bca87b6e6fd7dc4823
    @GET("/3/person/{person_id}/images?")
    Call<ActorProfile> searchActorImages(
            @Path("person_id") int id,
            @Query("api_key") String key
    );

    //Get specific actor tv credits - https://api.themoviedb.org/3/person/{person_id}/tv_credits?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US
    @GET("/3/person/{person_id}/tv_credits?")
    Call<TVCredits> searchActorCredits(
            @Path("person_id") int id,
            @Query("api_key") String key
    );

    //Get specific season details - https://api.themoviedb.org/3/tv/{tv_id}/season/{season_number}?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US
    @GET("/3/tv/{tv_id}/season/{season_number}?")
    Call<Season> searchSeasonDetails(
            @Path("tv_id") int tvId,
            @Path("season_number") int seasonNum,
            @Query("api_key") String key
    );
    //Get tv show list by genre - https://api.themoviedb.org/3/discover/tv?api_key=5711ccb17a8987bca87b6e6fd7dc4823&language=en-US&with_genres=37
    @GET("/3/discover/tv?")
    Call<TVShowSearchResponse> searchByGenre(
            @Query("with_genres") int id,
            @Query("page") int page,
            @Query("api_key") String key
    );
}
