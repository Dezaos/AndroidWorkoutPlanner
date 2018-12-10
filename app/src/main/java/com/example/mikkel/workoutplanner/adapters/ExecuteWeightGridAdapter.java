package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.ExecuteExerciseElement;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.utils.ViewAnimations;
import com.example.mikkel.workoutplanner.viewholders.ExecuteExerciseElementHolder;

import java.util.ArrayList;

public class ExecuteWeightGridAdapter extends RecyclerView.Adapter<ExecuteExerciseElementHolder> {

    ArrayList<ExecuteExerciseElement> elements = new ArrayList<>();
    private LayoutInflater inflater;
    private View view;

    //Onclik event, is called when something in the adapter is clicked
    private EventHandler onClick = new EventHandler();

    public EventHandler getOnClick() {
        return onClick;
    }

    public ExecuteWeightGridAdapter(Context context, ArrayList<ExecuteExerciseElement> elements) {
        this.elements = elements;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExecuteExerciseElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.execute_execise_element,parent,false);
        return new ExecuteExerciseElementHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull final ExecuteExerciseElementHolder holder, final int position) {

        final ExecuteExerciseElement element = elements.get(position);
        updateButton(element,holder);

        holder.mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Handle animation
                holder.mainButton.clearAnimation();
                if(element.getCurrentReps() == 0)
                    ViewAnimations.fade(holder.mainButton,50,1);
                else
                    ViewAnimations.blink(holder.mainButton,100,25,0,1);

                //Set rep value
                if(element.getCurrentReps() == 0)
                    element.setCurrentReps(element.getMaxReps());
                else
                    element.setCurrentReps(element.getCurrentReps() - 1);

                updateButton(element,holder);
                onClick.notifyAllListeners(this,element);
            }
        });
    }

    //Hide the button if the reps is 0
    private void updateButton(ExecuteExerciseElement element,ExecuteExerciseElementHolder holder)
    {
        Drawable buttonBackground = view.getResources().getDrawable(
                element.getCurrentReps() == 0 ? R.color.fui_transparent : R.drawable.exercise_element_circle);
        holder.mainButton.setBackground(buttonBackground);
        holder.mainButton.setText(element.getCurrentReps() == 0 ? "" : String.valueOf(element.getCurrentReps()));
    }

    //This Handles the buttons current background and text
    @Override
    public int getItemCount() {
        return elements.size();
    }
}
