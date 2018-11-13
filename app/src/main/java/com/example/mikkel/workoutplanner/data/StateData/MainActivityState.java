package com.example.mikkel.workoutplanner.data.StateData;

import com.example.mikkel.workoutplanner.MainActivity;

public class MainActivityState extends StateData
{
    private String _currentTitle;
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


    public String get_currentTitle() {
        return _currentTitle;
    }

    public void set_currentTitle(String _currentTitle) {
        this._currentTitle = _currentTitle;
        MainActivity.Activity.getSupportActionBar().setTitle(_currentTitle);
    }

    @Override
    public void applyState() {
        super.applyState();
        set_currentTitle(_currentTitle);
    }
}
