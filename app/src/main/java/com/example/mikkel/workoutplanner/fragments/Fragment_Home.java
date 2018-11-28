package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.routineGridAdapter;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.utils.MuscleInfo;
import com.example.mikkel.workoutplanner.viewholders.RoutineHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Fragment_Home extends NavigationFragment
{
    private ArrayList<MuscleInfo> tempData = new ArrayList<MuscleInfo>();
    private FirebaseRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        tempData.add(new MuscleInfo("Leg",2));
        tempData.add(new MuscleInfo("Bicep",2));
        tempData.add(new MuscleInfo("Tricep",2));

        RecyclerView recyclerView = view.findViewById(R.id.routinesRecyclerView);

        //The code below makes the firebase recycler view behavior
        Query query = FirebaseDatabase.getInstance().getReference().
                child(DataManager.Routines_PATH_ID).
                child(DataManager.getInstance().get_user().getUid()).
                limitToLast(100);

        FirebaseRecyclerOptions<Routine> options =
                new FirebaseRecyclerOptions.Builder<Routine>().
                        setQuery(query,Routine.class).build();

        adapter = new FirebaseRecyclerAdapter<Routine, RoutineHolder>(options) {
            @NonNull
            @Override
            public RoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).
                        inflate(R.layout.routine_element,parent,false);
                return new RoutineHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoutineHolder holder, int position, @NonNull Routine model) {
                holder.title.setText(model.getName());
                holder.muscles.setAdapter(new routineGridAdapter(getActivity(),tempData));
                holder.muscles.setLayoutManager(new GridLayoutManager(getActivity(),3));
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Home");

        MainActivity.Activity.get_state().setMenuId(R.menu.menu);

    }
}
