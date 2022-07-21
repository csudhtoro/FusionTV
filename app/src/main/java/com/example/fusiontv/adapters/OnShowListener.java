package com.example.fusiontv.adapters;

public interface OnShowListener {

    void onShowClick(int position);

    void onGenreClick(String Genre);

    void onShowAiringTodayClick(int position);

    void onShowTrendingTodayClick(int position);

    void onShowSearchClick(int position);

    void onFavoritesClick(int position);

    void onWatchlistClick(int position);

    void onSeasonClick(int position);

    void onShowSimilarClick(int position);

    void onShowRecommendedClick(int position);

    void onShowCastClick(int position);

    void onShowBackdropClick(int position);

    void onActorTVCreditClick(int position);

    void onShowActorImageClick(int position);

    void onShowGenreClick(int adapterPosition);

    void onFiscalWeekClick(int adapterPosition);

    void onNotificationClick(int adapterPosition);
}
