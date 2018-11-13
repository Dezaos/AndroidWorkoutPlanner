package com.example.mikkel.workoutplanner.data.StateData;

import android.content.Context;
import android.view.View;

public class ViewState
{
    public Context Context;
    public int Visibility;

    public ViewState(Context context, int visibility) {
        Context = context;
        Visibility = visibility;
    }
}
