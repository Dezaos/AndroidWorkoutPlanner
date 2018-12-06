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

import java.util.Calendar;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;

public class Fragment_Calender extends NavigationFragment implements Notification
{
    MCalendarView calendarView;
    int currentYear;
    int currentMonth;
    int currentDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendarView = getView().findViewById(R.id.mainCalenderView);
        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.getMarkedDates().getAll().clear();
                DataManager.getInstance().updateCurrentMonth(year,month);
            }
        });
        DataManager.getInstance().updateCurrentMonth(currentYear,currentMonth);
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
        if(data != null)
        {
            String date = data.toString();
            int year = Integer.parseInt(PathUtils.getYear(date));
            int month = Integer.parseInt(PathUtils.getMonth(date));
            int day = Integer.parseInt(PathUtils.getDay(date));

            if(!(year == currentYear && month == currentMonth && day == currentDay))
                calendarView.markDate(year,month,day);

        }
    }
}
