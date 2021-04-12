package com.example.movieappschool.logic;

import android.content.Context;

public class Converter {

    public static float dpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
