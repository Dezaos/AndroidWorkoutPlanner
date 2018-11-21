package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.Interfaces.OnPositiveClick;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.TabsAdapter;
import com.example.mikkel.workoutplanner.data.Database.Routine;
import com.example.mikkel.workoutplanner.data.StateData.RoutinesFragmentState;
import com.example.mikkel.workoutplanner.dialogs.DialogNewRoutine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Fragment_Routines extends NavigationFragment implements OnPositiveClick,Notification
{
    private View view;
    private TabLayout tabsLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private RoutinesFragmentState state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plans,container,false);

        return view;
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Routines");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton imageButton = view.findViewById(R.id.routineAddButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNewPlanClick();
            }
        });

        tabsAdapter = new TabsAdapter(getChildFragmentManager());

        viewPager = view.findViewById(R.id.RoutineViewPager);
        viewPager.setAdapter(tabsAdapter);

        tabsLayout = view.findViewById(R.id.WorkoutRoutineTabs);
        tabsLayout.setupWithViewPager(viewPager);

        if(DataManager.getInstance().getState(RoutinesFragmentState.class) == null)
            state = DataManager.getInstance().addState(new RoutinesFragmentState());
        else
            state = DataManager.getInstance().getState(RoutinesFragmentState.class);

        tabsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                state.setSelectedTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        syncPlans();
    }

    private void syncPlans()
    {
       tabsAdapter.clear();
        ArrayList<Routine> routines = DataManager.getInstance().getRoutines();
        for (int i = 0; i < routines.size(); i++) {
            Routine routine = routines.get(i);
            Fragment_Exercises exercises = new Fragment_Exercises();
            exercises.setRoutineUId(routine.getuId());
            tabsAdapter.addItem(exercises, routine.getName());
        }
        tabsAdapter.notifyDataSetChanged();
        setCurrentTab();
    }

    private void onNewPlanClick()
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
        DataManager.getInstance().getEventHandler().subscribe(this);
        setCurrentTab();
    }

    private void setCurrentTab()
    {
        TabLayout.Tab tab = tabsLayout.getTabAt(state.getSelectedTab());
        if(tab != null)
            tab.select();
        else
        {
            tab = tabsLayout.getTabAt(tabsLayout.getTabCount() - 1);
            if(tab != null)
                tab.select();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DataManager.getInstance().getEventHandler().unsubscribe(this);
    }

    @Override
    public void onPositiveClicked(Object data) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Routine routine = new Routine();
        routine.setName((String)data);
        database.child(DataManager.Routines_PATH_ID).child(DataManager.getInstance().get_user().getUid()).push().setValue(routine);
        syncPlans();
        setCurrentTab();
    }

    @Override
    public void onNotification(Object data) {
        syncPlans();
    }
}
