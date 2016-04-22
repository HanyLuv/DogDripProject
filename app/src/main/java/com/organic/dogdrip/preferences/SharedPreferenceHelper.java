package com.organic.dogdrip.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

/**
 * Created by ojin.kwon on 2016-02-02.
 */
public class SharedPreferenceHelper {

    public SharedPreferences prefs;

    protected SharedPreferenceHelper(Context context, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        } else {
            prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    public boolean getData(String key, boolean defaultVal) {

        return prefs.getBoolean(key, defaultVal);
    }

    public String getData(String key, String defaultVal) {

        return prefs.getString(key, defaultVal);
    }

    public int getData(String key, int defaultVal) {

        return prefs.getInt(key, defaultVal);
    }

    public long getData(String key, long defaultVal) {

        return prefs.getLong(key, defaultVal);
    }

    public void putData(String key, boolean val) {

        Editor edit = prefs.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }

    public void putData(String key, int val) {

        Editor edit = prefs.edit();
        edit.putInt(key, val);
        edit.commit();
    }

    public void putData(String key, long val) {

        Editor edit = prefs.edit();
        edit.putLong(key, val);
        edit.commit();
    }

    public void putData(String key, String val) {

        Editor edit = prefs.edit();
        edit.putString(key, val);
        edit.commit();
    }
}
