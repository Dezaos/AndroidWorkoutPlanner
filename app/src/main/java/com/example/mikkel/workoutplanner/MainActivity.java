package com.example.mikkel.workoutplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.Singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.fragments.Fragment_calender;
import com.example.mikkel.workoutplanner.fragments.Fragment_plans;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private AppCompatActivity _this;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    break;
                case R.id.navigation_plans:
                    currentFragment = new Fragment_plans();
                    break;
                case R.id.navigation_calender:
                    currentFragment = new Fragment_calender();
                    break;
            }

            if(currentFragment != null)
            {
                FragmentTransitionManager.getInstance().ClearAndInitializeFragment(_this,currentFragment);
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
