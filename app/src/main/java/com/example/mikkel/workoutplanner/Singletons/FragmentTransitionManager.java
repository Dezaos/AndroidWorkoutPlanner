package com.example.mikkel.workoutplanner.Singletons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.mikkel.workoutplanner.R;

public class FragmentTransitionManager {
    private static final FragmentTransitionManager ourInstance = new FragmentTransitionManager();
    public static FragmentTransitionManager getInstance() {
        return ourInstance;
    }

    private FragmentTransaction _currentFragmentTransition;

    private FragmentTransitionManager() {
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        initializeFragment(activity,fragment,-1,-1);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut)
    {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if(animationIn != -1 && animationOut != -1)
        {
            fragmentTransaction.setCustomAnimations(animationIn,animationOut,animationIn,animationOut);
        }
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        _currentFragmentTransition = fragmentTransaction;
    }

    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut)
    {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        if(fragmentManager.getBackStackEntryCount() > 0)
        {
            fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(animationIn != -1 && animationOut != -1)
        {
            fragmentTransaction.setCustomAnimations(animationIn,animationOut,animationIn,animationOut);
        }

        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();
        _currentFragmentTransition = fragmentTransaction;
    }

    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        clearAndInitializeFragment(activity,fragment,-1,-1);
    }

    public void setCurrentFragmentAnimation(AppCompatActivity activity, int animationIn, int animationOut)
    {
        if(_currentFragmentTransition != null)
            _currentFragmentTransition.setCustomAnimations(animationIn,animationOut,animationIn,animationOut);
    }
}
