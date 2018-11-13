package com.example.mikkel.workoutplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.Singletons.DataManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment_Login extends Fragment
{
    private View _view;
    final int LOGIN_RC = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_login,container,false);

        //Hide the bottom navigation bar and action menu
        MainActivity.Activity.setBottomNavigationVisibility(View.GONE);

        Button loginButton = _view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        LOGIN_RC);
            }
        });

        MainActivity.Activity.getSupportActionBar().setTitle(R.string.LoginTitle);

        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_RC)
        {
            if(resultCode == RESULT_OK)
            {
                DataManager.getInstance().set_user(FirebaseAuth.getInstance().getCurrentUser());
                MainActivity.Activity.loginSucces();
            }
            else
            {
                TextView errorText = _view.findViewById(R.id.ErrorText);
                errorText.setVisibility(View.VISIBLE);
            }
        }
    }
}