package com.example.mikkel.workoutplanner.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.mikkel.workoutplanner.Interfaces.onPositiveClick;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.Plan;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogNewPlan extends DialogFragment
{
    private onPositiveClick listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_new_plan_dialog,null);

        builder.setView(view).
                setPositiveButton(R.string.planDialogAccept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user = DataManager.getInstance().get_user();
                        if(user != null)
                        {
                            String name = ((EditText)view.findViewById(R.id.planNameEditText)).getText().toString();
                            if(listener != null)
                                listener.onPositiveClicked(name);
                        }


                    }
                }).setNegativeButton(R.string.planDialogDecline, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void setListener(onPositiveClick listener)
    {
        this.listener = listener;
    }
}
