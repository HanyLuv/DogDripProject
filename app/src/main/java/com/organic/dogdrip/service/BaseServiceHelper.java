package com.organic.dogdrip.service;

import android.content.Context;

/**
 * Created by ojin.kwon on 2016-02-25.
 */
abstract public class BaseServiceHelper {

    public static final String KEY_ID = "id";

    protected static final int INVALID_VALUE = -1;

    private Context mContext = null;

    public BaseServiceHelper(Context context){
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }
}
