package com.example.mikkel.workoutplanner.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.ExecuteExerciseAdapter;
import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.ExecuteRoutineFragmentState;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.data.Database.MuscleInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_ExecuteRoutine extends NavigationFragment implements Notification
{
    private ExecuteExerciseAdapter adapter;
    private Snackbar snackbar;
    private Timer timer;
    private View view;

    public ExecuteRoutineFragmentState getState()
    {
        return getSafeState(ExecuteRoutineFragmentState.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_exexute_routine,container,false);
        setToolbarTitle(getState().getExecuteRoutine().getName());
        updateDatabase();

        adapter = new ExecuteExerciseAdapter(getActivity(),getState().getExecuteRoutine().getExercises());
        RecyclerView recyclerView = view.findViewById(R.id.executeRoutineRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.getOnClick().subscribe(this);

        view.findViewById(R.id.exerciseDoneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routineDone();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showTimer(false);
    }

    private void routineDone()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you done?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getState().setDone(true);

                //Reset current execute routine
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                String uId = DataManager.getInstance().getUser().getUid();
                DatabaseReference ref = database.child(DataManager.Current_Execute_Routines_PATH_ID).child(uId).child("UserRoutine");
                getState().getExecuteRoutine().setuId(ref.getKey());
                ref.setValue(null);

                //Add the routine to the database
                addRoutineToDatabase();
                getFragmentManager().popBackStack();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void addRoutineToDatabase()
    {

    }

    private void updateDatabase()
    {
        //Update database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String uId = DataManager.getInstance().getUser().getUid();
        DatabaseReference ref = database.child(DataManager.Current_Execute_Routines_PATH_ID).child(uId).child("UserRoutine");
        getState().getExecuteRoutine().setuId(ref.getKey());
        ref.setValue(getState().getExecuteRoutine());
    }


    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();

        //Hide the bottom navigation
        setupBottomNavigation(View.GONE);

        if(toolbar != null)
        {
            //This sets the back button icon
            toolbar.setNavigationIcon(R.drawable.back_white);

            //setToolbarTitle();

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });

            //This is to store the old toolbar menu, to apply it after the fragment is done
            getState().setSavedMenu(MainActivity.Activity.getState().getMenuId());

            //Sets the new toolbar menu
            MainActivity.Activity.getState().setMenuId(R.menu.execute_routine_menu);
        }
    }




    private void showTimer(boolean updateSavedTime)
    {
        if(snackbar == null && updateSavedTime || (!updateSavedTime && getState().getShowSnackbar()))
        {
            snackbar = Snackbar.make(view,"",Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopSnackbar();
                }
            });

            snackbar.show();
            getState().setShowSnackbar(true);

            if(updateSavedTime)
                getState().setSavedTime(System.currentTimeMillis() / 1000);

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateTimeText();
                }
            },0,1000);

        }
        else if(snackbar != null)
        {
            getState().setSavedTime(System.currentTimeMillis() / 1000);
            updateTimeText();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if(!getState().isDone())
            updateDatabase();
    }

    @Override
    protected void onSafeStop() {
        super.onSafeStop();
        stopSnackbar();

        //Applies the old toolbar menu
        if(getState().getSavedMenu() != 0)
            MainActivity.Activity.getState().setMenuId(getState().getSavedMenu());
    }

    private void stopSnackbar()
    {
        if(snackbar != null)
        {
            snackbar.dismiss();
            snackbar = null;

        }
        if(timer != null)
        {
            timer.cancel();
            timer = null;
        }
        getState().setShowSnackbar(false);
    }

    private void updateTimeText()
    {
        long savedTime = getState().getSavedTime();

        double currentTime = System.currentTimeMillis()/1000;
        final long min = ((long) Math.floor(Math.abs(currentTime - savedTime) / 60));
        final long sec = (long)(Math.abs(currentTime - savedTime) % 60);


        if(snackbar != null)
        {
            MainActivity.Activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(snackbar != null)
                    {
                        snackbar.setText("Rest Time: " +
                                (min < 10 ? "0" : "") + min + ":" +
                                (sec < 10 ? "0" : "") + sec);
                    }
                }
            });
        }
    }

    @Override
    public void onNotification(Object sender, Object data) {
        showTimer(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.done_routine:
                routineDone();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
