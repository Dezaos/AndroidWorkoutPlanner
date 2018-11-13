package com.example.mikkel.workoutplanner.Singletons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
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
    private ArrayList<FrameLayout> _savedFrames = new ArrayList<>();

    private FragmentTransitionManager() {
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        initializeFragment(activity,fragment,-1,-1,-1);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, int frameId)
    {
        initializeFragment(activity,fragment,-1,-1,frameId);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut)
    {
        initializeFragment(activity,fragment,animationIn,animationOut,-1);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut, int frameId)
    {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if(animationIn != -1 && animationOut != -1)
        {
            fragmentTransaction.setCustomAnimations(animationIn,animationOut,animationIn,animationOut);
        }
        int id = frameId == -1 ? R.id.mainFrame : frameId;
        if(id != R.id.mainFrame)
        {
            FrameLayout frame = activity.findViewById(id);
            if(frame != null && !_savedFrames.contains(frame))
                _savedFrames.add(frame);
        }

                fragmentTransaction.replace(id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        _currentFragmentTransition = fragmentTransaction;
    }


    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment, int frameId)
    {
        clearAndInitializeFragment(activity,fragment,-1,-1,frameId);
    }

    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut)
    {
        clearAndInitializeFragment(activity,fragment,animationIn,animationOut,-1);
    }

    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment, int animationIn, int animationOut,int frameId)
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

        for (int i = 0; i < _savedFrames.size(); i++) {
            _savedFrames.get(i).removeAllViews();
            _savedFrames.get(i).removeAllViewsInLayout();
        }
        _savedFrames.clear();

        int id = frameId == -1 ? R.id.mainFrame : frameId;
        if(id != R.id.mainFrame)
        {
            FrameLayout frame = activity.findViewById(id);
            if(frame != null && !_savedFrames.contains(frame))
                _savedFrames.add(frame);
        }
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.commit();
        _currentFragmentTransition = fragmentTransaction;


    }


    public void clearAndInitializeFragment(AppCompatActivity activity, Fragment fragment)
    {
        clearAndInitializeFragment(activity,fragment,-1,-1,-1);
    }



    public void setCurrentFragmentAnimation(AppCompatActivity activity, int animationIn, int animationOut)
    {
        if(_currentFragmentTransition != null)
            _currentFragmentTransition.setCustomAnimations(animationIn,animationOut,animationIn,animationOut);
    }
}
