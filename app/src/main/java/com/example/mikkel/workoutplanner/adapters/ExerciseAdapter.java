package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.fragments.Fragment_EditExercise;

import java.util.ArrayList;

public class ExerciseAdapter extends ArrayAdapter<Exercise>
{
    private Context context;
    private ArrayList<Exercise> exercises;

    public ExerciseAdapter(@NonNull Context context, ArrayList<Exercise> resource) {
        super(context,0, resource);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Exercise item = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.exercise_element,parent,false);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransitionManager.getInstance().initializeFragment(
                            (AppCompatActivity)context,new Fragment_EditExercise(), false,
                            R.anim.enter_from_right,R.anim.exit_to_left,
                            R.anim.enter_from_left,R.anim.exit_to_right);

                }
            });
        }

        return convertView;
    }
}
