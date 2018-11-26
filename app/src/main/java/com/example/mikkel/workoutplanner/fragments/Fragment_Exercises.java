package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.data.StateData.ExercisesFragmentState;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.viewholders.ExerciseHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Fragment_Exercises extends Fragment
{
    private FirebaseRecyclerAdapter adapter;
    private ExercisesFragmentState state;

    public String getRoutineUId() {
        if(state == null)
        {
            state = DataManager.getInstance().getState(ExercisesFragmentState.class);
            if(state == null)
            {
                state = DataManager.getInstance().addState(new ExercisesFragmentState());
            }
        }

        return state.getRoutineUid();
    }

    public void setRoutineUId(String routineUId) {
        if(state == null)
        {
            state = DataManager.getInstance().getState(ExercisesFragmentState.class);
            if(state == null)
            {
                state = DataManager.getInstance().addState(new ExercisesFragmentState());
            }
        }
        state.setRoutineUid(routineUId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercises,container,false);

        DataManager dataManager = DataManager.getInstance();
        final String uid = dataManager.get_user().getUid();

        Query query = FirebaseDatabase.getInstance().getReference().
                child(DataManager.EXERCISES_PATH_ID).child(uid).child(getRoutineUId()).
                limitToLast(100);

        FirebaseRecyclerOptions<Exercise> options = new FirebaseRecyclerOptions.
                Builder<Exercise>().setQuery(query,Exercise.class).build();

        adapter = new FirebaseRecyclerAdapter<Exercise, ExerciseHolder>(options) {
            @NonNull
            @Override
            public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.exercise_element,parent,false);

                return new ExerciseHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull final Exercise model) {
                holder.name.setText(model.getName());
                holder.mucle.setText(model.getMuscle());

                final String uId = model.getuId();

                String firstElement = String.valueOf(model.getType() == ExerciseType.Weight ?
                        (int)model.getSets() : (int)model.getReps());
                String secondElement = model.getType() == ExerciseType.Weight ?
                        String.valueOf(model.getReps()) : String.valueOf(model.getTime());
                String thirdElement = String.valueOf(model.getType() == ExerciseType.Weight ?
                        model.getKg() : model.getKm());

                String firstHint = model.getType() == ExerciseType.Weight ?
                        getResources().getString(R.string.editExerciseWeigthHint) :
                        getResources().getString(R.string.editExerciseTimeHint);
                String secondHint = model.getType() == ExerciseType.Weight ?
                        getResources().getString(R.string.editExerciseWeigthSecondHint) :
                        getResources().getString(R.string.editExerciseTimeSecondHint);
                String thirdHint = model.getType() == ExerciseType.Weight ?
                        getResources().getString(R.string.editExerciseWeigthThirdHint) :
                        getResources().getString(R.string.editExerciseTimeThirdHint);

                holder.firstElment.setText(firstElement);
                holder.secondElment.setText(secondElement);
                holder.thirdElment.setText(thirdElement);

                holder.firstHint.setText(firstHint);
                holder.secondHint.setText(secondHint);
                holder.thirdtHint.setText(thirdHint);
                holder.removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().
                                child(DataManager.EXERCISES_PATH_ID).
                                child(DataManager.getInstance().get_user().getUid()).
                                child(model.getRoutineUId()).child(uId).setValue(null);
                    }
                });

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onEditExerciseClick(model);
                    }
                });
            }
        };

        RecyclerView exerciseList = view.findViewById(R.id.exerciseRecycleListView);
        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(MainActivity.Activity));
        adapter.startListening();

        FloatingActionButton fab = view.findViewById(R.id.addExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void onEditExerciseClick(Exercise exercise)
    {
        Fragment_EditExercise editExercise = new Fragment_EditExercise();
        editExercise.setRoutineUId(getRoutineUId());
        editExercise.setCurrentExercise(exercise);

        FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                editExercise,false,
                R.anim.enter_from_right,R.anim.exit_to_left,
                R.anim.enter_from_left,R.anim.exit_to_right);
    }
}
