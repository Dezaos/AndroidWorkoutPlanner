package com.example.mikkel.workoutplanner.singletons;

import android.support.annotation.NonNull;

import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.data.StateData.StateData;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private FirebaseAuth _auth;
    private ArrayList<StateData> _stateData = new ArrayList<StateData>();
    private boolean _init;
    private FirebaseUser _user;

    public FirebaseUser get_user() {
        return _user;
    }
    public void set_user(FirebaseUser _user) {
        this._user = _user;
    }

    public boolean get_init() {
        return _init;
    }

    public void set_init(boolean _init) {
        this._init = _init;
    }

    private DataManager()
    {
        _auth = FirebaseAuth.getInstance();
        _user = _auth.getCurrentUser();
    }

    public void logout()
    {
        _user = null;

        AuthUI.getInstance()
                .signOut(MainActivity.Activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        FragmentTransitionManager.getInstance().clearAndInitializeFragment(MainActivity.Activity,new Fragment_Login());
                    }
                });

    }

    public <T extends StateData> T addState(T state)
    {
        _stateData.add(state);
        return state;
    }

    public <T extends StateData> T getState(Class<T> type)
    {
        for (int i = 0; i < _stateData.size(); i++) {
            if(type.isInstance(_stateData.get(i)))
            {
                return (T)_stateData.get(i);
            }
        }
        return null;
    }

}
