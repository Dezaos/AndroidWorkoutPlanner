package com.example.mikkel.workoutplanner.singletons;

import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.utils.StateHandler;

public class StateManager {
    //Singleton
    private static final StateManager ourInstance = new StateManager();

    public static StateManager getInstance() {
        return ourInstance;
    }

    //Fields
    private StateHandler stateHandler = new StateHandler();

    //Properties
    public StateHandler getStateHandler() {
        return stateHandler;
    }

    public void setStateHandler(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    //Contructor
    private StateManager() {
    }
}
