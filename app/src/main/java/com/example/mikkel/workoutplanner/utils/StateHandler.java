package com.example.mikkel.workoutplanner.utils;

import android.graphics.Paint;

import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.data.StateData.StateData;
import com.example.mikkel.workoutplanner.fragments.NavigationFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to handle states, states are used by fragments and activities to hold their state
 */
public class StateHandler
{
    private Map<Object,StateData> states = new HashMap<Object, StateData>();

    //Call this to add state with an object as key
    public <T extends StateData> T addState(Object object,T state)
    {
        if(states.containsKey(object))
            states.remove(object);
        states.put(object,state);
        return state;
    }

    //Call this to get a state with a object as key
    public <T extends StateData> T getState(Object object)
    {
        return states.containsKey(object) ? (T) states.get(object) : null;
    }

    public Object getKey(int hashCode)
    {
        for (Object object : states.keySet())
        {
            if(object.hashCode() == hashCode)
                return object;
        }
        return null;
    }


    //Call this to remove a state with a objects as key
    public <T extends StateData> T removeState(Object object)
    {
        if(states.containsKey(object))
        {
            StateData data = states.get(object);
            states.remove(object);
            return (T)data;
        }
        return null;
    }

    public boolean contains(Object onject)
    {
        return states.containsKey(onject);
    }

    public boolean contains(int hashCode)
    {
        for (Object object : states.keySet())
        {
            if(object.hashCode() == hashCode)
                return true;
        }
        return false;
    }


    public void clear()
    {
        states.clear();
    }
    public int size()
    {
        return states.size();
    }
}
