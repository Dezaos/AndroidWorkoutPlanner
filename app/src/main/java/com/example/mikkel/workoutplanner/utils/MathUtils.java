package com.example.mikkel.workoutplanner.utils;

import java.math.BigDecimal;

public class MathUtils
{
    /**
     * Round a float to nearest decimal
     * @param number
     * @param decimalPlace
     * @return
     */
    public static float round(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
