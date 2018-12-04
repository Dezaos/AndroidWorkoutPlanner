package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.ExecuteExercise;
import com.example.mikkel.workoutplanner.data.Database.ExecuteExerciseElement;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.utils.ViewAnimations;
import com.example.mikkel.workoutplanner.viewholders.ExecuteExerciseElementHolder;
import com.example.mikkel.workoutplanner.viewholders.ExecuteExerciseHolder;

import java.util.ArrayList;

public class ExecuteExerciseAdapter extends RecyclerView.Adapter<ExecuteExerciseHolder> implements Notification{

    ArrayList<ExecuteExercise> exercises = new ArrayList<>();
    private LayoutInflater inflater;
    private View view;
    private Context context;
    private int maxColums = 6;
    private EventHandler onClick = new EventHandler();

    public EventHandler getOnClick() {
        return onClick;
    }

    public ExecuteExerciseAdapter(Context context, ArrayList<ExecuteExercise> exercises) {
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExecuteExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.execute_exercise,parent,false);
        return new ExecuteExerciseHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull final ExecuteExerciseHolder holder, final int position) {
        ExecuteExercise exercise = exercises.get(position);
        holder.title.setText(exercise.getName());

        if(exercise.getType() == ExerciseType.Weight)
        {
            int sets = exercise.getSets();
            int reps = exercise.getReps();
            float kg = exercise.getKg();
            holder.tip.setText(sets + "x" + reps + "  " + kg + "kg");
        }
        else
        {
            int reps = exercise.getReps();
            float time = exercise.getTime();
            holder.tip.setText(reps + "x" + "  " + time + "minutes");

        }

        ExecuteWeightGridAdapter adapter = new ExecuteWeightGridAdapter(context,exercise.getElements());
        adapter.getOnClick().subscribe(this);

        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(
                context,exercise.getReps() >= maxColums ? maxColums : exercise.getReps()));
        holder.recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    @Override
    public void onNotification(Object sender, Object data) {
        onClick.notifyAllListeners(this,data);
    }
}
