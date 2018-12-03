package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.ExecuteRoutineFragmentState;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String uId = DataManager.getInstance().getUser().getUid();
        ArrayList<Routine> routines = DataManager.getInstance().getRoutines();

        ExecuteRoutine newRoutine = new ExecuteRoutine();
        Routine routine = null;

        for (int i = 0; i < routines.size() ; i++) {
            if(routines.get(i).getuId().equals(getState().getRoutineuId()))
            {
                routine = routines.get(i);
                break;
            }
        }

        if(routine != null)
            newRoutine.convert(routine);

        DatabaseReference ref = database.child(DataManager.Current_Execute_Routines_PATH_ID).child(uId);
        newRoutine.setuId(ref.getKey());
        ref.setValue(newRoutine);

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
