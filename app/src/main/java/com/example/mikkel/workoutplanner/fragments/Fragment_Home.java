package com.example.mikkel.workoutplanner.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.RoutineGridAdapter;
import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.utils.Animation;
import com.example.mikkel.workoutplanner.data.Database.MuscleInfo;
import com.example.mikkel.workoutplanner.viewholders.RoutineHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Fragment_Home extends NavigationFragment implements Notification
{
    private FirebaseRecyclerAdapter listAdapter;
    private RoutineGridAdapter currentAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        routinesSetup();
        DataManager.getInstance().getMuscleInfoEvent().subscribe(this);
        DataManager.getInstance().getCurrentRoutineEvent().subscribe(this);
        DataManager.getInstance().getLastRoutineEvent().subscribe(this);
        listAdapter.startListening();
    }

    private void routinesSetup()
    {
        View view = getView();

        if(view == null)
            return;

        recyclerView = view.findViewById(R.id.routinesRecyclerView);
        updateAdapter();

    }

    @Override
    public void onStop() {
        super.onStop();
        listAdapter.stopListening();
        DataManager.getInstance().getMuscleInfoEvent().unsubscribe(this);
        DataManager.getInstance().getCurrentRoutineEvent().unsubscribe(this);
        DataManager.getInstance().getLastRoutineEvent().unsubscribe(this);
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Home");
        MainActivity.Activity.getState().setMenuId(R.menu.menu);
    }

    private void updateAdapter()
    {
        updateCurrentRoutine();
        updateLastRoutine();
        updateRoutineList();
    }

    private void updateLastRoutine()
    {
        final ExecuteRoutine executeRoutine = DataManager.getInstance().getLastRoutine();
        View view = getView();

        if (view == null)
            return;

        //Get the last routine super view
        View currentRoutineView = view.findViewById(R.id.lastRoutine);

        //Set if the title should be visible
        view.findViewById(R.id.homeLastRoutineTitle).
                setVisibility(executeRoutine == null ? View.GONE : View.VISIBLE);

        //If there is not a current routine, then hide the routine super view
        if (executeRoutine == null)
        {
            currentRoutineView.setVisibility(View.GONE);
            return;
        }
        currentRoutineView.setVisibility(View.VISIBLE);

        //Get the cardview
        CardView cardView = currentRoutineView.findViewById(R.id.lastRoutineCardView);

        //Add click listener for the cardview
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeOldRoutine(executeRoutine,executeRoutine.getuId());
            }
        });

        //Get the title for the current routine at set it to the name of the routine
        TextView title = currentRoutineView.findViewById(R.id.lastRoutineTitle);
        title.setText(executeRoutine.getName());

        //Setup the recyclerview
        RecyclerView recyclerView = currentRoutineView.findViewById(R.id.lastMusclesRecyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,true));
        currentAdapter = new RoutineGridAdapter(
                getActivity(),executeRoutine.getMuscleInfos(),
                executeRoutine.getuId());
        recyclerView.setAdapter(currentAdapter);
    }


    private void updateCurrentRoutine()
    {
        final ExecuteRoutine executeRoutine = DataManager.getInstance().getCurrentRoutine();
        View view = getView();

        if (view == null)
         return;

        //Get the current routine super view
        View currentRoutineView = view.findViewById(R.id.currentRoutine);
        view.findViewById(R.id.homeCurrentRoutineTitle).
                setVisibility(executeRoutine == null ? View.GONE : View.VISIBLE);

        //If there is not a current routine, then hide the routine super view
        if (executeRoutine == null)
        {
            currentRoutineView.setVisibility(View.GONE);
            return;
        }
        currentRoutineView.setVisibility(View.VISIBLE);

        //Get the cardview
        CardView cardView = currentRoutineView.findViewById(R.id.currentRoutineCardView);

        //Add click listener for the cardview
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeOldRoutine(executeRoutine,null);
            }
        });

        //Get the title for the current routine at set it to the name of the routine
        TextView title = currentRoutineView.findViewById(R.id.currentRoutineTitle);
        title.setText(executeRoutine.getName());

        //If the stop button is pressed, then remove the current rotutine
        currentRoutineView.findViewById(R.id.currentStopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Ask the user if the ywant to stop their current routine
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to stop your current routine?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Reset current execute routine
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        String uId = DataManager.getInstance().getUser().getUid();
                        DatabaseReference ref = database.
                                child(DataManager.CURRENT_EXECUTE_ROUTINES_PATH_ID).
                                child(uId).child("CurrentRoutine");
                        ref.setValue(null);
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
        });

        //Setup the recyclerview
        RecyclerView recyclerView = currentRoutineView.findViewById(R.id.currentMusclesRecyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,true));
        currentAdapter = new RoutineGridAdapter(
                getActivity(),executeRoutine.getMuscleInfos(),
                executeRoutine.getuId());
        recyclerView.setAdapter(currentAdapter);

    }

    private void updateRoutineList()
    {
        //Used for the on recycler click event later in the code
        final Fragment_Home home = this;

        //The code below makes the firebase recycler view behavior
        Query query = FirebaseDatabase.getInstance().getReference().
                child(DataManager.ROUTINES_PATH_ID).
                child(DataManager.getInstance().getUser().getUid()).
                limitToLast(100);

        FirebaseRecyclerOptions<Routine> options =
                new FirebaseRecyclerOptions.Builder<Routine>().
                        setQuery(query,Routine.class).build();

        //This created the listAdapter
        listAdapter = new FirebaseRecyclerAdapter<Routine, RoutineHolder>(options) {
            @NonNull
            @Override
            public RoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).
                        inflate(R.layout.routine_element,parent,false);
                return new RoutineHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final RoutineHolder holder, int position, @NonNull final Routine model) {
                holder.title.setText(model.getName());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,true);
                holder.muscles.setLayoutManager(gridLayoutManager);

                holder.editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment_Routines routines = new Fragment_Routines();
                        routines.changeCurrentTab(model.getuId());

                        //This opens the edit exercise fragment
                        FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                                routines,true,
                                new Animation(R.anim.enter_from_right,R.anim.exit_to_left,
                                        R.anim.enter_from_left,R.anim.exit_to_right));
                        MainActivity.Activity.setCheckedInButtonNavigation(R.id.navigation_routines);
                    }
                });

                ArrayList<MuscleInfo> dataManagerList = null;

                if(DataManager.getInstance().getMuscleInfoes().containsKey(model.getuId()))
                    dataManagerList = DataManager.getInstance().getMuscleInfoes().get(model.getuId());

                if(dataManagerList == null)
                    return;

                //Setup the adapter
                RoutineGridAdapter gridAdapter = new RoutineGridAdapter(getActivity(),dataManagerList,model.getuId());
                gridAdapter.getOnClick().subscribe(home);
                holder.muscles.setAdapter(gridAdapter);

                //Set click listeners
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executeNewRoutine(model.getuId());
                    }
                });
                holder.muscles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executeNewRoutine(model.getuId());
                    }
                });
            }
        };

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter.startListening();
    }

    /**
     * Call this to open the old routine in a new fragment
     * @param executeRoutine
     * @param oldUid
     */
    private void executeOldRoutine(ExecuteRoutine executeRoutine, String oldUid)
    {
        Fragment_ExecuteRoutine executeRoutineFragment = new Fragment_ExecuteRoutine();
        executeRoutineFragment.getState().setRoutineuId(executeRoutine.getuId());
        executeRoutineFragment.getState().setExecuteRoutine(executeRoutine);
        executeRoutineFragment.getState().setOldUId(oldUid);
        executeRoutineFragment.getState().setUpdateLast(true);

        FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                executeRoutineFragment,false,
                new Animation(R.anim.enter_from_right,R.anim.exit_to_left,
                        R.anim.enter_from_left,R.anim.exit_to_right));
    }

    /**
     * Call this to open a new routine in a new fragment
     * @param uId
     */
    private void executeNewRoutine(final String uId)
    {
        if(DataManager.getInstance().getCurrentRoutine() != null)
        {
            //Ask the user if the ywant to stop their current routine
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to override your current routine?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startRoutine(uId);
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
        else
        {
            startRoutine(uId);
        }


    }

    /**
     * From a routine uId, open a new fragment with that routine
     * @param uId
     */
    private void startRoutine(String uId)
    {
        ArrayList<MuscleInfo> list = DataManager.getInstance().getMuscleInfoes().get(uId);
        if(list != null && list.size() > 0)
        {
            Fragment_ExecuteRoutine executeRoutineFragment = new Fragment_ExecuteRoutine();
            executeRoutineFragment.getState().setRoutineuId(uId);

            ArrayList<MuscleInfo> muscleInfos = DataManager.getInstance().getMuscleInfoes().get(uId);
            ArrayList<MuscleInfo> newMuscleslist = (ArrayList<MuscleInfo>) muscleInfos.clone();
            ExecuteRoutine executeRoutine = new ExecuteRoutine();
            executeRoutineFragment.getState().setUpdateLast(true);

            ArrayList<Routine> routines = DataManager.getInstance().getRoutines();
            Routine routine = null;

            for (int i = 0; i < routines.size() ; i++) {
                if(routines.get(i).getuId().equals(uId))
                {
                    routine = routines.get(i);
                    break;
                }
            }

            if(routine != null)
                executeRoutine.convert(routine);

            executeRoutine.setMuscleInfos(newMuscleslist);
            executeRoutineFragment.getState().setExecuteRoutine(executeRoutine);

            FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                    executeRoutineFragment,false,
                    new Animation(R.anim.enter_from_right,R.anim.exit_to_left,
                            R.anim.enter_from_left,R.anim.exit_to_right));
        }
    }


    @Override
    public void onNotification(Object sender, Object data) {
        if(sender == DataManager.getInstance())
            updateAdapter();
        else if(data instanceof String)
            executeNewRoutine(data.toString());


    }
}
