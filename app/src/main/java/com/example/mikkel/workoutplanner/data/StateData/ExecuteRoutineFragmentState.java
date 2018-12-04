package com.example.mikkel.workoutplanner.data.StateData;

import android.support.design.widget.Snackbar;

import com.example.mikkel.workoutplanner.data.Database.ExecuteExercise;
import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;

import java.util.Timer;

public class ExecuteRoutineFragmentState extends StateData
{
    private int savedMenu;
    private String routineuId;
    private ExecuteRoutine executeRoutine = new ExecuteRoutine();
    private boolean done;

    private long savedTime;
    private boolean showSnackbar;

    private String oldUId;
    private boolean updateLast;

    public int getSavedMenu() {
        return savedMenu;
    }

    public void setSavedMenu(int savedMenu) {
        this.savedMenu = savedMenu;
    }

    public ExecuteRoutine getExecuteRoutine() {
        return executeRoutine;
    }

    public void setExecuteRoutine(ExecuteRoutine executeRoutine) {
        this.executeRoutine = executeRoutine;
    }

    public String getRoutineuId() {
        return routineuId;
    }

    public void setRoutineuId(String routineuId) {
        this.routineuId = routineuId;
    }

    public long getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(long savedTime) {
        this.savedTime = savedTime;
    }

    public boolean getShowSnackbar() {
        return showSnackbar;
    }

    public void setShowSnackbar(boolean showSnackbar) {
        this.showSnackbar = showSnackbar;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getOldUId() {
        return oldUId;
    }

    public void setOldUId(String oldUId) {
        this.oldUId = oldUId;
    }

    public boolean getUpdateLast() {
        return updateLast;
    }

    public void setUpdateLast(boolean updateLast) {
        this.updateLast = updateLast;
    }
}
