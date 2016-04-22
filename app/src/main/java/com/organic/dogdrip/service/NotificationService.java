package com.organic.dogdrip.service;

import android.app.IntentService;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.organic.dogdrip.service.alarm.AlarmData;
import com.organic.dogdrip.service.alarm.AlarmHelper;
import com.organic.dogdrip.service.notification.NotificationData;
import com.organic.dogdrip.service.notification.NotificationHelper;


/**
 * Created by ojin.kwon on 2016-02-24.
 */
public class NotificationService extends IntentService {

    private static final String TAG = NotificationService.class.getSimpleName();

    private static final String ACTION_PRE_FIX = NotificationService.class.getName() + ".";
    private static final String ACTION_CREATE_NOTIFICATION = ACTION_PRE_FIX + "create.noti";
    private static final String ACTION_CANCEL_NOTIFICATION = ACTION_PRE_FIX + "cancel.noti";
    private static final String ACTION_CANCEL_ALL_NOTIFIACTION = ACTION_PRE_FIX + "cancel.noti.all";

    private static final String ACTION_CREAE_ALARM = ACTION_PRE_FIX + "create.alarm";
    private static final String ACTION_CANCEL_ALARM = ACTION_PRE_FIX + "cancel.alarm";

    private NotificationHelper mNotificationHelper = null;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationService(String name) {
        super(name);
    }

    public NotificationService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null && intent.getAction() != null){
            String action = intent.getAction();

            /**
             * Notification
             */
            if(action.equals(ACTION_CREATE_NOTIFICATION)){
                getNotificationHelper().createNotification(intent);
            }else if(action.equals(ACTION_CANCEL_NOTIFICATION)){
                getNotificationHelper().cancelNotification(intent);
            }else if(action.equals(ACTION_CANCEL_ALL_NOTIFIACTION)){
                getNotificationHelper().cancelAllNotification();
            }
            /**
             * Alarm
             */
            else if(action.equals(ACTION_CREAE_ALARM)){
                AlarmHelper.getInstance(this).createAlarm(intent);
            }else if(action.equals(ACTION_CANCEL_ALARM)){
                AlarmHelper.getInstance(this).cancelAlarm(intent);
            }
        }
    }

    private NotificationHelper getNotificationHelper(){
        if(mNotificationHelper == null){
            mNotificationHelper = new NotificationHelper(this);
        }
        return mNotificationHelper;
    }

    public static final void createNotifiaction(Context context, int id, String title, String message, Class<? extends ContextWrapper> targetClass, String uri){
        Intent intent = createNotificationIntent(context, id, title, message, targetClass, uri);
        if(context != null && intent != null) {
            context.startService(intent);
        }
    }

    public static final void createNotifcation(Context context, NotificationData data){
        if(context != null && data != null){
            Intent intent = new Intent(context, NotificationService.class);
            intent.setAction(ACTION_CREATE_NOTIFICATION);
            intent.putExtra(NotificationHelper.KEY_NOTIFICATION_PARCELABLE, data);
            context.startService(intent);
        }
    }


    public static final Intent createNotificationIntent(Context context, int id, String title, String message, Class<? extends ContextWrapper> targetClass, String uri){
        Intent intent = null;
        if(context != null && title != null && message != null) {
            NotificationData data = new NotificationData(id, title, message, targetClass, uri);
            intent = createNotificationIntent(context, data);
        }
        return  intent;
    }

    public static final Intent createNotificationIntent(Context context, NotificationData data){
        Intent intent = null;
        if(context != null && data != null){
            intent = new Intent(context, NotificationService.class);
            intent.setAction(ACTION_CREATE_NOTIFICATION);
            intent.putExtra(NotificationHelper.KEY_NOTIFICATION_PARCELABLE, data);
        }
        return intent;
    }

    public static final void cancelNotification(Context context, int id){
        if(context != null){
            Intent intent = new Intent(context, NotificationService.class);
            intent.setAction(ACTION_CANCEL_NOTIFICATION);
            intent.putExtra(NotificationHelper.KEY_ID, id);
            context.startService(intent);
        }
    }

    public static final void cancelAllNotification(Context context){
        if(context != null){
            Intent intent = new Intent(context, NotificationService.class);
            intent.setAction(ACTION_CANCEL_ALL_NOTIFIACTION);
            context.startService(intent);
        }
    }


    public static final void createAlarm(Context context, AlarmData data){
        if(context != null && data != null){
            Intent intent = new Intent(context, NotificationService.class);
            intent.setAction(ACTION_CREAE_ALARM);
            intent.putExtra(AlarmHelper.KEY_ALARM_PARCELABLE, data);
            context.startService(intent);
        }
    }
}
