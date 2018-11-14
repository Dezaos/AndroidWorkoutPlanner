package com.example.mikkel.workoutplanner.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Pair;

import java.util.ArrayList;

public class TabsAdapter extends FragmentStatePagerAdapter{
    private final ArrayList<Pair<Fragment, String>> fragments = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment fragment, String title) {
        fragments.add(new Pair<>(fragment, title));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).first;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).second;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
