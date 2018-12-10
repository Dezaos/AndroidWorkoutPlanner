package com.example.mikkel.workoutplanner.utils;

import android.support.v4.app.FragmentTransaction;

public class Animation
{
    private int animationIn;
    private int animationOut;
    private int backstackAnimationIn;
    private int backStackAnimationOut;

    public int getAnimationIn() {
        return animationIn;
    }

    public void setAnimationIn(int animationIn) {
        this.animationIn = animationIn;
    }

    public int getAnimationOut() {
        return animationOut;
    }

    public void setAnimationOut(int animationOut) {
        this.animationOut = animationOut;
    }

    public int getBackstackAnimationIn() {
        return backstackAnimationIn;
    }

    public void setBackstackAnimationIn(int backstackAnimationIn) {
        this.backstackAnimationIn = backstackAnimationIn;
    }

    public int getBackStackAnimationOut() {
        return backStackAnimationOut;
    }

    public void setBackStackAnimationOut(int backStackAnimationOut) {
        this.backStackAnimationOut = backStackAnimationOut;
    }

    public Animation(int animationIn, int animationOut, int backstackAnimationIn, int backStackAnimationOut) {
        this.animationIn = animationIn;
        this.animationOut = animationOut;
        this.backstackAnimationIn = backstackAnimationIn;
        this.backStackAnimationOut = backStackAnimationOut;
    }

    //Apply the animation to a FragmetTransition
    public void useAnimation(FragmentTransaction transaction)
    {
        if(getAnimationIn() != -1 &&
                getAnimationOut() != -1 &&
                getBackstackAnimationIn() != -1 &&
                getBackStackAnimationOut() != -1)
        {
            transaction.setCustomAnimations(animationIn,animationOut,backstackAnimationIn,backStackAnimationOut);

        }
    }

}
