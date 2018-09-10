package com.r3tr0.moneyassistant.utils;

import android.content.res.Resources;

public class Converters {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
