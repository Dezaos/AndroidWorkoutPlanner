package com.example.mikkel.workoutplanner.data.StateData;

import com.example.mikkel.workoutplanner.MainActivity;

public class MainActivityState extends StateData
{
    private boolean _showActionMenu;
    private int menuId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId)
    {
        this.menuId = menuId;
        MainActivity.Activity.invalidateOptionsMenu();
    }

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
