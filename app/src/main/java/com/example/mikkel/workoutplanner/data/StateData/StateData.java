package com.example.mikkel.workoutplanner.data.StateData;

import android.app.Activity;
import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mikkel.workoutplanner.R;

import java.util.HashMap;
import java.util.Map;

public class StateData
{
    private Map<Integer,ViewState> _views = new HashMap<Integer, ViewState>();

    public void setVisibleityOfView(View view, int id, int visibility)
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

    private void setVisibility(View view,int id, int visibility)
    {
        view.findViewById(id).setVisibility(visibility);
    }

    private void popAllVisibilityState()
    {
        for(Integer id : _views.keySet())
        {
            ViewState state = _views.get(id);
            setVisibility(state.view,id,state.Visibility);
        }
    }

    public void applyState()
    {
        popAllVisibilityState();
    }
    
}
