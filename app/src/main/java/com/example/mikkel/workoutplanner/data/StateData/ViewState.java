package com.example.mikkel.workoutplanner.data.StateData;

import android.content.Context;
import android.view.View;

public class ViewState
{
    public View view;
    public int Visibility;

    public ViewState(View view, int visibility) {
        this.view = view;
        Visibility = visibility;
    }
}
