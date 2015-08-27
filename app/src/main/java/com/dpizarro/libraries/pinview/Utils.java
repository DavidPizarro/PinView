package com.dpizarro.libraries.pinview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * User: David Pizarro de Jesús
 */
class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();

    public static float getRandomSize(Context context) {
        List<Float> options = new ArrayList<>();
        options.add(context.getResources().getDimension(R.dimen.size_custom_random_1));
        options.add(context.getResources().getDimension(R.dimen.size_custom_random_2));
        options.add(context.getResources().getDimension(R.dimen.size_custom_random_3));
        options.add(context.getResources().getDimension(R.dimen.size_custom_random_4));
        options.add(context.getResources().getDimension(R.dimen.size_custom_random_5));

        Random random = new Random();
        int index = random.nextInt(options.size());
        return options.get(index);
    }

    public static String getRandomSplit(Context context) {

        List<String> options = Arrays.asList(context.getResources().getStringArray(R.array.random_splits));
        Random random = new Random();
        int index = random.nextInt(options.size());
        return options.get(index);
    }

    public static int getRandomColor() {
        // generate the random integers for r, g and b value
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r, g, b);
    }

    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = ((Activity) context).getCurrentFocus();
            if (imm != null && currentFocus != null) {
                IBinder windowToken = currentFocus.getWindowToken();
                if (windowToken != null) {
                    imm.hideSoftInputFromWindow(windowToken, 0);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't even hide keyboard " + e.getMessage());
        }
    }
}
