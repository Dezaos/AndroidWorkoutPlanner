package com.example.mikkel.workoutplanner.singletons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.mikkel.workoutplanner.R;

import java.util.ArrayList;

public class FragmentTransitionManager {
    private static final FragmentTransitionManager ourInstance = new FragmentTransitionManager();
    public static FragmentTransitionManager getInstance() {
        return ourInstance;
    }

    private FragmentTransaction _currentFragmentTransition;

    public FragmentTransaction get_currentFragmentTransition() {
        return _currentFragmentTransition;
    }

    private FragmentTransitionManager() {
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, boolean clear)
    {
        initializeFragment(activity,fragment,clear,-1,-1,-1,-1-1);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, boolean clear, int frameId)
    {
        initializeFragment(activity,fragment,clear,-1,-1,-1,-1,frameId);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, boolean clear, int animationIn, int animationOut, int backstackAnimationIn, int backStackAnimationOut)
    {
        initializeFragment(activity,fragment,clear,animationIn,animationOut,backstackAnimationIn,backStackAnimationOut,-1);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,boolean clear, int animationIn, int animationOut, int backstackAnimationIn, int backStackAnimationOut, int frameId)
    {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if(clear)
        {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            if(fragmentManager.getBackStackEntryCount() > 0)
            {
                fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }

        if(animationIn != -1 && animationOut != -1 &&
                backstackAnimationIn != -1 && backStackAnimationOut != -1)
        {
            fragmentTransaction.setCustomAnimations(animationIn,animationOut,
                    backstackAnimationIn,backStackAnimationOut);
        }
        int id = frameId == -1 ? R.id.mainFrame : frameId;

        //Replace or add to backstack
        if(!clear)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.commit();

        //Save current Transition
        _currentFragmentTransition = fragmentTransaction;
    }


}
