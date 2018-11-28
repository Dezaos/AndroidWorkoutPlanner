package com.example.mikkel.workoutplanner.data.StateData;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/*
This is a super class used for every state. A state is used to store the different values in a
activity or fragment.
 */
public class StateData
{
    private String id;
    private Map<Integer,ViewState> _views = new HashMap<Integer, ViewState>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //This is used to set the visibility of a view and store it
    public void setVisibilityOfView(View view, int id, int visibility)
    {
        View v = view.findViewById(id);
        if(v != null)
        {
            if(!_views.containsKey(id))
                _views.put(id,new ViewState(view,visibility));
            else
            {
                _views.get(id).Visibility = visibility;
            }
            setVisibility(view,id,visibility);
        }
    }

    //This is used to set the visibility of a view and store it
    private void setVisibility(View view,int id, int visibility)
    {
        view.findViewById(id).setVisibility(visibility);
    }

    //This is used to apply all visibility changes in the state
    private void applyAllVisibilityState()
    {
        for(Integer id : _views.keySet())
        {
            ViewState state = _views.get(id);
            setVisibility(state.view,id,state.Visibility);
        }
    }

    //This is used to apply every state change
    public void applyState()
    {
        applyAllVisibilityState();
    }
    
}
