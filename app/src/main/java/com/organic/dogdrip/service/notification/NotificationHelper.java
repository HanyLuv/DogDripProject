package com.organic.dogdrip.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.organic.dogdrip.R;
import com.organic.dogdrip.service.BaseServiceHelper;
import com.organic.dogdrip.ui.SplashActivity;


/**
 * Created by ojin.kwon on 2016-02-25.
 */
public class NotificationHelper extends BaseServiceHelper {

    public static final String KEY_NOTIFICATION_PARCELABLE = "noti_parcelable";

    private NotificationManager mManager = null;

    public NotificationHelper(Context context){
        super(context);
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(Intent intent){
        if(intent != null){
            createNotification((NotificationData) intent.getParcelableExtra(KEY_NOTIFICATION_PARCELABLE));
        }
    }

    public void createNotification(NotificationData data){
        if(data != null && data.id > INVALID_VALUE){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
            builder.setContentTitle(data.title)
                    .setContentText(data.message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            if(data.defaultSmallImageResource > 0){
                builder.setSmallIcon(data.defaultLargeImageResource);
            }else{
                builder.setSmallIcon(R.drawable.common_ic_googleplayservices);
            }

//            if(data.defaultLargeImageResource > 0 && data.hasLarge){
//                builder.setLargeIcon();
//            }

            if(data.targetClass != null){
                Class<?> target = null;
                try {
                    target = Class.forName(data.targetClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Intent targetIntent = new Intent(getContext(), target);
                targetIntent.setAction(NotificationData.ACTION_NOTIFICATION);
                targetIntent.setData(Uri.parse(data.uri));
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
                stackBuilder.addParentStack(SplashActivity.class);
                stackBuilder.addNextIntent(targetIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);
            }
            mManager.notify(data.id, builder.build());
        }
    }

    public void cancelNotification(Intent intent){
        if(intent != null){
            cancelNotification(intent.getIntExtra(KEY_ID, INVALID_VALUE));
        }
    }
    public void cancelNotification(int id){
        mManager.cancel(id);
    }

    public void cancelAllNotification(){
        mManager.cancelAll();
    }
}
