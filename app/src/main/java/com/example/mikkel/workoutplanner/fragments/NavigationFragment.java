package com.example.mikkel.workoutplanner.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;

public class NavigationFragment extends Fragment {

    protected Toolbar toolbar;

    @Override
    public void onStart() {
        super.onStart();
        onCreateNavigation();
    }

    protected void onCreateNavigation()
    {
        View view = getView();
        if(view == null)
            return;
        toolbar = view.findViewById(R.id.toolbar);
        if(toolbar != null && view != null)
        {
            MainActivity.Activity.setSupportActionBar(toolbar);
            setToolbarTitle(getResources().getString(R.string.app_name));
        }
        setupBottomNavigation(View.VISIBLE);
        setupActioMenu(true);
    }

    protected void setToolbarTitle(String title)
    {
        if(toolbar != null)
        {
            toolbar.setTitle(title);
        }
    }

    protected void setupBottomNavigation(int visibility)
    {
        MainActivity.Activity.setBottomNavigationVisibility(visibility);
    }

    protected void setupActioMenu(boolean visible)
    {
        MainActivity.Activity.setActionMenuVisibility(visible);
    }


}
