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

    public void setVisibleityOfView(Context context, int id, int visibility)
    {
        View v = ((Activity)context).findViewById(id);
        if(v != null)
        {
            if(!_views.containsKey(id))
                _views.put(id,new ViewState(context,visibility));
            else
            {
                _views.get(id).Visibility = visibility;
            }
            setVisibility(context,id,visibility);
        }
    }

    private void setVisibility(Context context,int id, int visibility)
    {
        ((Activity)context).findViewById(id).setVisibility(visibility);
    }

    private void popAllVisibilityState()
    {
        for(Integer id : _views.keySet())
        {
            ViewState state = _views.get(id);
            setVisibility(state.Context,id,state.Visibility);
        }
    }

    public void applyState()
    {
        popAllVisibilityState();
    }
    
}
