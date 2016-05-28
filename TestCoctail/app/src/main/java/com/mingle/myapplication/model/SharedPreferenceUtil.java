package com.mingle.myapplication.model;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
public class SharedPreferenceUtil {
    /*
    private static SharedPreferenceUtil instance;
    public static SharedPreferenceUtil getInstance() {
        if(instance == null) {
            instance = new SharedPreferenceUtil();
        }
        return instance;
    }
    */
    public static void putSharedPreference(Context context, String key, int value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putSharedPreference(Context context, String key, boolean value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putSharedPreference(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getSharedPreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, 0);
    }


    public static boolean getSharedPreference(Context context, String key, Boolean v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }
    public static String getSharedPreference(Context context, String key, String v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key,null);
    }
}
