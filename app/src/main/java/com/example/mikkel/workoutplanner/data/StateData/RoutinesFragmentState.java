package com.example.mikkel.workoutplanner.data.StateData;

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
