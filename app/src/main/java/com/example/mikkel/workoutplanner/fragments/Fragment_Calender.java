package com.example.mikkel.workoutplanner.fragments;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.Interfaces.Notification;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.example.mikkel.workoutplanner.singletons.FragmentTransitionManager;
import com.example.mikkel.workoutplanner.utils.Animation;
import com.example.mikkel.workoutplanner.utils.EventDecorator;
import com.example.mikkel.workoutplanner.utils.OnClickListenerWithExeRoutineHashmap;
import com.example.mikkel.workoutplanner.utils.PathUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Fragment_Calender extends NavigationFragment implements Notification, OnDateSelectedListener, OnMonthChangedListener {
    MaterialCalendarView calender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calender = getView().findViewById(R.id.calendarView);
        calender.setOnDateChangedListener(this);
        calender.setOnMonthChangedListener(this);
        CalendarDay date = calender.getCurrentDate();
        DataManager.getInstance().updateCurrentMonth(date.getYear(),date.getMonth());
        ViewGroup.LayoutParams layoutParams = calender.getLayoutParams();
        int markerRadius = 0;

        if(getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutParams.height = (int)(getActivity().getApplicationContext().
                    getResources().getDisplayMetrics().heightPixels * 0.75);
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            markerRadius = 5;
        }
        else
        {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            markerRadius = 10;
        }

        calender.addDecorator(new EventDecorator(getResources().getColor(R.color.colorPrimary),markerRadius));

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
            if(calender != null)
                calender.invalidateDecorators();
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        ArrayList<ExecuteRoutine> executeRoutines = null;
        String path = PathUtils.getDatePath(calendarDay.getYear(),
                calendarDay.getMonth(),calendarDay.getDay());
        if(!DataManager.getInstance().getCurrentMonthRoutines().containsKey(path))
            return;

        executeRoutines = DataManager.getInstance().getCurrentMonthRoutines().get(path);

        if (executeRoutines == null && executeRoutines.size() == 0)
            return;

        if(executeRoutines.size() == 1)
            startRoutine(executeRoutines.get(0));
        else
        {
            HashMap<String,ExecuteRoutine> executeRoutineHashMap = new HashMap<>();
            for (int i = 0; i < executeRoutines.size(); i++) {
                ExecuteRoutine executeRoutine = executeRoutines.get(i);
                int hour = executeRoutine.getHourOfDay();
                int minute = executeRoutine.getMinutesOfDay();
                String title = executeRoutine.getName() + " - " +
                        (hour < 10 ? "0" :"") + hour + ":" +
                        (minute < 10 ? "0" :"") + minute;
                executeRoutineHashMap.put(title,executeRoutine);
            }

            String[] notFinalTitles = new String[executeRoutineHashMap.size()];

            int indexer = 0;
            for (String key : executeRoutineHashMap.keySet())
            {
                notFinalTitles[indexer] = key;
                indexer++;
            }

            final String[] titles = notFinalTitles;

            final AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
            builder.setTitle("Pick routine")
                    .setItems(titles, new OnClickListenerWithExeRoutineHashmap(executeRoutineHashMap) {
                        public void onClick(DialogInterface dialog, int which) {
                            if(getRoutines().containsKey(titles[which]))
                                startRoutine(getRoutines().get(titles[which]));

                        }
                    });

            builder.show();
        }


    }

    private void startRoutine(ExecuteRoutine executeRoutine)
    {
        Fragment_ExecuteRoutine executeRoutineFragment = new Fragment_ExecuteRoutine();
        executeRoutineFragment.getState().setRoutineuId(executeRoutine.getuId());
        executeRoutineFragment.getState().setExecuteRoutine(executeRoutine);
        executeRoutineFragment.getState().setOldUId(executeRoutine.getuId());
        executeRoutineFragment.getState().setUpdateLast(false);

        FragmentTransitionManager.getInstance().initializeFragment(MainActivity.Activity,
                executeRoutineFragment,false,
                new Animation(R.anim.enter_from_right,R.anim.exit_to_left,
                        R.anim.enter_from_left,R.anim.exit_to_right));
    }

    @Override
    public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
        CalendarDay date = calendarDay;
        DataManager.getInstance().updateCurrentMonth(date.getYear(),date.getMonth());
    }
}
