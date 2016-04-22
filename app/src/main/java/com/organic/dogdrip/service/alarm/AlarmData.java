package com.organic.dogdrip.service.alarm;

import android.os.Parcel;
import android.os.Parcelable;

import com.organic.dogdrip.service.notification.NotificationData;


/**
 * Created by ojin.kwon on 2016-02-25.
 */
public class AlarmData implements Parcelable{
    public int id = -1;
    public String name = null;
    public long dTime = 0;
    public NotificationData notificationData = null;
    public AlarmData(){

    }

    public AlarmData(int id, String name, long dTime, NotificationData data){
        this.id = id;
        this.name = name;
        this.dTime = dTime;
        notificationData = data;
    }

    public AlarmData(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        dTime = parcel.readLong();
        notificationData = parcel.readParcelable(NotificationData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeLong(dTime);
        dest.writeParcelable(notificationData, flags);
    }

    public static Creator<AlarmData> CREATOR = new Creator<AlarmData>() {
        @Override
        public AlarmData createFromParcel(Parcel source) {
            return new AlarmData(source);
        }

        @Override
        public AlarmData[] newArray(int size) {
            return new AlarmData[size];
        }
    };

    @Override
    public String toString() {
        return "AlarmData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dTime=" + dTime +
                ", notificationData=" + notificationData +
                '}';
    }
}
