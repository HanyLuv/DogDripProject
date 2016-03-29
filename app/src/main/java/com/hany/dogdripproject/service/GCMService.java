package com.hany.dogdripproject.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.service.notification.NotificationData;
import com.hany.dogdripproject.ui.SplashActivity;
import com.hany.dogdripproject.utils.Log;


/**
 * Created by kwonojin on 16. 3. 28..
 */
public class GCMService extends GcmListenerService {

    private static final String TAG = "GCMService";
    private static final String DATA_KEY = "msg";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = data.getString(DATA_KEY);
        if(message != null){
            NotificationData notificationData = new NotificationData();
            notificationData.id = 1;
            notificationData.message = "푸쉬 테스트";
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
