package com.hany.dogdripproject;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.hany.dogdripproject.image.ImageLoadManager;
import com.hany.dogdripproject.manager.UserInfoManager;
import com.hany.dogdripproject.net.NetworkManager;

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
