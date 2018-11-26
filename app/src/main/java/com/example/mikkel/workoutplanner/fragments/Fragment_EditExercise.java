package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.utils.MathUtils;
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

    private String routineUId;
    private int savedMenu;
    private Exercise currentExercise;

    public Exercise getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(Exercise currentExercise) {
        this.currentExercise = currentExercise;
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

        final View view = inflater.inflate(R.layout.fragment_edit_exercise,container,false);

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

        if(currentExercise == null)
            applyNewExercise(view);
        else
            applyOldExercise(view);

        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TextInputEditText first = view.findViewById(R.id.firstSetElement);
                TextInputEditText second = view.findViewById(R.id.secondSetElement);
                TextInputEditText third = view.findViewById(R.id.thirdSetElement);

                first.setText(null);
                second.setText(null);
                third.setText(null);
                applySetHints(view,checkedId);
            }
        });

        return view;
    }

    private void applySetHints(View view, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.WeightItem:
                currentExercise.setType(ExerciseType.Weight);
                break;
            case R.id.TimeItem:
                currentExercise.setType(ExerciseType.Time);
                break;
        }

        TextInputEditText first = view.findViewById(R.id.firstSetElement);
        TextInputEditText second = view.findViewById(R.id.secondSetElement);
        TextInputEditText third = view.findViewById(R.id.thirdSetElement);

        TextInputLayout firstLayout = view.findViewById(R.id.firstSetElementLayout);
        TextInputLayout secondLayout = view.findViewById(R.id.secondSetElementLayout);
        TextInputLayout thirdLayout = view.findViewById(R.id.thirdSetElementLayout);


        String firstHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthHint) :
                getResources().getString(R.string.editExerciseTimeHint);
        String secondHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthSecondHint) :
                getResources().getString(R.string.editExerciseTimeSecondHint);
        String thirdHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthThirdHint) :
                getResources().getString(R.string.editExerciseTimeThirdHint);


        switch (checkedId)
        {
            case R.id.WeightItem:
                first.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                second.setInputType(InputType.TYPE_CLASS_NUMBER);
                third.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case R.id.TimeItem:
                first.setInputType(InputType.TYPE_CLASS_NUMBER);
                second.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                third.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
        }
        firstLayout.setHint(firstHint);
        secondLayout.setHint(secondHint);
        thirdLayout.setHint(thirdHint);
    }

    private void applyNewExercise(View view)
    {
        currentExercise = new Exercise();
        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);
        radioGroup.check(R.id.WeightItem);
        currentExercise.setType(ExerciseType.Weight);
        applySetHints(view,radioGroup.getCheckedRadioButtonId());
    }

    private void applyOldExercise(View view)
    {
        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);
        switch (currentExercise.getType())
        {
            case Weight:
                radioGroup.check(R.id.WeightItem);
                break;
            case Time:
                radioGroup.check(R.id.TimeItem);
                break;
        }
        applySetHints(view,radioGroup.getCheckedRadioButtonId());

        TextInputEditText name = view.findViewById(R.id.NameEditText);
        AutoCompleteTextView muscle = view.findViewById(R.id.MuscleAutoComplete);
        TextInputEditText first = view.findViewById(R.id.firstSetElement);
        TextInputEditText second = view.findViewById(R.id.secondSetElement);
        TextInputEditText third = view.findViewById(R.id.thirdSetElement);

        String firstText = String.valueOf(currentExercise.getType() == ExerciseType.Weight ?
                currentExercise.getSets()
                : currentExercise.getReps());


        //Set name
        name.setText(currentExercise.getName());

        //Set muscle
        muscle.setText(currentExercise.getMuscle());

        //Set first element value
        first.setText(firstText);

        //set second element value
        second.setText(currentExercise.getType() == ExerciseType.Weight ?
                String.valueOf(currentExercise.getReps())
                : Float.toString(currentExercise.getTime()));

        //set second element value
        third.setText(currentExercise.getType() == ExerciseType.Weight ?
                String.valueOf(currentExercise.getKg())
                : Float.toString(currentExercise.getKm()));
    }


    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle(currentExercise != null && currentExercise.getuId() != null ?
                "Edit exercise" : "Add exercise");
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
        if(savedMenu != 0)
            MainActivity.Activity.get_state().setMenuId(savedMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_exercise:

                String name = ((TextInputEditText)getView().findViewById(R.id.NameEditText)).getText().toString();
                String muscle = ((AutoCompleteTextView)getView().findViewById(R.id.MuscleAutoComplete)).getText().toString();
                TextInputEditText first = getView().findViewById(R.id.firstSetElement);
                TextInputEditText second = getView().findViewById(R.id.secondSetElement);
                TextInputEditText third = getView().findViewById(R.id.thirdSetElement);

                currentExercise.setName(name);
                currentExercise.setMuscle(muscle);
                currentExercise.setRoutineUId(getRoutineUId());

                int sets = 0;
                int reps = 0;
                float kg = 0;
                float time = 0;
                float km = 0;

                String firstText = first.getText().toString();
                String secondText = second.getText().toString();
                String thirdText = third.getText().toString();

                if(currentExercise.getType() == ExerciseType.Weight)
                {
                    sets = firstText.isEmpty() ? 0 : Integer.parseInt(first.getText().toString());
                    reps = secondText.isEmpty() ? 0 : Integer.parseInt(second.getText().toString());
                    kg = thirdText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(thirdText),2);
                    currentExercise.setSets(sets);
                    currentExercise.setReps(reps);
                    currentExercise.setKg(kg);

                    if(!currentExercise.weigthValid())
                        return super.onOptionsItemSelected(item);
                }
                else if(currentExercise.getType() == ExerciseType.Time)
                {
                    reps = firstText.isEmpty() ? 0 : Integer.parseInt(first.getText().toString());
                    time = secondText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(secondText),2);
                    km = thirdText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(thirdText),2);
                    currentExercise.setReps(reps);
                    currentExercise.setTime(time);
                    currentExercise.setKm(km);

                    if(!currentExercise.timeValid())
                        return super.onOptionsItemSelected(item);
                }

                if(currentExercise != null && currentExercise.valid())
                {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    String uId = DataManager.getInstance().get_user().getUid();

                    if(currentExercise.getuId() == null)
                    {
                        DatabaseReference ref = database.child(DataManager.EXERCISES_PATH_ID).child(uId).
                                child(getRoutineUId()).push();
                        currentExercise.setuId(ref.getKey());
                        ref.setValue(currentExercise);
                    }
                    else
                    {
                        database.child(DataManager.EXERCISES_PATH_ID).child(uId).
                                child(getRoutineUId()).child(currentExercise.getuId()).
                                setValue(currentExercise);
                    }
                }

                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
