package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;

public class Fragment_Home extends NavigationFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Home");

        MainActivity.Activity.get_state().setMenuId(R.menu.menu);

    }
}
