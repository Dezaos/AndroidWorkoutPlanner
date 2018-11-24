package com.example.mikkel.workoutplanner.singletons;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.utils.CollectionUtils;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.StateData;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.utils.ListUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
    private EventHandler eventHandler = new EventHandler();

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
                child(DataManager.Routines_PATH_ID).child(_user.getUid()).addChildEventListener(
                        new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                routines.add(routine);
                eventHandler.notifyAllListeners(routine);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                int index = ListUtils.indexOfWithEquals(Routine.class,routines,routine);
                if(index >= 0)
                {
                    routines.set(index,routine);
                    eventHandler.notifyAllListeners(routine);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Routine routine = Routine.build(Routine.class,dataSnapshot);
                if(CollectionUtils.removeByEquals(routines,routine))
                {
                    eventHandler.notifyAllListeners(routine);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
