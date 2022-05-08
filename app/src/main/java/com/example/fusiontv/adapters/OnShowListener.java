package com.example.fusiontv.adapters;

public interface OnShowListener {

    void onShowClick(int position);

    void onGenreClick(String Genre);

    void onShowAiringTodayClick(int position);

    void onShowTrendingTodayClick(int position);

    void onShowSearchClick(int position);

    void onFavoritesClick(int position);

    void onWatchlistClick(int adapterPosition);
}
