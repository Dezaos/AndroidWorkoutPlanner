package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.StateData.StateData;
import com.example.mikkel.workoutplanner.singletons.StateManager;
import com.example.mikkel.workoutplanner.utils.StateHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This is a super class for the fragment that want to use my state system
 */
public class NavigationFragment extends Fragment{

    private final String STATEMESSAGE= "SaveState";

    protected Toolbar toolbar;

    protected<T extends StateData> T getState(Class<T> type)
    {
        StateData state = StateManager.getInstance().getStateHandler().getState(this);
        if(type.isInstance(state))
            return (T)state;
        return null;
    }

    /**
     * This will always return a state, because if no state is presen for the stateholder,
     *  then a state will be created
     * @param type
     * @param <T>
     * @return
     */
    protected <T extends StateData> T getSafeState(Class<T> type)
    {
        if(!statePresent()) {
            try {
                addState(type.newInstance());
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return getState(type);
    }

    protected <T extends StateData> T addState(T state)
    {
        StateManager.getInstance().getStateHandler().addState(this,state);
        return state;
    }

    /**
     * Check if a state for the state holder is present
     * @return
     */
    protected boolean statePresent()
    {
        return StateManager.getInstance().getStateHandler().contains(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        onCreateNavigation();
    }

    /**
     * This is a default setup for the fragment, this can be overrided
     */
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

    @Override
    public void onStop() {
        super.onStop();
        if(!isAlive())
        {
            onSafeStop();
        }
    }

    /**
     * Call this to clean the state
     * @param fragment
     * @param <T>
     */
    protected <T extends NavigationFragment>void stateCleanup(NavigationFragment fragment)
    {
        StateHandler stateHandler = StateManager.getInstance().getStateHandler();
        stateHandler.removeState(fragment);
    }

    /**
     * This is called if a fragment is killed and not used anymore
     */
    protected void onSafeStop()
    {
        stateCleanup(this);
    }

    /**
     * This is to check if the fragment is still alive or is present in a fragment manager
     * @return
     */
    protected boolean isAlive()
    {
        return !(this.isRemoving() || this.getActivity() == null || this.isDetached()
                || !this.isAdded() || this.getView() == null);
    }

    /**
     * This saves the hashcode for its state
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATEMESSAGE,hashCode());
    }

    @Override
    public void setInitialSavedState(@Nullable SavedState state) {
        super.setInitialSavedState(state);
    }

    /**
     * The class uses the save bundle to check if the old saved instance had a state and if it has,
     * then get that state and make it owner this new fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null)
            return;

        StateHandler stateHandler = StateManager.getInstance().getStateHandler();

        Object oldKey = stateHandler.getKey(savedInstanceState.getInt(STATEMESSAGE));
        if(oldKey != null)
        {
            StateData state = stateHandler.getState(oldKey);
            stateHandler.removeState(oldKey);
            stateHandler.addState(this,state);
        }

    }
}
