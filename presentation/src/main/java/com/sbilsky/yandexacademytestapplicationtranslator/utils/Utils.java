package com.sbilsky.yandexacademytestapplicationtranslator.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class Utils {
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String languageFirstSymbolToUpperCase(String language) {
        return language.substring(0, 1).toUpperCase() + language.substring(1);
    }
}
