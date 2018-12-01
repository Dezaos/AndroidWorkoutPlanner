package com.example.mikkel.workoutplanner.data.StateData;

import android.support.v4.app.Fragment;
import android.util.Pair;

import com.example.mikkel.workoutplanner.adapters.RoutineTabsAdapter;
import com.example.mikkel.workoutplanner.fragments.Fragment_Exercises;
import com.example.mikkel.workoutplanner.utils.TabInfo;

import java.util.ArrayList;

//A state for the routines fragment
public class RoutinesFragmentState extends StateData
{
    private int selectedTab;
    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

}
