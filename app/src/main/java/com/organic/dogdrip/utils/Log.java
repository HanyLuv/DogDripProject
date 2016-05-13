package com.organic.dogdrip.utils;

import com.organic.dogdrip.BuildConfig;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class Log {

    private static final boolean DEBUG = BuildConfig.DEBUG;


    public static void d(String tag, String msg){
        if(DEBUG){
            android.util.Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG){
            android.util.Log.e(tag + "", msg  + "");
        }
    }

}
