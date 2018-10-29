package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.WorkoutPlan;

public class WorkoutPlanAdapter extends ArrayAdapter<WorkoutPlan>
{
    private Context context;

    public WorkoutPlanAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final WorkoutPlan item = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.workoutplanmenu_item,parent,false);
        }

        return convertView;
    }
}
