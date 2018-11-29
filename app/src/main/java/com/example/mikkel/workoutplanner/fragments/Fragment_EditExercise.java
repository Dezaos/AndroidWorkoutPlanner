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

    //Fields
    private String routineUId;

    //This array is used for the muscle auto complete
    private String[] _muscleSuggestions = new String[]
            {
                "Shoulders", "Forearm", "Biceps", "Chest", "Quads", "Abs",
                "Abductors", "Obliques", "Traps", "Lats", "Triceps", "Lower back",
                "Glutes", "Hamstrings", "Calves", "Cardio"
            };

        private int savedMenu;

    //This is the current exercise to edit
    private Exercise currentExercise;

    //Properties
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

        //Sort the muscle auto complete list
        Arrays.sort(_muscleSuggestions);

        //Set teh adapter for the auto complete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(),
                android.R.layout.simple_dropdown_item_1line,_muscleSuggestions);
        AutoCompleteTextView autoComplete = view.findViewById(R.id.MuscleAutoComplete);
        autoComplete.setAdapter(adapter);

        //Sets the onclick event
        view.findViewById(R.id.MuscleAutoComplete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This shows the auto complete
                ((AutoCompleteTextView)view).showDropDown();
            }
        });

        /*
            If the current exercise is not null, then apply the current exercise to the
            different views or call new exercise behavior
         */
        if(currentExercise == null)
            applyNewExercise(view);
        else
            applyOldExercise(view);


        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);

        //Add a radio group on checked listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Gets the different views to change
                TextInputEditText first = view.findViewById(R.id.firstSetElement);
                TextInputEditText second = view.findViewById(R.id.secondSetElement);
                TextInputEditText third = view.findViewById(R.id.thirdSetElement);

                //Sets the views text to null
                first.setText(null);
                second.setText(null);
                third.setText(null);

                //Resets the views error messages to null
                first.setError(null);
                second.setError(null);
                third.setError(null);

                //Set the current hints for the views
                applySetHints(view,checkedId);
            }
        });

        return view;
    }

    //Call this to set the current hint for the number views
    private void applySetHints(View view, int checkedId)
    {
        //Set current type
        switch (checkedId)
        {
            case R.id.WeightItem:
                currentExercise.setType(ExerciseType.Weight);
                break;
            case R.id.TimeItem:
                currentExercise.setType(ExerciseType.Time);
                break;
        }

        //Get the edit texts
        TextInputEditText first = view.findViewById(R.id.firstSetElement);
        TextInputEditText second = view.findViewById(R.id.secondSetElement);
        TextInputEditText third = view.findViewById(R.id.thirdSetElement);

        //Gets the input layouts
        TextInputLayout firstLayout = view.findViewById(R.id.firstSetElementLayout);
        TextInputLayout secondLayout = view.findViewById(R.id.secondSetElementLayout);
        TextInputLayout thirdLayout = view.findViewById(R.id.thirdSetElementLayout);

        //Set the hints for the number views
        String firstHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthHint) :
                getResources().getString(R.string.editExerciseTimeHint);
        String secondHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthSecondHint) :
                getResources().getString(R.string.editExerciseTimeSecondHint);
        String thirdHint = currentExercise.getType() == ExerciseType.Weight ?
                getResources().getString(R.string.editExerciseWeigthThirdHint) :
                getResources().getString(R.string.editExerciseTimeThirdHint);


        //Sets the number views input type for the different exercise types
        switch (checkedId)
        {
            case R.id.WeightItem:
                first.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                second.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                third.setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL |
                        InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.TimeItem:
                first.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_CLASS_TEXT);
                second.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_CLASS_TEXT);
                third.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_CLASS_TEXT);
                break;
        }

        //This sets the hints
        firstLayout.setHint(firstHint);
        secondLayout.setHint(secondHint);
        thirdLayout.setHint(thirdHint);
    }

    //Call this to run the new exercise behavior
    private void applyNewExercise(View view)
    {
        currentExercise = new Exercise();
        RadioGroup radioGroup = view.findViewById(R.id.exerciseType);
        radioGroup.check(R.id.WeightItem);
        currentExercise.setType(ExerciseType.Weight);
        applySetHints(view,radioGroup.getCheckedRadioButtonId());
    }

    //Call this to apply an old exercise
    private void applyOldExercise(View view)
    {
        //This checks the current type
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

        //This applies the new hints
        applySetHints(view,radioGroup.getCheckedRadioButtonId());

        //This is to get the different views
        TextInputEditText name = view.findViewById(R.id.NameEditText);
        AutoCompleteTextView muscle = view.findViewById(R.id.MuscleAutoComplete);
        TextInputEditText first = view.findViewById(R.id.firstSetElement);
        TextInputEditText second = view.findViewById(R.id.secondSetElement);
        TextInputEditText third = view.findViewById(R.id.thirdSetElement);

        //This code sets the different number text from the current exercise
        String firstText = String.valueOf(currentExercise.getType() == ExerciseType.Weight ?
                currentExercise.getSets()
                : currentExercise.getReps());

        String secondText = currentExercise.getType() == ExerciseType.Weight ?
                String.valueOf(currentExercise.getReps())
                : Float.toString(currentExercise.getTime());

        String thirdText = currentExercise.getType() == ExerciseType.Weight ?
                String.valueOf(currentExercise.getKg())
                : Float.toString(currentExercise.getKm());

        //Set first element value
        first.setText(firstText);

        //Set name
        name.setText(currentExercise.getName());

        //Set muscle
        muscle.setText(currentExercise.getMuscle());

        //set second element value
        second.setText(secondText);

        //set second element value
        third.setText(thirdText);
    }


    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();

        //Sets the toolbar type to edit- or add exercise
        setToolbarTitle(currentExercise != null && currentExercise.getuId() != null ?
                "Edit exercise" : "Add exercise");

        //Hide the bottom navigation
        setupBottomNavigation(View.GONE);

        if(toolbar != null)
        {
            //This sets the back button icon
            toolbar.setNavigationIcon(R.drawable.back_white);

            //This makes the back button pop the back stack
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });

            //This is to store the old toolbar menu, to apply it after the fragment is done
            savedMenu = MainActivity.Activity.get_state().getMenuId();

            //Sets the new toolbar menu
            MainActivity.Activity.get_state().setMenuId(R.menu.edit_exercise_menu);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Applies the old toolbar menu
        if(savedMenu != 0)
            MainActivity.Activity.get_state().setMenuId(savedMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_exercise:

                //This makes sure that the current routine uId is correct
                currentExercise.setRoutineUId(getRoutineUId());

                //Gets the different views
                TextInputEditText name = getView().findViewById(R.id.NameEditText);
                AutoCompleteTextView muscle = getView().findViewById(R.id.MuscleAutoComplete);
                String nameText = name.getText().toString();
                String muscleText = muscle.getText().toString();

                //Sets name and muscle error messages if they are empty
                if(nameText.isEmpty())
                    name.setError(getString(R.string.required));
                if(muscleText.isEmpty())
                    muscle.setError(getString(R.string.required));

                //Set name and muscle for the current exercise
                currentExercise.setName(nameText);
                currentExercise.setMuscle(muscleText);

                //Values used to store the differed numbers values
                int sets = 0;
                int reps = 0;
                float kg = 0;
                float time = 0;
                float km = 0;

                //This gets the number values and if this is false, then something went wrong
                boolean validNumbers = applyNumberValues(sets,reps,kg,time,km);

                if(validNumbers && currentExercise != null && currentExercise.valid())
                {
                    //This gets tne database reference and use uId
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    String uId = DataManager.getInstance().getUser().getUid();

                    /*
                    If the exercise uId is empty, then add the current exercise as a
                    new exercise otherwise update the old exercise
                     */
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
                    getFragmentManager().popBackStack();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean applyNumberValues(int sets, int reps, float kg, float time, float km)
    {
        //Gets the number views
        TextInputEditText first = getView().findViewById(R.id.firstSetElement);
        TextInputEditText second = getView().findViewById(R.id.secondSetElement);
        TextInputEditText third = getView().findViewById(R.id.thirdSetElement);

        //Gets the number views text
        String firstText = first.getText().toString();
        String secondText = second.getText().toString();
        String thirdText = third.getText().toString();
        boolean valid = true;

        if(currentExercise.getType() == ExerciseType.Weight)
        {
            /*
                In the code below, every view text extraction is in a try catch, because an error
                can happen if the text is not a number, if the text is not a number, then it
                tells the user. Also if a number is required and is empty, then it tells the user.
                If an error occurred, then return false.
             */

            try
            {
                sets = firstText.isEmpty() ? 0 : Integer.parseInt(first.getText().toString());
                currentExercise.setSets(sets);
                if(sets <= 0)
                {
                    valid = false;
                    first.setError(getString(R.string.required));
                }

            }
            catch (NumberFormatException exception)
            {
                first.setError(getString(R.string.invalid_number));
                valid = false;
            }


            try
            {
                reps = secondText.isEmpty() ? 0 : Integer.parseInt(second.getText().toString());
                currentExercise.setReps(reps);
                if(reps <= 0)
                {
                    valid = false;
                    second.setError(getString(R.string.required));
                }

            }
            catch (NumberFormatException exception)
            {
                valid = false;
                second.setError(getString(R.string.invalid_number));

            }

            //Get the KG value
            try
            {
                kg = thirdText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(thirdText),2);
                currentExercise.setKg(kg);
            }
            catch (NumberFormatException exception)
            {
                valid = false;
                third.setError(getString(R.string.invalid_number));
            }

            if(!valid)
                return false;
        }
        else if(currentExercise.getType() == ExerciseType.Time)
        {
            try
            {
                reps = firstText.isEmpty() ? 0 : Integer.parseInt(first.getText().toString());
                currentExercise.setReps(reps);
                if (reps == 0)
                    reps = 1;

            }
            catch (NumberFormatException exception)
            {
                valid = false;

            }
            try
            {
                time = secondText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(secondText),2);
                currentExercise.setTime(time);
            }
            catch (NumberFormatException exception)
            {
                valid = false;

            }
            try
            {
                km = thirdText.isEmpty() ? 0 : MathUtils.round(Float.parseFloat(thirdText),2);
                currentExercise.setKm(km);
            }
            catch (NumberFormatException exception)
            {
                valid = false;
            }

            if(time == 0 && km == 0)
            {
                valid = false;
                second.setError(getString(R.string.time_km_require));
                third.setError(getString(R.string.time_km_require));
            }

            if(!valid)
                return false;
        }
        return true;
    }


}
