package com.example.mikkel.workoutplanner.singletons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.utils.Animation;

import java.util.ArrayList;

//This singleton is used to make transitions to fragments
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
        initializeFragment(activity,fragment,clear,null,-1,null);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, int frameId)
    {
        initializeFragment(activity,fragment,clear,null,frameId,null);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, Animation animation)
    {
        initializeFragment(activity,fragment,clear,animation,-1,null);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, String tag)
    {
        initializeFragment(activity,fragment,clear,null,-1,tag);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, int frameId, String tag)
    {
        initializeFragment(activity,fragment,clear,null,frameId,tag);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, Animation animation, String tag)
    {
        initializeFragment(activity,fragment,clear,animation,-1,tag);
    }

    public void initializeFragment(AppCompatActivity activity, Fragment fragment,
                                   boolean clear, Animation animation, int frameId, String tag)
    {
        //This creates the fragment transition
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        //If clear is true, then clear the backstack
        if(clear)
        {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            if(fragmentManager.getBackStackEntryCount() > 0)
            {
                fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }

        if(animation != null)
            animation.useAnimation(fragmentTransaction);

        //Gets the frame id
        int id = frameId == -1 ? R.id.mainFrame : frameId;

        //If this is not a clear call, then add the new fragment to the backstack
        if(!clear)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.replace(id,fragment,tag);
        fragmentTransaction.commit();

        //Save current Transition
        _currentFragmentTransition = fragmentTransaction;
    }


}
