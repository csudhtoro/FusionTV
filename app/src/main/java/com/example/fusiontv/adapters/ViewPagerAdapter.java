package com.example.fusiontv.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fusiontv.ActionAdventureFragment;
import com.example.fusiontv.AnimationFragment;
import com.example.fusiontv.ComedyFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ActionAdventureFragment();
            case 1:
                return new AnimationFragment();
            case 2:
                return new ComedyFragment();
            default:
                return new ActionAdventureFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 16;
    }
}
