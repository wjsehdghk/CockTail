package com.mingle.myapplication.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by multimedia on 2016-04-13.
 */
public class SharedPreferenceUtil {

    //public static final String KEY_CINEMA_MAJOR = "Cinema Major";              //영화관 Major
    //public static final String KEY_CINEMA_MODE = "Cinema RingerMode";          // 영화관 RingerMode
    //public static final String KEY_CINEMA_BRIGHTNESS = "Cinema Brightness";

    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if(instance == null) {
            instance = new SharedPreferenceUtil();
        }
        return instance;
    }


    public static void putSharedPreference(Context context, String key, int value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public static int getSharedPreference(Context context, String key) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(key, 0);
    }
}
