package com.example.mikkel.workoutplanner.Singletons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mikkel.workoutplanner.R;

public class FragmentTransitionManager {
    private static final FragmentTransitionManager ourInstance = new FragmentTransitionManager();
    public static FragmentTransitionManager getInstance() {
        return ourInstance;
    }

    private FragmentTransitionManager() {
    }

    public void InitializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        android.support.v4.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void ClearAndInitializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        if(fragmentManager.getBackStackEntryCount() > 0)
        {
            fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();
    }

}
