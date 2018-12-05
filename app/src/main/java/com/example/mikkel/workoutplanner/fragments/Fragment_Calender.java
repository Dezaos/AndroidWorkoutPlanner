package com.example.mikkel.workoutplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.utils.PathUtils;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;

public class Fragment_Calender extends NavigationFragment implements Notification
{
    MCalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendarView = getView().findViewById(R.id.mainCalenderView);
        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.getMarkedDates().getAll().clear();
                DataManager.getInstance().updateCurrentMonth(year,month);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        DataManager.getInstance().getCurrentRoutineEvent().subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        DataManager.getInstance().getCurrentRoutineEvent().unsubscribe(this);
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Calender");

        MainActivity.Activity.getState().setMenuId(R.menu.menu);

    }


    @Override
    public void onNotification(Object sender, Object data) {
        String date = data.toString();
        int year = Integer.getInteger(PathUtils.getYear(date));
        int month = Integer.getInteger(PathUtils.getMonth(date));
        int day = Integer.getInteger(PathUtils.getDay(date));

        calendarView.markDate(year,month,day);
    }
}
