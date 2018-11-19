package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.ExerciseAdapter;
import com.example.mikkel.workoutplanner.data.Database.Plan;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Exercises extends Fragment
{
    private FirebaseRecyclerOptions adapter;
    private String planUId;

    public String getPlanUId() {
        return planUId;
    }

    public void setPlanUId(String planUId) {
        this.planUId = planUId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises,container,false);

//        adapter = new ExerciseAdapter(getActivity(), WorkoutPlansManager.getInstance().getExercises());
//        ListView listView = view.findViewById(R.id.workoutListView);
//        listView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.addExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                Fragment_EditExercise editExercise = new Fragment_EditExercise();
                editExercise.setPlanUId(getPlanUId());

                FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                        editExercise,false,
                        R.anim.enter_from_right,R.anim.exit_to_left,
                        R.anim.enter_from_left,R.anim.exit_to_right);

//                Plan plan = new Plan();
//                plan.setName((String)data);
//                database.child(DataManager.PLANS_PATH_ID).child(DataManager.getInstance().get_user().getUid()).push().setValue(plan);
            }
        });

        return view;
    }


}
