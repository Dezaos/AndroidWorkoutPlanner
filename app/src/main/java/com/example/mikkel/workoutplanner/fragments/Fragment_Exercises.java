package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Exercises extends Fragment
{
    private FirebaseRecyclerOptions adapter;
    private String routineUId;

    public String getRoutineUId() {
        return routineUId;
    }

    public void setRoutineUId(String routineUId) {
        this.routineUId = routineUId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises,container,false);

        FloatingActionButton fab = view.findViewById(R.id.addExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                Fragment_EditExercise editExercise = new Fragment_EditExercise();
                editExercise.setRoutineUId(getRoutineUId());

                FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                        editExercise,false,
                        R.anim.enter_from_right,R.anim.exit_to_left,
                        R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        return view;
    }


}
