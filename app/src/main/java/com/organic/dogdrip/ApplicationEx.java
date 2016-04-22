package com.organic.dogdrip;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.organic.dogdrip.image.ImageLoadManager;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.NetworkManager;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class ApplicationEx extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.init(this);
        ImageLoadManager.init(NetworkManager.getInstance().getRequestQueue());
        UserInfoManager.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
