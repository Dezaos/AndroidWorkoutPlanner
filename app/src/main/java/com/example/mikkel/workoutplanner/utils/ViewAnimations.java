package com.example.mikkel.workoutplanner.utils;

import android.view.View;

public class ViewAnimations
{
    public static void Blink(View view, final long inDuration, final long outDuration, final float inAlpha, final float outAlpha)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.animate().setDuration(inDuration).alpha(inAlpha).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().setDuration(outDuration).alpha(outAlpha).start();
                    }
                }).start();
            }
        });
    }
}
