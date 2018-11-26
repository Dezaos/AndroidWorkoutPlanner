package com.example.mikkel.workoutplanner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.mikkel.workoutplanner.Interfaces.OnPositiveClick;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.auth.FirebaseUser;

public class DialogNewRoutine extends DialogFragment
{
    private OnPositiveClick listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_new_plan_dialog,null);

        builder.setView(view).
                setPositiveButton(R.string.routineDialogAccept, new DialogInterface.OnClickListener() {
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
                }).setNegativeButton(R.string.routineDialogDecline, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void setListener(OnPositiveClick listener)
    {
        this.listener = listener;
    }
}
