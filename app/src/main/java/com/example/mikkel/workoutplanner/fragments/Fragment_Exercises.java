package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.WorkoutPlansManager;
import com.example.mikkel.workoutplanner.adapters.ExerciseAdapter;

public class Fragment_Exercises extends Fragment
{
    private ExerciseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises,container,false);

        adapter = new ExerciseAdapter(getActivity(), WorkoutPlansManager.getInstance().getExercises());
        ListView listView = view.findViewById(R.id.workoutListView);
        listView.setAdapter(adapter);

        return view;
    }
}
