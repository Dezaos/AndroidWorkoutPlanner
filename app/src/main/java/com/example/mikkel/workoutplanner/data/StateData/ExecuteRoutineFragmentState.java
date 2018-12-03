package com.example.mikkel.workoutplanner.data.StateData;

public class ExecuteRoutineFragmentState extends StateData
{
    private int savedMenu;
    private String routineName;
    private String executeRoutineUid;
    private String routineuId;

    public int getSavedMenu() {
        return savedMenu;
    }

    public void setSavedMenu(int savedMenu) {
        this.savedMenu = savedMenu;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getExecuteRoutineUid() {
        return executeRoutineUid;
    }

    public void setExecuteRoutineUid(String executeRoutineUid) {
        this.executeRoutineUid = executeRoutineUid;
    }

    public String getRoutineuId() {
        return routineuId;
    }

    public void setRoutineuId(String routineuId) {
        this.routineuId = routineuId;
    }
}
