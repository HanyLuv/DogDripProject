package com.organic.dogdrip.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.organic.dogdrip.preferences.ConfigPreferenceManager;
import com.organic.dogdrip.service.notification.NotificationData;
import com.organic.dogdrip.ui.SplashActivity;
import com.organic.dogdrip.utils.Log;


/**
 * Created by kwonojin on 16. 3. 28..
 */
public class GCMService extends GcmListenerService {

    private static final String TAG = "GCMService";
    private static final String DATA_KEY = "msg";

    private ConfigPreferenceManager mConfigPref = null;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        if(mConfigPref == null){
            mConfigPref = new ConfigPreferenceManager(this);
        }

        String message = data.getString(DATA_KEY);
        if(message != null && mConfigPref.isSettingPush()){
            NotificationData notificationData = new NotificationData();
            notificationData.id = 1;
            notificationData.message = message;
            notificationData.targetClass = SplashActivity.class.getName();
            notificationData.title = "새로운 알림";
            notificationData.hasLarge = false;
            notificationData.uri = "drip/1";
            NotificationService.createNotifcation(this, notificationData);
        }
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }

}
