package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.TabsAdapter;
import com.example.mikkel.workoutplanner.data.StateData.PlansFragmentState;
import com.example.mikkel.workoutplanner.dialogs.DialogNewPlan;
import com.example.mikkel.workoutplanner.singletons.DataManager;


public class Fragment_Plans extends Fragment
{
    private View view;
    private TabLayout tabsLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private PlansFragmentState state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workoutplans,container,false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton imageButton = view.findViewById(R.id.workplanAddButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNewPlanClick();
            }
        });

        tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addItem(new Fragment_Exercises(), "Test1");
        tabsAdapter.addItem(new Fragment_Exercises(), "Test2");
        tabsAdapter.addItem(new Fragment_Exercises(), "Test3");

        viewPager = view.findViewById(R.id.PlansViewPager);
        viewPager.setAdapter(tabsAdapter);

        tabsLayout = view.findViewById(R.id.WorkoutplanTabs);
        tabsLayout.setupWithViewPager(viewPager);

        if(DataManager.getInstance().getState(PlansFragmentState.class) == null)
            state = DataManager.getInstance().addState(new PlansFragmentState());
        else
            state = DataManager.getInstance().getState(PlansFragmentState.class);

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

    }

    private void onNewPlanClick()
    {
        DialogNewPlan dialog = new DialogNewPlan();
        dialog.show(MainActivity.Activity.getFragmentManager(), "NewPlanDialog");
    }


    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabsLayout.getTabAt(state.getSelectedTab());
        tab.select();
    }
}
