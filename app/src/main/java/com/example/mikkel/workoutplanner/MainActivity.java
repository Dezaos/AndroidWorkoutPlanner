package com.example.mikkel.workoutplanner;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.data.StateData.MainActivityState;
import com.example.mikkel.workoutplanner.fragments.Fragment_Calendar;

import com.example.mikkel.workoutplanner.fragments.Fragment_Home;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.fragments.Fragment_Routines;
import com.example.mikkel.workoutplanner.singletons.StateManager;
import com.example.mikkel.workoutplanner.utils.Animation;


public class MainActivity extends AppCompatActivity {

    public static MainActivity Activity;

    private int lastBottomFragment = -1;
    BottomNavigationView bottomNavigation;
    private Menu actionMenu;
    private MainActivityState state;

    public MainActivityState getState() {
        return state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Gets the current state of the activity, so when the activity is remade, then it know what
        // is state where before
        state = StateManager.getInstance().getStateHandler().getState(this);
        if(state == null)
        {
            state = StateManager.getInstance().getStateHandler().addState(this,new MainActivityState(this));
            state.setMenuId(R.menu.menu);
        }

        //If the app just have been opened, then check if it is logged in and act accountancy, else
        //pop it's state
        if(!DataManager.getInstance().getInit())
        {
            DataManager.getInstance().setInit(true);
            if(DataManager.getInstance().getUser() != null)
            {
                loginSucces();
            }
            else
            {
                logout();
            }
        }
        state.applyState();
    }

    private void logout()
    {
        FragmentTransitionManager.getInstance().initializeFragment(this,new Fragment_Login(),true,R.id.mainFrame);
    }

    public void loginSucces()
    {
        DataManager.getInstance().login();
        changeMainFragment(new Fragment_Home(),0,String.valueOf(R.id.navigation_home));
        bottomNavigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(
                    String.valueOf(item.getItemId()));

            int currentItemIndex = -1;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentItemIndex = 0;
                    if(currentFragment == null)
                        currentFragment = new Fragment_Home();
                    break;
                case R.id.navigation_routines:
                    currentItemIndex = 1;
                    if(currentFragment == null)
                        currentFragment = new Fragment_Routines();
                    break;
                case R.id.navigation_calender:
                    currentItemIndex = 2;
                    if(currentFragment == null)
                        currentFragment = new Fragment_Calendar();
                    break;
            }

            if(currentItemIndex == lastBottomFragment)
                return false;
            changeMainFragment(currentFragment,currentItemIndex,String.valueOf(item.getItemId()) );
            return true;
        }
    };

    private void changeMainFragment(Fragment newFragment, int newIndex,String tag)
    {
        //old animations
        int inAnimationCurrent = lastBottomFragment < newIndex ? R.anim.enter_from_left : R.anim.enter_from_right;
        int inAnimationBackstack = lastBottomFragment < newIndex ? R.anim.enter_from_right : R.anim.enter_from_left;
        int outAnimationCurrent = lastBottomFragment < newIndex ? R.anim.exit_to_left : R.anim.exit_to_right;
        int outAnimationBackstack = lastBottomFragment < newIndex ? R.anim.exit_to_right : R.anim.exit_to_left;

        lastBottomFragment = newIndex;
        if(newFragment != null)
        {
            FragmentTransitionManager.getInstance().initializeFragment(Activity,newFragment,true,
                    new Animation(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out),tag);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(state.getMenuId(),menu);
        actionMenu = menu;

        for (int i = 0; i < actionMenu.size(); i++) {
            MenuItem item = actionMenu.getItem(i);
            item.setVisible(state.get_showActionMenu());
        }
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

    public void setBottomNavigationVisibility(int visibility)
    {
        bottomNavigation.setVisibility(visibility);
    }

    public void setActionMenuVisibility(boolean show)
    {
        if(actionMenu != null)
        {
            for (int i = 0; i < actionMenu.size(); i++) {
                MenuItem item = actionMenu.getItem(i);
                item.setVisible(show);
            }
        }
        state.set_showActionMenu(show);
    }

    public void setCheckedInButtonNavigation(int id)
    {
        MenuItem item = bottomNavigation.getMenu().findItem(id);
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.navigation_home:
                lastBottomFragment = 0;
                break;
            case R.id.navigation_routines:
                lastBottomFragment = 1;
                break;
            case R.id.navigation_calender:
                lastBottomFragment = 2;
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = 1;
    }
}
