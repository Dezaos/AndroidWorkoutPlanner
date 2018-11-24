package com.example.mikkel.workoutplanner.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Pair;

import com.example.mikkel.workoutplanner.utils.TabInfo;

import java.util.ArrayList;

public class TabsAdapter extends FragmentStatePagerAdapter{
    private final ArrayList<Pair<Fragment, TabInfo>> fragments = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment fragment, TabInfo tabInfo) {
        fragments.add(new Pair<>(fragment, tabInfo));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).first;
    }

    public TabInfo getInfo(int position)
    {
        return fragments.get(position).second;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).second.getName();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void clear(){fragments.clear();}

}

