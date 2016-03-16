package com.hany.dogdripproject;

import android.app.Application;

import com.hany.dogdripproject.net.NetworkManager;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class ApplicationEx extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.init(this);
    }
}
