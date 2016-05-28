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
        editor.apply();
    }

    public static void putSharedPreference(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static int getSharedPreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, 0);
    }
    public static String getSharedPreference2(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key,null);
    }
}
