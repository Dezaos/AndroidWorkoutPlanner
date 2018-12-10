package com.example.mikkel.workoutplanner.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Pair;

import com.example.mikkel.workoutplanner.fragments.Fragment_Exercises;
import com.example.mikkel.workoutplanner.utils.TabInfo;

import java.util.ArrayList;

public class RoutineTabsAdapter extends FragmentPagerAdapter {
    private ArrayList<Pair<Fragment_Exercises, TabInfo>> tabs = new ArrayList<>();

    //Every tabs has a Tabinfo, with the name of the tab and its routine uId
    public ArrayList<Pair<Fragment_Exercises, TabInfo>> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<Pair<Fragment_Exercises, TabInfo>> tabs) {
        this.tabs = tabs;
    }

    public RoutineTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment_Exercises fragment, TabInfo tabInfo) {
        tabs.add(new Pair<>(fragment, tabInfo));
    }

    @Override
    public Fragment_Exercises getItem(int position) {
        return tabs.get(position).first;
    }

    public TabInfo getInfo(int position)
    {
        return tabs.get(position).second;
    }

    public TabInfo getInfo(String uId){
        for (int i = 0; i < tabs.size(); i++) {
            if(uId.equals(tabs.get(i).second.getuId()))
                return tabs.get(i).second;
        }
        return null;
    }

    public int getIndex(String uId){
        for (int i = 0; i < tabs.size(); i++) {
            if(uId.equals(tabs.get(i).second.getuId()))
                return i;
        }
        return -1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).second.getName();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public void clear(){
        tabs.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        for (int i = 0; i < tabs.size(); i++) {
            if(tabs.get(i).first == object)
            {
                return i;
            }
        }
        return POSITION_NONE;
    }




}

