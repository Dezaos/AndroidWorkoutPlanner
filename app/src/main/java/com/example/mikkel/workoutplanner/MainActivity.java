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

import com.example.mikkel.workoutplanner.data.Database.Exercise;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.data.StateData.MainActivityState;
import com.example.mikkel.workoutplanner.fragments.Fragment_Calender;

import com.example.mikkel.workoutplanner.fragments.Fragment_Home;
import com.example.mikkel.workoutplanner.fragments.Fragment_Login;
import com.example.mikkel.workoutplanner.fragments.Fragment_Routines;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    public static MainActivity Activity;

    private int _lastBottomFragment = -1;
    BottomNavigationView _bottomNavigation;
    private Menu _actionMenu;
    private MainActivityState _state;

    public MainActivityState get_state() {
        return _state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        _bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Gets the current state of the activity, so when the activity is remade, then it know what
        // is state where before
        _state = DataManager.getInstance().getState(MainActivityState.class);
        if(_state == null)
        {
            _state = DataManager.getInstance().addState(new MainActivityState(this));
            _state.setMenuId(R.menu.menu);
        }

        //If the app just have been opened, then check if it is logged in and act accountancy, else
        //pop it's state
        if(!DataManager.getInstance().get_init())
        {
            DataManager.getInstance().set_init(true);
            if(DataManager.getInstance().get_user() != null)
            {
                loginSucces();
            }
            else
            {
                logout();
            }
        }
        _state.applyState();
    }

    private void logout()
    {
        FragmentTransitionManager.getInstance().initializeFragment(this,new Fragment_Login(),true,R.id.mainFrame);
    }

    public void loginSucces()
    {
        DataManager.getInstance().login();
        changeMainFragment(new Fragment_Home(),0);
        _bottomNavigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;

            int currentItemIndex = -1;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentItemIndex = 0;
                    currentFragment = new Fragment_Home();
                    break;
                case R.id.navigation_routines:
                    currentItemIndex = 1;
                    currentFragment = new Fragment_Routines();
                    break;
                case R.id.navigation_calender:
                    currentItemIndex = 2;
                    currentFragment = new Fragment_Calender();
                    break;
            }

            if(currentItemIndex == _lastBottomFragment)
                return false;
            changeMainFragment(currentFragment,currentItemIndex);
            return true;
        }
    };

    private void changeMainFragment(Fragment newFragment, int newIndex)
    {
        //old animations
        int inAnimationCurrent = _lastBottomFragment < newIndex ? R.anim.enter_from_left : R.anim.enter_from_right;
        int inAnimationBackstack = _lastBottomFragment < newIndex ? R.anim.enter_from_right : R.anim.enter_from_left;
        int outAnimationCurrent = _lastBottomFragment < newIndex ? R.anim.exit_to_left : R.anim.exit_to_right;
        int outAnimationBackstack = _lastBottomFragment < newIndex ? R.anim.exit_to_right : R.anim.exit_to_left;

        _lastBottomFragment = newIndex;
        if(newFragment != null)
        {
            FragmentTransitionManager.getInstance().initializeFragment(Activity,newFragment,true,
                    R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(_state.getMenuId(),menu);
        _actionMenu = menu;

        for (int i = 0; i < _actionMenu.size(); i++) {
            MenuItem item = _actionMenu.getItem(i);
            item.setVisible(_state.get_showActionMenu());
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
        _bottomNavigation.setVisibility(visibility);
    }

    public void setActionMenuVisibility(boolean show)
    {
        if(_actionMenu != null)
        {
            for (int i = 0; i < _actionMenu.size(); i++) {
                MenuItem item = _actionMenu.getItem(i);
                item.setVisible(show);
            }
        }
        _state.set_showActionMenu(show);
    }

}
