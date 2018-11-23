package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;


public class Fragment_EditExercise extends NavigationFragment {

    private String[] _muscleSuggestions = new String[]
            {
                "Shoulders", "Forearm", "Biceps", "Chest", "Quads", "Abs",
                "Abductors", "Obliques", "Traps", "Lats", "Triceps", "Lower back",
                "Glutes", "Hamstrings", "Calves", "Cardio"
            };

    private String uId;
    private String routineUId;
    private int savedMenu;
    private Exercise currentExercise;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRoutineUId() {
        return routineUId;
    }

    public void setRoutineUId(String routineUId) {
        this.routineUId = routineUId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_exercise,container,false);

        Arrays.sort(_muscleSuggestions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(),
                android.R.layout.simple_dropdown_item_1line,_muscleSuggestions);
        AutoCompleteTextView autoComplete = view.findViewById(R.id.MuscleAutoComplete);
        autoComplete.setAdapter(adapter);

        view.findViewById(R.id.MuscleAutoComplete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AutoCompleteTextView)view).showDropDown();
            }
        });

        currentExercise = new Exercise();

        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);
        if(routineUId == null)
            radioGroup.check(0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.WeightItem:
                        currentExercise.setType(ExerciseType.Weight);
                        break;
                    case R.id.TimeItem:
                        currentExercise.setType(ExerciseType.Time);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle(uId != null ? "Edit exercise" : "Add exercise");
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
            savedMenu = MainActivity.Activity.get_state().getMenuId();
            MainActivity.Activity.get_state().setMenuId(R.menu.edit_exercise_menu);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.Activity.get_state().setMenuId(savedMenu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_exercise:

                String name = ((TextInputEditText)getView().findViewById(R.id.NameEditText)).getText().toString();
                String muscle = ((AutoCompleteTextView)getView().findViewById(R.id.MuscleAutoComplete)).getText().toString();

                currentExercise.setName(name);
                currentExercise.setMuscle(muscle);

                if(currentExercise != null && currentExercise.valid())
                {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    String uId = DataManager.getInstance().get_user().getUid();

                        database.child(DataManager.EXERCISES_PATH_ID).child(uId).
                                child(getRoutineUId()).push().setValue(currentExercise);
                }

                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
