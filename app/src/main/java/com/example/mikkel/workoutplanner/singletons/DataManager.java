package com.example.mikkel.workoutplanner.singletons;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.utils.CollectionUtils;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.utils.ListUtils;
import com.example.mikkel.workoutplanner.utils.MuscleInfo;
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
import java.util.HashMap;

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
    private HashMap<String,ArrayList<MuscleInfo>> muscleInfoes = new HashMap<>();
    private EventHandler routineEvent = new EventHandler();
    private EventHandler muscleInfoEvent = new EventHandler();

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

    public EventHandler getRoutineEvent() {
        return routineEvent;
    }

    public HashMap<String, ArrayList<MuscleInfo>> getMuscleInfoes() {
        return muscleInfoes;
    }

    public void setMuscleInfoes(HashMap<String, ArrayList<MuscleInfo>> muscleInfoes) {
        this.muscleInfoes = muscleInfoes;
    }

    public EventHandler getMuscleInfoEvent() {
        return muscleInfoEvent;
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
        //Routine subscription
        FirebaseDatabase.getInstance().getReference().
                child(DataManager.Routines_PATH_ID).child(user.getUid()).addChildEventListener(
                        new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //This adds a new routine to the local list when a new routine is added
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                routines.add(routine);
                routineEvent.notifyAllListeners(this,routine);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //This updates the routine in the local list when the routine is changed
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                int index = ListUtils.indexOfWithEquals(Routine.class,routines,routine);
                if(index >= 0)
                {
                    routines.set(index,routine);
                    routineEvent.notifyAllListeners(this, routine);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                //This removes a routine in the local list when a routine is removed
                Routine routine = Routine.build(Routine.class,dataSnapshot);
                if(CollectionUtils.removeByEquals(routines,routine))
                {
                    routineEvent.notifyAllListeners(this, routine);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Update if a new exercise has been added
        FirebaseDatabase.getInstance().getReference().
                child(DataManager.EXERCISES_PATH_ID).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    updateMuscleInfo(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateMuscleInfo(DataSnapshot dataSnapshot)
    {
        Exercise firstExercise = Exercise.build(Exercise.class,dataSnapshot.getChildren().iterator().next());

        //If the muscleinfos list contains the routine, then clear the list, if not add a new list
        if(DataManager.getInstance().getMuscleInfoes().containsKey(firstExercise.getRoutineUId()))
            DataManager.getInstance().getMuscleInfoes().get(firstExercise.getRoutineUId()).clear();
        else
            DataManager.getInstance().getMuscleInfoes().put(firstExercise.getRoutineUId(),new ArrayList<MuscleInfo>());

        ArrayList<MuscleInfo> list = DataManager.getInstance().getMuscleInfoes().get(firstExercise.getRoutineUId());

        //Update the muscleinfo list from the new exercises
        for(DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            Exercise exercise = Exercise.build(Exercise.class,snapshot);

            //if the muscleinfo list contains the muscle, the
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getMuscle().equals(exercise.getMuscle()))
                {
                    list.get(i).addExercise(exercise.getuId());
                    found = true;
                }
            }

            if(!found)
                list.add(new MuscleInfo(exercise.getMuscle(),exercise.getuId()));
        }

        muscleInfoEvent.notifyAllListeners(this,firstExercise.getRoutineUId());
    }

    private void addMuscleInfoExercise(Exercise exercise)
    {
        //This part checks if the routine is in the muscleinfoes list, if it is not,
        // then add the routine and the new musfleinfo
        if(!muscleInfoes.containsKey(exercise.getuId()))
        {
            ArrayList<MuscleInfo> list = new ArrayList<>();
            list.add(new MuscleInfo(exercise.getMuscle(),exercise.getuId()));
            muscleInfoes.put(exercise.getRoutineUId(),list);
        }
        else
        {
            //If the routine is in the muscleinfoes list, then check if the exercise
            //is present, if it is, then add one to the amount. If if is not,
            //then add a new musleinfo about the exercise
            ArrayList<MuscleInfo> list = muscleInfoes.get(exercise.getRoutineUId());
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                MuscleInfo info = list.get(i);
                if(info.getMuscle().equals(exercise.getMuscle()))
                {
                    info.addExercise(exercise.getuId());
                }
            }
            if(!found)
                list.add(new MuscleInfo(exercise.getMuscle(),exercise.getuId()));
        }
        if(exercise.getRoutineUId() != null)
            muscleInfoEvent.notifyAllListeners(this,exercise.getRoutineUId());
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
