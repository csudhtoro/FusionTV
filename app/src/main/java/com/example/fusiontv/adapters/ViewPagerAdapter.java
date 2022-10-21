package com.example.fusiontv.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fusiontv.fragments.ActionAdventureFragment;
import com.example.fusiontv.fragments.AnimationFragment;
import com.example.fusiontv.fragments.ComedyFragment;
import com.example.fusiontv.fragments.CrimeFragment;
import com.example.fusiontv.fragments.DocumentaryFragment;
import com.example.fusiontv.fragments.DramaFragment;
import com.example.fusiontv.fragments.FamilyFragment;
import com.example.fusiontv.fragments.KidsFragment;
import com.example.fusiontv.fragments.MysteryFragment;
import com.example.fusiontv.fragments.NewsFragment;
import com.example.fusiontv.fragments.RealityFragment;
import com.example.fusiontv.fragments.SciFiFantasyFragment;
import com.example.fusiontv.fragments.SoapFragment;
import com.example.fusiontv.fragments.TalkFragment;
import com.example.fusiontv.fragments.WarPoliticsFragment;
import com.example.fusiontv.fragments.WesternFragment;

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
            case 3:
                return new CrimeFragment();
            case 4:
                return new DocumentaryFragment();
            case 5:
                return new DramaFragment();
            case 6:
                return new FamilyFragment();
            case 7:
                return new KidsFragment();
            case 8:
                return new MysteryFragment();
            case 9:
                return new NewsFragment();
            case 10:
                return new RealityFragment();
            case 11:
                return new SciFiFantasyFragment();
            case 12:
                return new SoapFragment();
            case 13:
                return new TalkFragment();
            case 14:
                return new WarPoliticsFragment();
            case 15:
                return new WesternFragment();
            default:
                return new ActionAdventureFragment();
        }


    }

    @Override
    public int getItemCount() {
        return 16;
    }

}
