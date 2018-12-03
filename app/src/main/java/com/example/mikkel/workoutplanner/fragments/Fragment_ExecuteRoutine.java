package com.example.mikkel.workoutplanner.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.StateData.ExecuteRoutineFragmentState;

public class Fragment_ExecuteRoutine extends NavigationFragment
{
    public ExecuteRoutineFragmentState getState()
    {
        return getSafeState(ExecuteRoutineFragmentState.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_exexute_routine,container,false);

        return view;
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();

        //Hide the bottom navigation
        setupBottomNavigation(View.GONE);

        if(toolbar != null)
        {
            //This sets the back button icon
            toolbar.setNavigationIcon(R.drawable.back_white);

            //setToolbarTitle();
            setToolbarTitle(getState().getRoutineName());

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });

            //This is to store the old toolbar menu, to apply it after the fragment is done
            //savedMenu = MainActivity.Activity.getState().getMenuId();
            getState().setSavedMenu(MainActivity.Activity.getState().getMenuId());

            //Sets the new toolbar menu
            MainActivity.Activity.getState().setMenuId(R.menu.edit_exercise_menu);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Applies the old toolbar menu
        if(getState().getSavedMenu() != 0)
            MainActivity.Activity.getState().setMenuId(getState().getSavedMenu());
    }

}
