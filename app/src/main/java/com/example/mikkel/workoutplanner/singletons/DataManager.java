package com.example.mikkel.workoutplanner.singletons;

import android.support.annotation.NonNull;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.Utils.EventHandler;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.StateData;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class DataManager {

    //Singleton field & property
    private static final DataManager ourInstance = new DataManager();
    public static DataManager getInstance() {
        return ourInstance;
    }

    //Static fields
    public static final String Routines_PATH_ID = "Routines";
    public static final String EXERCISES_PATH_ID = "Exercises";

    //Fields
    private FirebaseAuth _auth;
    private ArrayList<StateData> _stateData = new ArrayList<StateData>();
    private boolean _init;
    private FirebaseUser _user;
    private ArrayList<Routine> routines = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private EventHandler eventHandler = new EventHandler();
    private Exercise currentEditExercise;

    //Properties
    public FirebaseUser get_user() {
        return _user;
    }
    public void set_user(FirebaseUser _user) {
        this._user = _user;
    }

    public boolean get_init() {
        return _init;
    }

    public void set_init(boolean _init) {
        this._init = _init;
    }

    public ArrayList<Routine> getRoutines() {
        return routines;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Exercise getCurrentEditExercise() {
        return currentEditExercise;
    }

    public void setCurrentEditExercise(Exercise currentEditExercise) {
        this.currentEditExercise = currentEditExercise;
    }

    //Contructor
    private DataManager()
    {
        _auth = FirebaseAuth.getInstance();
        _user = _auth.getCurrentUser();
    }

    public void login()
    {
        subscribeSyncEvents();
    }

    private void subscribeSyncEvents()
    {
        FirebaseDatabase.getInstance().getReference().
                child(DataManager.Routines_PATH_ID).child(_user.getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        routines.clear();
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        while (iterator.hasNext())
                        {
                            DataSnapshot snapshot = iterator.next();
                            Routine routine = snapshot.getValue(Routine.class);
                            routine.setuId(snapshot.getKey());
                            routines.add(routine);
                        }
                        eventHandler.notifyAllListeners(routines);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        int x = 1;
                    }
                });

        FirebaseDatabase.getInstance().getReference().
                child(DataManager.EXERCISES_PATH_ID).child(_user.getUid()).addValueEventListener(
                        new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exercises.clear();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext())
                {
                    DataSnapshot snapshot = iterator.next();
                    Exercise exercise = snapshot.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                eventHandler.notifyAllListeners(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void logout()
    {
        _user = null;

        AuthUI.getInstance()
                .signOut(MainActivity.Activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        FragmentTransitionManager.getInstance().initializeFragment(
                                MainActivity.Activity,new Fragment_Login(),true);
                    }
                });

    }

    public <T extends StateData> T addState(T state)
    {
        _stateData.add(state);
        return state;
    }

    public <T extends StateData> T getState(Class<T> type)
    {
        for (int i = 0; i < _stateData.size(); i++) {
            if(type.isInstance(_stateData.get(i)))
            {
                return (T)_stateData.get(i);
            }
        }
        return null;
    }



}
