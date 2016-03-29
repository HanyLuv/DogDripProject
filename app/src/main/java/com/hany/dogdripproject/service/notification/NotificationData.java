package com.hany.dogdripproject.service.notification;

import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ojin.kwon on 2016-02-25.
 */
public final class NotificationData implements Parcelable {

    public static final String ACTION_NOTIFICATION = NotificationData.class.getName() + ".notification";

    public int id = -1;
    public String title = null;
    public String message = null;
    public String targetClass = null;
    public String uri = null;
    public int defaultSmallImageResource = -1;
    public int defaultLargeImageResource = -1;
    public boolean hasLarge = false;

    public NotificationData(){}

    public NotificationData(int id, String title, String message, Class< ? extends ContextWrapper> targetCls, String uri){
        this(id, title, message, targetCls, uri, -1, -1, false);
    }

    public NotificationData(int id, String title, String message, Class< ? extends ContextWrapper> targetCls, String uri, int defaultSmallImgRes){
        this(id, title, message, targetCls, uri, defaultSmallImgRes, -1, false);
    }

    public NotificationData(int id, String title, String message, Class< ? extends ContextWrapper> targetCls, String uri, int defaultSmallImgRes, int defaultLargeImgRes, boolean hasLarge){
        this.id = id;
        this.title = title;
        this.message = message;
        this.targetClass = targetCls.getName();
        this.uri = uri;
        this.defaultSmallImageResource = defaultSmallImgRes;
        this.defaultLargeImageResource = defaultLargeImgRes;
        this.hasLarge = hasLarge;
    }

    public NotificationData(Parcel parcel){
        id = parcel.readInt();
        title = parcel.readString();
        message = parcel.readString();
        targetClass = parcel.readString();
        uri = parcel.readString();
        defaultSmallImageResource = parcel.readInt();
        defaultLargeImageResource = parcel.readInt();
        hasLarge = parcel.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(targetClass);
        dest.writeString(uri);
        dest.writeInt(defaultSmallImageResource);
        dest.writeInt(defaultLargeImageResource);
        dest.writeInt(hasLarge ? 1 : 0);
    }

    public static Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel source) {
            return new NotificationData(source);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };

    @Override
    public String toString() {
        return "NotificationData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", targetClass='" + targetClass + '\'' +
                ", uri=" + uri +
                ", defaultSmallImageResource=" + defaultSmallImageResource +
                ", defaultLargeImageResource=" + defaultLargeImageResource +
                ", hasLarge=" + hasLarge +
                '}';
    }
}
