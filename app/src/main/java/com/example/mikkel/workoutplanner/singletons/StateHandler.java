package com.example.mikkel.workoutplanner.singletons;

import com.example.mikkel.workoutplanner.data.StateData.StateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StateHandler
{
    private ArrayList<StateData> stateData = new ArrayList<StateData>();
    private Map<Object,StateData> objectStateData = new HashMap<Object, StateData>();


    //Call this to add a new state
    public <T extends StateData> T addState(T state)
    {
        stateData.add(state);
        return state;
    }

    public <T extends StateData> T addState(T state, String id)
    {
        state.setId(id);
        stateData.add(state);
        return state;
    }

    //Call this to get a state
    public <T extends StateData> T getState(Class<T> type)
    {
        for (int i = 0; i < stateData.size(); i++) {
            if(type.isInstance(stateData.get(i)))
            {
                return (T) stateData.get(i);
            }
        }
        return null;
    }

    //Call this to get a state with id
    public <T extends StateData> T getState(Class<T> type, String id)
    {
        for (int i = 0; i < stateData.size(); i++) {
            if(type.isInstance(stateData.get(i)) &&
                    id == stateData.get(i).getId())
            {
                return (T) stateData.get(i);
            }
        }
        return null;
    }

    //Call this to remove a state
    public <T extends StateData> T removeState(Class<T> type)
    {
        for (int i = 0; i < stateData.size(); i++) {
            if(type.isInstance(stateData.get(i)))
            {
                StateData data = stateData.get(i);
                stateData.remove(i);
                return (T)data;
            }
        }
        return null;
    }

    //Call this to remove a state with id
    public <T extends StateData> T removeState(Class<T> type, String id)
    {
        for (int i = 0; i < stateData.size(); i++) {
            if(type.isInstance(stateData.get(i)) &&
                    id == stateData.get(i).getId())
            {
                StateData data = stateData.get(i);
                stateData.remove(i);
                return (T)data;
            }
        }
        return null;
    }

    //CAll this to add state with an object as key
    public <T extends StateData> T addObjectState(T state, Object object)
    {
        if(objectStateData.containsKey(object))
            objectStateData.remove(object);
        objectStateData.put(object,state);
        return state;
    }

    //Call this to get a state with a object as key
    public <T extends StateData> T getObjectState(Object object)
    {
        return objectStateData.containsKey(object) ? (T)objectStateData.get(object) : null;
    }

    //Call this to remove a state with a objects as key
    public <T extends StateData> T removeObjectState( Object object)
    {
        if(objectStateData.containsKey(object))
        {
            StateData data = objectStateData.get(object);
            objectStateData.remove(object);
            return (T)data;
        }
        return null;
    }
}
