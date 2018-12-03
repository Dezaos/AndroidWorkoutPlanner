package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.Interfaces.OnPositiveClick;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.RoutineTabsAdapter;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.RoutinesFragmentState;
import com.example.mikkel.workoutplanner.dialogs.DialogNewRoutine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.utils.TabInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Fragment_Routines extends NavigationFragment implements OnPositiveClick,Notification
{
    private View view;
    private TabLayout tabsLayout;
    private ViewPager viewPager;
    private RoutineTabsAdapter tabsAdapter;
    private String nextTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routines,container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Routines");

        MainActivity.Activity.getState().setMenuId(R.menu.routines_menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton imageButton = view.findViewById(R.id.routineAddButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNewRoutineClick();
            }
        });

        tabsAdapter = new RoutineTabsAdapter(getChildFragmentManager());

        viewPager = view.findViewById(R.id.RoutineViewPager);
        tabsLayout = view.findViewById(R.id.WorkoutRoutineTabs);
        viewPager.setAdapter(tabsAdapter);
        tabsLayout.setupWithViewPager(viewPager);

        if(getState(RoutinesFragmentState.class) == null)
            addState(new RoutinesFragmentState());

        tabsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getState(RoutinesFragmentState.class).setSelectedTab(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        syncRoutines(true,false);
    }

    private void syncRoutines(boolean init, boolean setLastTab)
    {
        boolean clear = !init;
        ArrayList<Routine> routines = DataManager.getInstance().getRoutines();

        tabsAdapter.clear();
        for (int i = 0; i < routines.size(); i++) {
            Routine routine = routines.get(i);
            Fragment_Exercises exercises = new Fragment_Exercises();
            exercises.getState().setRoutineUid(routine.getuId());
            tabsAdapter.addItem(exercises, new TabInfo(routine.getName(),routine.getuId()));
        }

        tabsAdapter.notifyDataSetChanged();
        setCurrentTab(setLastTab ? tabsAdapter.getCount() : -1);
    }

    private void onNewRoutineClick()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        DialogNewRoutine dialog = new DialogNewRoutine();
        dialog.setListener(this);
        dialog.show(getActivity().getFragmentManager(), "NewPlanDialog");
    }


    @Override
    public void onResume() {
        super.onResume();
        DataManager.getInstance().getRoutineEvent().subscribe(this);
        setCurrentTab(-1);
    }

    private void setCurrentTab(int nextindex)
    {
        int index = nextindex;

        if(index < 0)
        {
            index = nextTab == null ? getState(RoutinesFragmentState.class).getSelectedTab() :
                    tabsAdapter.getIndex(nextTab);
        }

        TabLayout.Tab tab = index != -1 ? tabsLayout.getTabAt(index) : null;
        if(tab != null)
            tab.select();
        else
        {
            tab = tabsLayout.getTabAt(tabsLayout.getTabCount() - 1);
            if(tab != null)
                tab.select();
        }

        if(nextTab != null)
            nextTab = null;
    }

    public void changeCurrentTab(String uId)
    {
        nextTab = uId;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataManager.getInstance().getRoutineEvent().unsubscribe(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabsAdapter.clear();
        tabsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPositiveClicked(Object data) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Routine routine = new Routine();
        routine.setName((String)data);
        DatabaseReference ref = database.child(DataManager.Routines_PATH_ID).
                child(DataManager.getInstance().getUser().getUid()).push();
        routine.setuId(ref.getKey());
        ref.setValue(routine);
        syncRoutines(false,true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.removeRoutineMenu:
                RoutinesFragmentState state = getState(RoutinesFragmentState.class);
                if(tabsAdapter.getCount() > 0 && tabsAdapter.getCount() >= state.getSelectedTab())
                {
                    String routineUid = tabsAdapter.getInfo(state.getSelectedTab()).getuId();
                    FirebaseDatabase.getInstance().getReference().child(DataManager.Routines_PATH_ID).
                            child(DataManager.getInstance().getUser().getUid()).
                            child(routineUid).setValue(null);

                    FirebaseDatabase.getInstance().getReference().child(DataManager.EXERCISES_PATH_ID).
                            child(DataManager.getInstance().getUser().getUid()).
                            child(routineUid).removeValue();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNotification(Object sender, Object data) {
        syncRoutines(false,false);
    }
}
