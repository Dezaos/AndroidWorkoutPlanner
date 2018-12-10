package com.example.mikkel.workoutplanner.utils;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CurrentDayDecorator implements DayViewDecorator {

    private Drawable background;
    private CalendarDay currentDay;

    public CurrentDayDecorator(Drawable background, CalendarDay currentDay) {
        this.background = background;
        this.currentDay = currentDay;
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        return calendarDay.equals(currentDay);
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.setBackgroundDrawable(background);
    }
}
