package com.example.mikkel.workoutplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.mikkel.workoutplanner.Singletons.DataManager;
import com.example.mikkel.workoutplanner.Singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.data.StateData.MainActivityState;
import com.example.mikkel.workoutplanner.fragments.Fragment_Calender;

import com.example.mikkel.workoutplanner.fragments.Fragment_Home;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.fragments.Fragment_WorkoutPlans;


public class MainActivity extends AppCompatActivity {

    public static MainActivity Activity;

    private int _lastBottomFragment = -1;
    BottomNavigationView _bottomNavigation;
    private MainActivityState _state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);

        _state = DataManager.getInstance().getState(MainActivityState.class);
        if(_state == null)
        {
            _state = DataManager.getInstance().addState(new MainActivityState());
        }

        if(DataManager.getInstance().get_user() != null)
        {
            loginSucces();
        }
        else
        {
            logout();
        }
    }

    private void logout()
    {
        _bottomNavigation.setOnNavigationItemSelectedListener(null);
        _bottomNavigation.setVisibility(View.INVISIBLE);
        FragmentTransitionManager.getInstance().clearAndInitializeFragment(this,new Fragment_Login(),R.id.outerFrame);
    }

    public void loginSucces()
    {
        _bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        _bottomNavigation.setVisibility(View.VISIBLE);
        changeMainFragment("Home",new Fragment_Home(),0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;

            int currentItemIndex = -1;
            String newTitle = "Home";
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentItemIndex = 0;
                    currentFragment = new Fragment_Home();
                    newTitle = "Home";
                    break;
                case R.id.navigation_plans:
                    currentItemIndex = 1;
                    currentFragment = new Fragment_WorkoutPlans();
                    newTitle = "Edit Workout Plans";
                    break;
                case R.id.navigation_calender:
                    currentItemIndex = 2;
                    currentFragment = new Fragment_Calender();
                    newTitle = "Calender";
                    break;
            }

            if(currentItemIndex == _lastBottomFragment)
                return false;
            changeMainFragment(newTitle,currentFragment,currentItemIndex);
            return true;
        }
    };

    private void changeMainFragment(String newTitle, Fragment newFragment, int newIndex)
    {
        getSupportActionBar().setTitle(newTitle);

        int inAnimationCurrent = _lastBottomFragment < newIndex ? R.anim.enter_from_left : R.anim.enter_from_right;
        int inAnimationOld = _lastBottomFragment < newIndex ? R.anim.enter_from_right : R.anim.enter_from_left;
        int outAnimationCurrent = _lastBottomFragment < newIndex ? R.anim.exit_to_left : R.anim.exit_to_right;
        int outAnimationOld = _lastBottomFragment < newIndex ? R.anim.exit_to_right : R.anim.exit_to_left;
        FragmentTransitionManager.getInstance().setCurrentFragmentAnimation(Activity,inAnimationOld,outAnimationOld);

        _lastBottomFragment = newIndex;
        if(newFragment != null)
        {
            FragmentTransitionManager.getInstance().clearAndInitializeFragment(Activity,newFragment,inAnimationCurrent,outAnimationCurrent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.LogoutButton:
                logout();
                DataManager.getInstance().logout();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
