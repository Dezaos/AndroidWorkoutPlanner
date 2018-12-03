package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.RoutineGridAdapter;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.utils.Animation;
import com.example.mikkel.workoutplanner.utils.MuscleInfo;
import com.example.mikkel.workoutplanner.viewholders.RoutineHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Fragment_Home extends NavigationFragment implements Notification
{
    private FirebaseRecyclerAdapter adapter;
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
        adapter.startListening();
    }

    private void routinesSetup()
    {
        View view = getView();

        if(view == null)
            return;

        recyclerView = view.findViewById(R.id.routinesRecyclerView);
        updateAdapter(null,true);

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        DataManager.getInstance().getMuscleInfoEvent().unsubscribe(this);
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Home");

        MainActivity.Activity.getState().setMenuId(R.menu.menu);

    }

    private void updateAdapter(String routineUid, final boolean initUpdate)
    {
        //Used for the on recycler click event later in the code
        final Fragment_Home home = this;

        ArrayList<MuscleInfo> checkList = null;

        if(!initUpdate)
        {
            checkList = DataManager.getInstance().getMuscleInfoes().get(routineUid);
            if(checkList == null)
                return;
        }

        final  ArrayList<MuscleInfo> list = checkList;

        //The code below makes the firebase recycler view behavior
        Query query = FirebaseDatabase.getInstance().getReference().
                child(DataManager.Routines_PATH_ID).
                child(DataManager.getInstance().getUser().getUid()).
                limitToLast(100);

        FirebaseRecyclerOptions<Routine> options =
                new FirebaseRecyclerOptions.Builder<Routine>().
                        setQuery(query,Routine.class).build();

        //This created the adapter
        adapter = new FirebaseRecyclerAdapter<Routine, RoutineHolder>(options) {
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

                ArrayList<MuscleInfo> newList = !initUpdate ? list : dataManagerList;

                if(newList == null)
                    return;

                RoutineGridAdapter gridAdapter = new RoutineGridAdapter(getActivity(),newList,model.getuId());
                gridAdapter.getOnClick().subscribe(home);
                holder.muscles.setAdapter(gridAdapter);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executeRoutine(model.getuId());
                    }
                });
                holder.muscles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executeRoutine(model.getuId());
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();
    }

    private void executeRoutine(String uId)
    {
        ArrayList<MuscleInfo> list = DataManager.getInstance().getMuscleInfoes().get(uId);
        if(list != null && list.size() > 0)
        {
            Fragment_ExecuteRoutine executeRoutine = new Fragment_ExecuteRoutine();

            //Set routine Uid
            executeRoutine.getState().setExecuteRoutineUid(uId);

            //Set routine name
            ArrayList<Routine> routines = DataManager.getInstance().getRoutines();
            for (int i = 0; i < routines.size(); i++) {
                if(routines.get(i).getuId().equals(uId))
                {
                    executeRoutine.getState().setRoutineName(routines.get(i).getName());
                    break;
                }

            }

            FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                    executeRoutine,false,
                    new Animation(R.anim.enter_from_right,R.anim.exit_to_left,
                            R.anim.enter_from_left,R.anim.exit_to_right));
        }
    }

    @Override
    public void onNotification(Object sender, Object data) {
        if(sender == DataManager.getInstance())
            updateAdapter(data.toString(),false);
        else if(data instanceof String)
            executeRoutine(data.toString());


    }
}
