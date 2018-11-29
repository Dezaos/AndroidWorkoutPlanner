package com.example.mikkel.workoutplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment_Login extends NavigationFragment
{
    private View _view;
    final int LOGIN_RC = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_login,container,false);

        //This makes the login button run the firebase logon ui
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
        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_RC)
        {
            //If the login is successful, then run the login behavior
            if(resultCode == RESULT_OK)
            {
                DataManager.getInstance().setUser(FirebaseAuth.getInstance().getCurrentUser());
                MainActivity.Activity.loginSucces();
            }
            else
            {
                //If the login is unsuccessful, then show the error text
                TextView errorText = _view.findViewById(R.id.ErrorText);
                errorText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Login");
        setupBottomNavigation(View.GONE);
        setupActioMenu(false);
    }
}
