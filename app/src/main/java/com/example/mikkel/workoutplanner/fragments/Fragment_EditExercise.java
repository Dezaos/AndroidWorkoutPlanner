package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.mikkel.workoutplanner.R;

import java.util.Arrays;

public class Fragment_EditExercise extends NavigationFragment {

    private String[] _muscleSuggestions = new String[]
            {
                "Shoulders", "Forearm", "Biceps", "Chest", "Quads", "Abs",
                "Abductors", "Obliques", "Traps", "Lats", "Triceps", "Lower back",
                "Glutes", "Hamstrings", "Calves", "Cardio"
            };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_exercise,container,false);

        Arrays.sort(_muscleSuggestions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(),
                android.R.layout.simple_dropdown_item_1line,_muscleSuggestions);
        AutoCompleteTextView autoComplete = view.findViewById(R.id.MuscleEditText);
        autoComplete.setAdapter(adapter);

        view.findViewById(R.id.MuscleEditText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AutoCompleteTextView)view).showDropDown();
            }
        });


        return view;
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Edit exercise");
        setupBottomNavigation(View.GONE);
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(R.drawable.back_white);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });
        }
    }
}
