package com.organic.dogdrip;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.kakao.AuthType;
import com.kakao.Session;
import com.organic.dogdrip.aws.S3Manager;
import com.organic.dogdrip.image.ImageLoadManager;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.vo.config.AppConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class ApplicationEx extends Application {

    private AppConfig mAppConfig = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        NetworkManager.init(this);
        ImageLoadManager.init(NetworkManager.getInstance().getRequestQueue());
        UserInfoManager.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Session.initialize(this, AuthType.KAKAO_TALK);
    }

    public AppConfig getAppConfig() {
        return mAppConfig;
    }

    public void setAppConfig(AppConfig config) {
        this.mAppConfig = config;
    }
}
