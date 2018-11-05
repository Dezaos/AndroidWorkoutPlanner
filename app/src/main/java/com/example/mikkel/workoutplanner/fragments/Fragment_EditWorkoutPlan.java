package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.Singletons.FragmentTransitionManager;

public class Fragment_EditWorkoutPlan extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_workoutplan,container,false);

        view.findViewById(R.id.EditPlanCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransitionManager.getInstance().InitializePreviousFragment();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
