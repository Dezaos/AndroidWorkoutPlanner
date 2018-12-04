package com.example.mikkel.workoutplanner.utils;

import android.view.View;
import android.view.animation.CycleInterpolator;

public class ViewAnimations
{
    public static void blink(final View view, final long inDuration, final long outDuration, final float inAlpha, final float outAlpha)
    {
        view.animate().setDuration(inDuration).alpha(inAlpha).withEndAction(new Runnable() {
            @Override
            public void run() {
                view.animate().setDuration(outDuration).alpha(outAlpha).start();
            }
        }).start();
    }

    public static void fade(final View view, final long durtaion, final float alpha)
    {
        view.animate().setDuration(durtaion).alpha(alpha).start();
    }
}
