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

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.viewholders.ExerciseHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Fragment_Exercises extends Fragment
{
    private FirebaseRecyclerAdapter adapter;
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
        final View view = inflater.inflate(R.layout.fragment_exercises,container,false);

        DataManager dataManager = DataManager.getInstance();
        String uid = dataManager.get_user().getUid();

        Query query = FirebaseDatabase.getInstance().getReference().
                child(DataManager.EXERCISES_PATH_ID).child(uid).child(routineUId).
                limitToLast(100);

        FirebaseRecyclerOptions<Exercise> options = new FirebaseRecyclerOptions.
                Builder<Exercise>().setQuery(query,Exercise.class).build();

        adapter = new FirebaseRecyclerAdapter<Exercise, ExerciseHolder>(options) {
            @NonNull
            @Override
            public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.routine_item,parent,false);

                return new ExerciseHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull Exercise model) {
                holder.name.setText(model.getName());
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
}
