package com.example.mikkel.workoutplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.Singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.fragments.Fragment_calender;
import com.example.mikkel.workoutplanner.fragments.Fragment_plans;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private AppCompatActivity _this;
    private int _lastBottomFragment = -1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;

            int currentItemIndex = -1;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentItemIndex = 0;
                    break;
                case R.id.navigation_plans:
                    currentItemIndex = 1;
                    currentFragment = new Fragment_plans();
                    break;
                case R.id.navigation_calender:
                    currentItemIndex = 2;
                    currentFragment = new Fragment_calender();
                    break;
            }

            if(currentItemIndex == _lastBottomFragment)
                return false;

            Log.d("Test", (String.valueOf(currentItemIndex)));

            int inAnimationCurrent = _lastBottomFragment < currentItemIndex ? R.anim.enter_from_left : R.anim.enter_from_right;
            int inAnimationOld = _lastBottomFragment < currentItemIndex ? R.anim.enter_from_right : R.anim.enter_from_left;
            int outAnimationCurrent = _lastBottomFragment < currentItemIndex ? R.anim.exit_to_left : R.anim.exit_to_right;
            int outAnimationOld = _lastBottomFragment < currentItemIndex ? R.anim.exit_to_right : R.anim.exit_to_left;
            FragmentTransitionManager.getInstance().setCurrentFragmentAnimation(_this,inAnimationOld,outAnimationOld);

            _lastBottomFragment = currentItemIndex;
            if(currentFragment != null)
            {
                FragmentTransitionManager.getInstance().clearAndInitializeFragment(_this,currentFragment,inAnimationCurrent,outAnimationCurrent);
                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _this = this;
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
