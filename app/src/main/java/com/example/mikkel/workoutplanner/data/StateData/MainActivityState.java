package com.example.mikkel.workoutplanner.data.StateData;

import com.example.mikkel.workoutplanner.MainActivity;

public class MainActivityState extends StateData
{
    private boolean _showActionMenu;

    public boolean get_showActionMenu() {
        return _showActionMenu;
    }

    public void set_showActionMenu(boolean _showActionMenu) {
        this._showActionMenu = _showActionMenu;
    }

    public MainActivityState(MainActivity mainActivity)
    {
        MainActivity.Activity = mainActivity;
    }

    @Override
    public void applyState() {
        super.applyState();
    }
}
