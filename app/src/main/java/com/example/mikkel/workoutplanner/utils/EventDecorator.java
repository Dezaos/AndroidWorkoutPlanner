package com.example.mikkel.workoutplanner.utils;

import android.graphics.Color;

import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

/**
 * This class is used for the calender to decorate the days
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private int radius;

    public EventDecorator(int color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        int year = calendarDay.getYear();
        int month = calendarDay.getMonth();
        int day = calendarDay.getDay();
        return DataManager.getInstance().getCurrentMonthRoutines().
                containsKey(PathUtils.getDatePath(year,month,day));
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.addSpan(new DotSpan(radius, color));
    }
}
