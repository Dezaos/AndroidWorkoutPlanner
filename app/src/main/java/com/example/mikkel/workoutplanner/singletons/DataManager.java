package com.example.mikkel.workoutplanner.singletons;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.utils.CollectionUtils;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.utils.ListUtils;
import com.example.mikkel.workoutplanner.utils.StateHandler;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//This singleton is used to store different data for the app
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
    private FirebaseAuth auth;
    private boolean init;
    private FirebaseUser user;
    private ArrayList<Routine> routines = new ArrayList<>();
    private EventHandler eventHandler = new EventHandler();

    //Properties
    public FirebaseUser getUser() {
        return user;
    }
    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
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
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    //Call this to run login behavior
    public void login()
    {
        subscribeSyncEvents();
    }

    //Call this to subscribe to firebase sync events
    private void subscribeSyncEvents()
    {
        FirebaseDatabase.getInstance().getReference().
                child(DataManager.Routines_PATH_ID).child(user.getUid()).addChildEventListener(
                        new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //This adds a new routine to the local list when a new routine is added
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                routines.add(routine);
                eventHandler.notifyAllListeners(routine);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //This updates the routine in the local list when the routine is changed
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

                //This removes a routine in the local list when a routine is removed
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

    //Call this to run the logout behavior
    public void logout()
    {
        user = null;

        //This code runs the firebase logout behavior
        AuthUI.getInstance()
                .signOut(MainActivity.Activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        FragmentTransitionManager.getInstance().initializeFragment(
                                MainActivity.Activity,new Fragment_Login(),true);
                    }
                });

    }



}
